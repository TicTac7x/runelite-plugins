package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_XericsAid extends _Potion {
    public P_XericsAid(Provider provider) {
        super("cox_xerics_aid", new TriggerItem[]{
            new TriggerItem(ItemID.RAIDS_VIAL_XERICAID_1).fixedCharges(1),
            new TriggerItem(ItemID.RAIDS_VIAL_XERICAID_2).fixedCharges(2),
            new TriggerItem(ItemID.RAIDS_VIAL_XERICAID_3).fixedCharges(3),
            new TriggerItem(ItemID.RAIDS_VIAL_XERICAID_4).fixedCharges(4),
        }, provider);
    }
}
