package tictac7x.charges.items.potions.toa;

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
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.Store;

public class P_SmellingSalts extends _Potion {
    public P_SmellingSalts(final Provider provider) {
        super("toa_smelling_salts", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_SMELLING_SALTS_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_SMELLING_SALTS_2).fixedCharges(2),
        }, provider);
    }
}
