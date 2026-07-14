package tictac7x.charges.items.potions.cox;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;

public class P_ElderMinus extends _Potion {
    public P_ElderMinus(Provider provider) {
        super("cox_elder_minus", new TriggerItem[]{
            new TriggerItem(ItemID.RAIDS_VIAL_ELDER_WEAK_1).fixedCharges(1),
            new TriggerItem(ItemID.RAIDS_VIAL_ELDER_WEAK_2).fixedCharges(2),
            new TriggerItem(ItemID.RAIDS_VIAL_ELDER_WEAK_3).fixedCharges(3),
            new TriggerItem(ItemID.RAIDS_VIAL_ELDER_WEAK_4).fixedCharges(4),
        }, provider);
    }
}
