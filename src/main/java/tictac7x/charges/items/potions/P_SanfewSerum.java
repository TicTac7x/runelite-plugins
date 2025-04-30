package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SanfewSerum extends _Potion {
    public P_SanfewSerum(final Provider provider) {
        super("sanfew_serum", new TriggerItem[]{
            new TriggerItem(ItemId.SANFEW_SERUM_1).fixedCharges(1),
            new TriggerItem(ItemId.SANFEW_SERUM_2).fixedCharges(2),
            new TriggerItem(ItemId.SANFEW_SERUM_3).fixedCharges(3),
            new TriggerItem(ItemId.SANFEW_SERUM_4).fixedCharges(4),
        }, provider);
    }
}
