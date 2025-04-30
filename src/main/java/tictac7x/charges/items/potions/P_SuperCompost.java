package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SuperCompost extends _Potion {
    public P_SuperCompost(final Provider provider) {
        super("super_compost", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_COMPOST_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_COMPOST_2).fixedCharges(2),
            new TriggerItem(ItemId.SUPER_COMPOST_3).fixedCharges(3),
            new TriggerItem(ItemId.SUPER_COMPOST_4).fixedCharges(4),
        }, provider);
    }
}
