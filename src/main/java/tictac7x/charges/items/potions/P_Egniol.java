package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Egniol extends _Potion {
    public P_Egniol(final Provider provider) {
        super("egniol", new TriggerItem[]{
            new TriggerItem(ItemId.EGNIOL_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.EGNIOL_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.EGNIOL_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.EGNIOL_POTION_4).fixedCharges(4),
        }, provider);
    }
}
