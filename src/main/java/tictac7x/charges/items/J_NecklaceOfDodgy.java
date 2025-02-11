package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnWidgetLoaded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class J_NecklaceOfDodgy extends ChargedItem {
    public J_NecklaceOfDodgy(
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
        super(TicTac7xChargesImprovedConfig.dodgy_necklace, ItemID.DODGY_NECKLACE, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.DODGY_NECKLACE).needsToBeEquipped(),
        };
        
        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your dodgy necklace has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Protects.
            new OnChatMessage("Your dodgy necklace protects you. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Breaks.
            new OnChatMessage("Your dodgy necklace protects you. It then crumbles to dust.").setFixedCharges(10).notification("Your dodgy necklace crumbles to dust."),

            // Break.
            new OnChatMessage("The necklace shatters. Your next dodgy necklace will start afresh from (?<charges>.+) charges.").setDynamicallyCharges(),

            new OnWidgetLoaded(219, 1, 0).text("Status: (?<charges>.+) charges? left.").setDynamically().onMenuOption("Break").onMenuTarget("Dodgy necklace"),
        };
    }
}
