package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_MenaphiteRemedy extends _Potion {
    public P_MenaphiteRemedy(final Provider provider) {
        super("menaphite_remedy", new TriggerItem[]{
            new TriggerItem(ItemId.MENAPHITE_REMEDY_1).fixedCharges(1),
            new TriggerItem(ItemId.MENAPHITE_REMEDY_2).fixedCharges(2),
            new TriggerItem(ItemId.MENAPHITE_REMEDY_3).fixedCharges(3),
            new TriggerItem(ItemId.MENAPHITE_REMEDY_4).fixedCharges(4),
        }, provider);
    }
}
