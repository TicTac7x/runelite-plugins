package tictac7x.charges.items.potions.cox;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;

public class P_ElderPlus extends _Potion {
    public P_ElderPlus(Provider provider) {
        super("cox_elder_plus", new TriggerItem[]{
            new TriggerItem(ItemID.RAIDS_VIAL_ELDER_STRONG_1).fixedCharges(1),
            new TriggerItem(ItemID.RAIDS_VIAL_ELDER_STRONG_2).fixedCharges(2),
            new TriggerItem(ItemID.RAIDS_VIAL_ELDER_STRONG_3).fixedCharges(3),
            new TriggerItem(ItemID.RAIDS_VIAL_ELDER_STRONG_4).fixedCharges(4),
        }, provider);
    }
}
