package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_DivineRanging extends _Potion {
    public P_DivineRanging(final Provider provider) {
        super("divine_ranging", new TriggerItem[]{
            new TriggerItem(ItemId.DIVINE_RANGING_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.DIVINE_RANGING_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.DIVINE_RANGING_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.DIVINE_RANGING_POTION_4).fixedCharges(4),
        }, provider);
    }
}
