package tictac7x.charges.items.foods;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class F_Onions extends _Sack {
    public F_Onions(final Provider provider) {
        super("onions", new TriggerItem[]{
            new TriggerItem(ItemId.ONIONS_1).fixedCharges(1),
            new TriggerItem(ItemId.ONIONS_2).fixedCharges(2),
            new TriggerItem(ItemId.ONIONS_3).fixedCharges(3),
            new TriggerItem(ItemId.ONIONS_4).fixedCharges(4),
            new TriggerItem(ItemId.ONIONS_5).fixedCharges(5),
            new TriggerItem(ItemId.ONIONS_6).fixedCharges(6),
            new TriggerItem(ItemId.ONIONS_7).fixedCharges(7),
            new TriggerItem(ItemId.ONIONS_8).fixedCharges(8),
            new TriggerItem(ItemId.ONIONS_9).fixedCharges(9),
            new TriggerItem(ItemId.ONIONS_10).fixedCharges(10),
        }, provider);
    }
}
