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
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;

public class J_AlchemistsAmulet extends ChargedItem {
    public J_AlchemistsAmulet(
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
        super(TicTac7xChargesImprovedConfig.alchemists_amulet, ItemID.ALCHEMISTS_AMULET, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ALCHEMISTS_AMULET).needsToBeEquipped(), //Uncharged 29988
            new TriggerItem(ItemID.ALCHEMISTS_AMULET_29990).needsToBeEquipped(), //Charged
            new TriggerItem(ItemID.ALCHEMISTS_AMULET_29992).needsToBeEquipped(), //???
        };

        this.triggers = new TriggerBase[] {
            // Check
            new OnChatMessage("Your Alchemist's amulet has (?<charges>.+) charges left.").setDynamicallyCharges(),

            // Charge
            new OnChatMessage("You apply an additional .+ charges to your Alchemist's amulet. It now has (?<charges>.+) charges in total.").setDynamicallyCharges(),
            new OnChatMessage("You apply (?<charges>.+) charges to your Alchemist's amulet.").setDynamicallyCharges(),

            // Uncharge
            new OnChatMessage("You uncharge your Alchemist's amulet, regaining .+ amulets of chemistry in the process.").setFixedCharges(0),

            // Use charge
            new OnChatMessage("Your Alchemist's amulet helps you create a 4-dose potion. It has (?<charges>.+) charges left.").setDynamicallyCharges(),
        };
    }
}
