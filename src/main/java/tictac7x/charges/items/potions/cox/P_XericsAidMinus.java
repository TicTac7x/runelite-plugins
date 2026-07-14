package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_XericsAidMinus extends _Potion {
    public P_XericsAidMinus(Provider provider) {
        super("cox_xerics_aid_minus", new TriggerItem[]{
            new TriggerItem(ItemID.RAIDS_VIAL_XERICAID_WEAK_1).fixedCharges(1),
            new TriggerItem(ItemID.RAIDS_VIAL_XERICAID_WEAK_2).fixedCharges(2),
            new TriggerItem(ItemID.RAIDS_VIAL_XERICAID_WEAK_3).fixedCharges(3),
            new TriggerItem(ItemID.RAIDS_VIAL_XERICAID_WEAK_4).fixedCharges(4),
        }, provider);
    }
}
