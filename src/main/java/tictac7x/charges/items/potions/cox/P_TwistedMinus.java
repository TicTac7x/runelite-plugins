package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_TwistedMinus extends _Potion {
    public P_TwistedMinus(Provider provider) {
        super("cox_twisted_minus", new TriggerItem[]{
            new TriggerItem(ItemID.RAIDS_VIAL_TWISTED_WEAK_1).fixedCharges(1),
            new TriggerItem(ItemID.RAIDS_VIAL_TWISTED_WEAK_2).fixedCharges(2),
            new TriggerItem(ItemID.RAIDS_VIAL_TWISTED_WEAK_3).fixedCharges(3),
            new TriggerItem(ItemID.RAIDS_VIAL_TWISTED_WEAK_4).fixedCharges(4),
        }, provider);
    }
}
