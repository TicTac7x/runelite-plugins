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
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;

public class J_RingOfDueling extends ChargedItem {
    public J_RingOfDueling(
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
        super(ChargesImprovedConfig.ring_of_dueling, ItemID.RING_OF_DUELING1, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_DUELING1).fixedCharges(1),
            new TriggerItem(ItemID.RING_OF_DUELING2).fixedCharges(2),
            new TriggerItem(ItemID.RING_OF_DUELING3).fixedCharges(3),
            new TriggerItem(ItemID.RING_OF_DUELING4).fixedCharges(4),
            new TriggerItem(ItemID.RING_OF_DUELING5).fixedCharges(5),
            new TriggerItem(ItemID.RING_OF_DUELING6).fixedCharges(6),
            new TriggerItem(ItemID.RING_OF_DUELING7).fixedCharges(7),
            new TriggerItem(ItemID.RING_OF_DUELING8).fixedCharges(8),
        };
    }
}
