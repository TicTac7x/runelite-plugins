package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SuperRestore extends _Potion {
    public P_SuperRestore(final Provider provider) {
        super("super_restore", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_RESTORE_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_RESTORE_2).fixedCharges(2),
            new TriggerItem(ItemId.SUPER_RESTORE_3).fixedCharges(3),
            new TriggerItem(ItemId.SUPER_RESTORE_4).fixedCharges(4),
        }, provider);
    }
}
