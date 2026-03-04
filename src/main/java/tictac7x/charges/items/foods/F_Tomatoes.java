package tictac7x.charges.items.foods;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class F_Tomatoes extends _Basket {
    public F_Tomatoes(final Provider provider) {
        super("potatoes", new TriggerItem[]{
            new TriggerItem(ItemId.TOMATOES_1).fixedCharges(1),
            new TriggerItem(ItemId.TOMATOES_2).fixedCharges(2),
            new TriggerItem(ItemId.TOMATOES_3).fixedCharges(3),
            new TriggerItem(ItemId.TOMATOES_4).fixedCharges(4),
            new TriggerItem(ItemId.TOMATOES_5).fixedCharges(5),
        }, provider);
    }   
}
