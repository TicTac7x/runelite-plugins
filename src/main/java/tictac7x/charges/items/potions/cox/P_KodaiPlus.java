package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_KodaiPlus extends _Potion {
    public P_KodaiPlus(Provider provider) {
        super("cox_kodai_plus", new TriggerItem[]{
            new TriggerItem(ItemID.RAIDS_VIAL_KODAI_STRONG_1).fixedCharges(1),
            new TriggerItem(ItemID.RAIDS_VIAL_KODAI_STRONG_2).fixedCharges(2),
            new TriggerItem(ItemID.RAIDS_VIAL_KODAI_STRONG_3).fixedCharges(3),
            new TriggerItem(ItemID.RAIDS_VIAL_KODAI_STRONG_4).fixedCharges(4),
        }, provider);
    }
}
