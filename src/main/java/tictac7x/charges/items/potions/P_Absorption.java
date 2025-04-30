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
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.Store;

public class P_Absorption extends _Potion {
    public P_Absorption(final Provider provider) {
        super("absorption", new TriggerItem[]{
            new TriggerItem(ItemId.ABSORPTION_1).fixedCharges(1),
            new TriggerItem(ItemId.ABSORPTION_2).fixedCharges(2),
            new TriggerItem(ItemId.ABSORPTION_3).fixedCharges(3),
            new TriggerItem(ItemId.ABSORPTION_4).fixedCharges(4),
        }, provider);
    }
}
