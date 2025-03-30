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

public class J_AmuletOfChemistry extends ChargedItem {
    public J_AmuletOfChemistry(
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
        super(TicTac7xChargesImprovedConfig.amulet_of_chemistry, ItemID.AMULET_OF_CHEMISTRY, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.AMULET_OF_CHEMISTRY).needsToBeEquipped()
        };

        this.triggers = new TriggerBase[] {
            // Check
            new OnChatMessage("Your amulet of chemistry has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use charge
            new OnChatMessage("Your amulet of chemistry helps you create a 4-dose potion. It then crumbles to dust.").setFixedCharges(5),
            new OnChatMessage("Your amulet of chemistry helps you create a 4-dose potion. It has one charge left.").setFixedCharges(1),
            new OnChatMessage("Your amulet of chemistry helps you create a 4-dose potion. It has (?<charges>.+) charges left.").setDynamicallyCharges(),

            // Status from break dialog
            new OnWidgetLoaded(219, 1, 0).text("Status: (?<charges>.+) charges? left.").setDynamically().onItemClick(),

            // Break
            new OnChatMessage("The amulet shatters. Your next amulet of chemistry will start afresh from (?<charges>.+) charges.").setDynamicallyCharges(),
        };
    }
}
