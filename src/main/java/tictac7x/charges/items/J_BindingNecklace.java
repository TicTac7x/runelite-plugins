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
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnMenuOptionClicked;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

import java.util.Optional;

public class J_BindingNecklace extends ChargedItem {
    public J_BindingNecklace(
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
        super(ChargesImprovedConfig.binding_necklace, ItemID.BINDING_NECKLACE, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BINDING_NECKLACE).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[] {
            // Check, one left.
            new OnChatMessage("You have one charge left before your Binding necklace disintegrates.").setFixedCharges(1),

            // Check.
            new OnChatMessage("You have (?<charges>.+) charges left before your Binding necklace disintegrates.").setDynamicallyCharges(),

            // Charge used.
            new OnChatMessage("You (partially succeed to )?bind the temple's power into (mud|lava|steam|dust|smoke|mist) runes\\.").decreaseCharges(1),

            // Fully used.
            new OnChatMessage("Your Binding necklace has disintegrated.").setFixedCharges(16),

            // Destroy.
            new OnMenuOptionClicked("Yes").consumer(() -> {
                final Optional<Widget> bindingNecklaceDestroyWidget = ChargesImprovedPlugin.getWidget(client, 584, 0, 2);
                if (bindingNecklaceDestroyWidget.isPresent() && bindingNecklaceDestroyWidget.get().getText().equals("Destroy necklace of binding?")) {
                    setCharges(16);
                }
            }),
        };
    }
}
