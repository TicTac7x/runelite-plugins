package tictac7x.charges.items.foods;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class F_Potatoes extends _Sack {
    public F_Potatoes(final Provider provider) {
        super("potatoes", new TriggerItem[]{
            new TriggerItem(ItemId.POTATOES_1).fixedCharges(1),
            new TriggerItem(ItemId.POTATOES_2).fixedCharges(2),
            new TriggerItem(ItemId.POTATOES_3).fixedCharges(3),
            new TriggerItem(ItemId.POTATOES_4).fixedCharges(4),
            new TriggerItem(ItemId.POTATOES_5).fixedCharges(5),
            new TriggerItem(ItemId.POTATOES_6).fixedCharges(6),
            new TriggerItem(ItemId.POTATOES_7).fixedCharges(7),
            new TriggerItem(ItemId.POTATOES_8).fixedCharges(8),
            new TriggerItem(ItemId.POTATOES_9).fixedCharges(9),
            new TriggerItem(ItemId.POTATOES_10).fixedCharges(10),
        }, provider);
    }
}
