package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SuperStrength extends _Potion {
    public P_SuperStrength(final Provider provider) {
        super("super_strength", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_STRENGTH_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_STRENGTH_2).fixedCharges(2),
            new TriggerItem(ItemId.SUPER_STRENGTH_3).fixedCharges(3),
            new TriggerItem(ItemId.SUPER_STRENGTH_4).fixedCharges(4),
        }, provider);
    }
}
