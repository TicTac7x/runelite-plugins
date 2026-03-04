package tictac7x.charges.items.foods;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class F_Cabbages extends _Sack {
    public F_Cabbages(final Provider provider) {
        super("cabbages", new TriggerItem[]{
            new TriggerItem(ItemId.CABBAGES_1).fixedCharges(1),
            new TriggerItem(ItemId.CABBAGES_2).fixedCharges(2),
            new TriggerItem(ItemId.CABBAGES_3).fixedCharges(3),
            new TriggerItem(ItemId.CABBAGES_4).fixedCharges(4),
            new TriggerItem(ItemId.CABBAGES_5).fixedCharges(5),
            new TriggerItem(ItemId.CABBAGES_6).fixedCharges(6),
            new TriggerItem(ItemId.CABBAGES_7).fixedCharges(7),
            new TriggerItem(ItemId.CABBAGES_8).fixedCharges(8),
            new TriggerItem(ItemId.CABBAGES_9).fixedCharges(9),
            new TriggerItem(ItemId.CABBAGES_10).fixedCharges(10),
        }, provider);
    }
}
