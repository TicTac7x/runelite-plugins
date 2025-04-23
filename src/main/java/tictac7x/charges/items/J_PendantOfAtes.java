package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.store.ItemId;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;

public class J_PendantOfAtes extends ChargedItem {
    public J_PendantOfAtes(
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ChatMessageManager chatMessageManager,
        final Notifier notifier,
        final TicTac7xChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(TicTac7xChargesImprovedConfig.pendant_of_ates, ItemId.PENDANT_OF_ATES, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.PENDANT_OF_ATES_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.PENDANT_OF_ATES),
        };

        this.triggers = new TriggerBase[]{
            // Check empty.
            new OnChatMessage("The pendant has no charges.").setFixedCharges(0).onItemClick(),

            // Check.
            new OnChatMessage("The pendant has (?<charges>.+) charges?.").setDynamicallyCharges().onItemClick(),

            // Charge.
            new OnChatMessage("You add .+ frozen tears? to your pendant. It now has (?<charges>.+) charges.").setDynamicallyCharges(),

            // Uncharge.
            new OnChatMessage("You uncharge your pendant by removing (?<charges>.+) frozen tears? from it.").decreaseDynamicallyCharges(),

            // Teleport.
            new OnGraphicChanged(2754).decreaseCharges(1),

            // Auto-charge.
            new OnChatMessage("The banker charges your Pendant of ates using (?<frozentear>.+)x Frozen tear.").matcherConsumer(m -> {
                final int frozenTear = Integer.parseInt(m.group("frozentear"));
                increaseCharges(frozenTear);
            }),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),
        };
    }
}
