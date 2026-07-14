package tictac7x.charges.items.foods;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class F_Oranges extends _Basket {
    public F_Oranges(Provider provider) {
        super("oranges", new TriggerItem[]{
            new TriggerItem(ItemId.ORANGES_1).fixedCharges(1),
            new TriggerItem(ItemId.ORANGES_2).fixedCharges(2),
            new TriggerItem(ItemId.ORANGES_3).fixedCharges(3),
            new TriggerItem(ItemId.ORANGES_4).fixedCharges(4),
            new TriggerItem(ItemId.ORANGES_5).fixedCharges(5),
        }, provider);
    }   
}
