package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Antidote extends _Potion {
    public P_Antidote(final Provider provider) {
        super("antidote", new TriggerItem[]{
            new TriggerItem(ItemId.ANTIDOTE_1).fixedCharges(1),
            new TriggerItem(ItemId.ANTIDOTE_2).fixedCharges(2),
            new TriggerItem(ItemId.ANTIDOTE_3).fixedCharges(3),
            new TriggerItem(ItemId.ANTIDOTE_4).fixedCharges(4),
        }, provider);
    }
}
