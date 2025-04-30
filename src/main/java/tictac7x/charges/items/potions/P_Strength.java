package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Strength extends _Potion {
    public P_Strength(final Provider provider) {
        super("strength", new TriggerItem[]{
            new TriggerItem(ItemId.STRENGTH_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.STRENGTH_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.STRENGTH_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.STRENGTH_POTION_4).fixedCharges(4),
        }, provider);
    }
}
