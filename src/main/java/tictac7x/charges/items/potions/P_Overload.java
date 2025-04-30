package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Overload extends _Potion {
    public P_Overload(final Provider provider) {
        super("overload", new TriggerItem[]{
            new TriggerItem(ItemId.OVERLOAD_1).fixedCharges(1),
            new TriggerItem(ItemId.OVERLOAD_2).fixedCharges(2),
            new TriggerItem(ItemId.OVERLOAD_3).fixedCharges(3),
            new TriggerItem(ItemId.OVERLOAD_4).fixedCharges(4),
        }, provider);
    }
}
