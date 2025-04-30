package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SuperAntipoison extends _Potion {
    public P_SuperAntipoison(final Provider provider) {
        super("super_antipoison", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_ANTIPOISON_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_ANTIPOISON_2).fixedCharges(2),
            new TriggerItem(ItemId.SUPER_ANTIPOISON_3).fixedCharges(3),
            new TriggerItem(ItemId.SUPER_ANTIPOISON_4).fixedCharges(4),
        }, provider);
    }
}
