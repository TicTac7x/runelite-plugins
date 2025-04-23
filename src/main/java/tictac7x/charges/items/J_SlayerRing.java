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
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class J_SlayerRing extends ChargedItem {
    public J_SlayerRing(
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
        super(TicTac7xChargesImprovedConfig.slayer_ring, ItemId.SLAYER_RING_8, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SLAYER_RING_1).fixedCharges(1),
            new TriggerItem(ItemId.SLAYER_RING_2).fixedCharges(2),
            new TriggerItem(ItemId.SLAYER_RING_3).fixedCharges(3),
            new TriggerItem(ItemId.SLAYER_RING_4).fixedCharges(4),
            new TriggerItem(ItemId.SLAYER_RING_5).fixedCharges(5),
            new TriggerItem(ItemId.SLAYER_RING_6).fixedCharges(6),
            new TriggerItem(ItemId.SLAYER_RING_7).fixedCharges(7),
            new TriggerItem(ItemId.SLAYER_RING_8).fixedCharges(8),
        };
    }
}
