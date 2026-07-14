package tictac7x.charges.items.potions.cox;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;

public class P_Kodai extends _Potion {
    public P_Kodai(Provider provider) {
        super("cox_kodai", new TriggerItem[]{
            new TriggerItem(ItemID.RAIDS_VIAL_KODAI_1).fixedCharges(1),
            new TriggerItem(ItemID.RAIDS_VIAL_KODAI_2).fixedCharges(2),
            new TriggerItem(ItemID.RAIDS_VIAL_KODAI_3).fixedCharges(3),
            new TriggerItem(ItemID.RAIDS_VIAL_KODAI_4).fixedCharges(4),
        }, provider);
    }
}
