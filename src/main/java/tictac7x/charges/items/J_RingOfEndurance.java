package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.ChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;

import java.util.Optional;

public class J_RingOfEndurance extends ChargedItem {
    public J_RingOfEndurance(
        final Client client,
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(ChargesImprovedConfig.ring_of_endurance, ItemID.RING_OF_ENDURANCE, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_ENDURANCE),
            new TriggerItem(ItemID.RING_OF_ENDURANCE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.RING_OF_ENDURANCE_UNCHARGED_24844).fixedCharges(0),
        };

        this.triggers = new TriggerBase[] {
            // Charge.
            new OnChatMessage("You load your Ring of endurance with (?<charges>.+) stamina doses?.").increaseDynamically(),

            // Check.
            new OnChatMessage("Your Ring of endurance is charged with (?<charges>.+) stamina doses?.").setDynamicallyCharges(),

            // Use charge.
            new OnChatMessage("Your Ring of endurance doubles the duration of your stamina potion's effect.").decreaseCharges(1),

            // Uncharge.
            new OnMenuOptionClicked("Yes").runConsumerOnNextGameTick(() -> {
                final Optional<Widget> unchargeWidget = ChargesImprovedPlugin.getWidget(client, 584, 0, 2);
                if (unchargeWidget.isPresent() && unchargeWidget.get().getText().equals("Are you sure you want to uncharge your Ring of endurance?")) {
                    setCharges(0);
                }
            }),
        };
    }
}