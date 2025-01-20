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

public class J_RingOfPursuit extends ChargedItem {
    public J_RingOfPursuit(
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
        super(TicTac7xChargesImprovedConfig.ring_of_pursuit, ItemID.RING_OF_PURSUIT, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_PURSUIT).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your ring of pursuit has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use.
            new OnChatMessage("Your ring of pursuit reveals the entire trail to you. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use last charge.
            new OnChatMessage("Your ring of pursuit reveals the entire trail to you. It then crumbles to dust.").notification().setFixedCharges(10),

            // Destroy.
            new OnChatMessage("The ring shatters. Your next ring of pursuit will start afresh from 10 charges.").setFixedCharges(10),
        };
    }
}
