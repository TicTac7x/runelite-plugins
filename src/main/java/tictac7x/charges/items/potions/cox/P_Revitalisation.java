package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Revitalisation extends _Potion {
    public P_Revitalisation(Provider provider) {
        super("cox_revitalisation", new TriggerItem[]{
            new TriggerItem(ItemId.COX_REVITALISATION_1).fixedCharges(1),
            new TriggerItem(ItemId.COX_REVITALISATION_2).fixedCharges(2),
            new TriggerItem(ItemId.COX_REVITALISATION_3).fixedCharges(3),
            new TriggerItem(ItemId.COX_REVITALISATION_4).fixedCharges(4),
        }, provider);
    }
}
