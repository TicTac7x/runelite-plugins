package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_MenaphiteRemedy extends _Potion {
    public P_MenaphiteRemedy(Provider provider) {
        super("menaphite_remedy", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSESTATRENEWAL).fixedCharges(1),
            new TriggerItem(ItemID._2DOSESTATRENEWAL).fixedCharges(2),
            new TriggerItem(ItemID._3DOSESTATRENEWAL).fixedCharges(3),
            new TriggerItem(ItemID._4DOSESTATRENEWAL).fixedCharges(4),
        }, provider);
    }
}
