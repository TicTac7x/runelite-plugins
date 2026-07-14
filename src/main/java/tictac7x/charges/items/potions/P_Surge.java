package tictac7x.charges.items.potions;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class P_Surge extends _Potion {
    public P_Surge(Provider provider) {
        super("surge", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSESURGE).fixedCharges(1),
            new TriggerItem(ItemID._2DOSESURGE).fixedCharges(2),
            new TriggerItem(ItemID._3DOSESURGE).fixedCharges(3),
            new TriggerItem(ItemID._4DOSESURGE).fixedCharges(4),
        }, provider);
    }
}
