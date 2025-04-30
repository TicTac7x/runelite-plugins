package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Fishing extends _Potion {
    public P_Fishing(final Provider provider) {
        super("fishing", new TriggerItem[]{
            new TriggerItem(ItemId.FISHING_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.FISHING_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.FISHING_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.FISHING_POTION_4).fixedCharges(4),
        }, provider);
    }
}
