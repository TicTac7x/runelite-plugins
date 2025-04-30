package tictac7x.charges.items.potions.cox;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.items.potions._Potion;
import tictac7x.charges.store.ItemId;
import tictac7x.charges.store.Store;

public class P_Elder extends _Potion {
    public P_Elder(
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
        super("cox_elder", new TriggerItem[]{
            new TriggerItem(ItemId.COX_ELDER_1).fixedCharges(1),
            new TriggerItem(ItemId.COX_ELDER_2).fixedCharges(2),
            new TriggerItem(ItemId.COX_ELDER_3).fixedCharges(3),
            new TriggerItem(ItemId.COX_ELDER_4).fixedCharges(4),
        }, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
    }
}
