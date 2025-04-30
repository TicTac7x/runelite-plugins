package tictac7x.charges.items.potions;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class P_BlackWarlockMix extends _Potion {
    public P_BlackWarlockMix(final Provider provider) {
        super("black_warlock_mix", new TriggerItem[]{
            new TriggerItem(ItemId.BLACK_WARLOCK_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.BLACK_WARLOCK_MIX_2).fixedCharges(2),
        }, provider);
    }
}
