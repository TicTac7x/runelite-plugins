package tictac7x.charges.items.potions;

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
import tictac7x.charges.store.ItemId;
import tictac7x.charges.store.Store;

public class P_Combat extends _Potion {
    public P_Combat(
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
        super("combat", new TriggerItem[]{
            new TriggerItem(ItemId.COMBAT_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.COMBAT_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.COMBAT_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.COMBAT_POTION_4).fixedCharges(4),
        }, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
    }
}
