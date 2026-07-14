package tictac7x.charges.items.foods;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class F_Strawberries extends _Basket {
    public F_Strawberries(Provider provider) {
        super("strawberries", new TriggerItem[]{
            new TriggerItem(ItemId.STRAWBERRIES_1).fixedCharges(1),
            new TriggerItem(ItemId.STRAWBERRIES_2).fixedCharges(2),
            new TriggerItem(ItemId.STRAWBERRIES_3).fixedCharges(3),
            new TriggerItem(ItemId.STRAWBERRIES_4).fixedCharges(4),
            new TriggerItem(ItemId.STRAWBERRIES_5).fixedCharges(5),
        }, provider);
    }   
}
