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
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class U_FungicideSpray extends ChargedItem {
    public U_FungicideSpray(
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
        super(TicTac7xChargesImprovedConfig.fungicide_spray, ItemID.FUNGICIDE_SPRAY_0, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_0).fixedCharges(0),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_1).fixedCharges(1),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_2).fixedCharges(2),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_3).fixedCharges(3),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_4).fixedCharges(4),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_5).fixedCharges(5),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_6).fixedCharges(6),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_7).fixedCharges(7),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_8).fixedCharges(8),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_9).fixedCharges(9),
            new TriggerItem(ItemID.FUNGICIDE_SPRAY_10).fixedCharges(10),
        };
    }
}
