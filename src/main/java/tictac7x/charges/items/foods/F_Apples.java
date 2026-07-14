package tictac7x.charges.items.foods;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class F_Apples extends _Basket {
    public F_Apples(Provider provider) {
        super("apples", new TriggerItem[]{
            new TriggerItem(ItemId.APPLES_1).fixedCharges(1),
            new TriggerItem(ItemId.APPLES_2).fixedCharges(2),
            new TriggerItem(ItemId.APPLES_3).fixedCharges(3),
            new TriggerItem(ItemId.APPLES_4).fixedCharges(4),
            new TriggerItem(ItemId.APPLES_5).fixedCharges(5),
        }, provider);
    }
}
