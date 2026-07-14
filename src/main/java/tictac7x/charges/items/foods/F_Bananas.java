package tictac7x.charges.items.foods;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class F_Bananas extends _Basket {
    public F_Bananas(Provider provider) {
        super("bananas", new TriggerItem[]{
            new TriggerItem(ItemId.BANANAS_1).fixedCharges(1),
            new TriggerItem(ItemId.BANANAS_2).fixedCharges(2),
            new TriggerItem(ItemId.BANANAS_3).fixedCharges(3),
            new TriggerItem(ItemId.BANANAS_4).fixedCharges(4),
            new TriggerItem(ItemId.BANANAS_5).fixedCharges(5),
        }, provider);
    }   
}
