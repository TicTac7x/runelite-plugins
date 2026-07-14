package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_OverloadPlus extends _Potion {
    public P_OverloadPlus(Provider provider) {
        super("cox_overload_plus", new TriggerItem[]{
            new TriggerItem(ItemId.COX_OVERLOAD_PLUS_1).fixedCharges(1),
            new TriggerItem(ItemId.COX_OVERLOAD_PLUS_2).fixedCharges(2),
            new TriggerItem(ItemId.COX_OVERLOAD_PLUS_3).fixedCharges(3),
            new TriggerItem(ItemId.COX_OVERLOAD_PLUS_4).fixedCharges(4),
        }, provider);
    }
}
