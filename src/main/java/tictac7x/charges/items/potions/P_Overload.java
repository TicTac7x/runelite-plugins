package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Overload extends _Potion {
    public P_Overload(Provider provider) {
        super("overload", new TriggerItem[]{
            new TriggerItem(ItemId.OVERLOAD_1).fixedCharges(1),
            new TriggerItem(ItemId.OVERLOAD_2).fixedCharges(2),
            new TriggerItem(ItemId.OVERLOAD_3).fixedCharges(3),
            new TriggerItem(ItemId.OVERLOAD_4).fixedCharges(4),
        }, provider);
    }
}
