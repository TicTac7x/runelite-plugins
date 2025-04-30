package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_RestoreMix extends _Potion {
    public P_RestoreMix(final Provider provider) {
        super("restore_mix", new TriggerItem[]{
            new TriggerItem(ItemId.RESTORE_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.RESTORE_MIX_2).fixedCharges(2),
        }, provider);
    }
}
