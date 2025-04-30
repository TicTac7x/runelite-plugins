package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Absorption extends _Potion {
    public P_Absorption(final Provider provider) {
        super("absorption", new TriggerItem[]{
            new TriggerItem(ItemId.ABSORPTION_1).fixedCharges(1),
            new TriggerItem(ItemId.ABSORPTION_2).fixedCharges(2),
            new TriggerItem(ItemId.ABSORPTION_3).fixedCharges(3),
            new TriggerItem(ItemId.ABSORPTION_4).fixedCharges(4),
        }, provider);
    }
}
