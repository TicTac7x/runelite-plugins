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
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class J_BraceletOfFlamtaer extends ChargedItem {
    public J_BraceletOfFlamtaer(
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
        super(TicTac7xChargesImprovedConfig.bracelet_of_flamtaer, ItemID.FLAMTAER_BRACELET, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FLAMTAER_BRACELET).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[]{
            new OnChatMessage("Your Flamtaer bracelet helps you build the temple quicker. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),
            new OnChatMessage("Your flamtaer bracelet has (?<charges>.+) charges? left.").setDynamicallyCharges(),
            new OnChatMessage("Your Flamtaer bracelet helps you build the temple quicker. It then crumbles to dust.").setFixedCharges(80).notification("Your flamtaer bracelet crumbles to dust."),
            new OnChatMessage("The bracelet shatters. Your next Flamtaer bracelet will star afresh from 80 charges.").setFixedCharges(80)
        };
    }
}
