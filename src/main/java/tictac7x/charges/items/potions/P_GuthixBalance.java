package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_GuthixBalance extends _Potion {
    public P_GuthixBalance(final Provider provider) {
        super("guthix_balance", new TriggerItem[]{
            new TriggerItem(ItemId.GUTHIX_BALANCE_1).fixedCharges(1),
            new TriggerItem(ItemId.GUTHIX_BALANCE_2).fixedCharges(2),
            new TriggerItem(ItemId.GUTHIX_BALANCE_3).fixedCharges(3),
            new TriggerItem(ItemId.GUTHIX_BALANCE_4).fixedCharges(4),
        }, provider);
    }
}
