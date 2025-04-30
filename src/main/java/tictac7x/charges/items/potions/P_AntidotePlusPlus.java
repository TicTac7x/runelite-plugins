package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_AntidotePlusPlus extends _Potion {
    public P_AntidotePlusPlus(final Provider provider) {
        super("antidote_plus_plus", new TriggerItem[]{
            new TriggerItem(ItemId.ANTIDOTE_PLUS_PLUS_1).fixedCharges(1),
            new TriggerItem(ItemId.ANTIDOTE_PLUS_PLUS_2).fixedCharges(2),
            new TriggerItem(ItemId.ANTIDOTE_PLUS_PLUS_3).fixedCharges(3),
            new TriggerItem(ItemId.ANTIDOTE_PLUS_PLUS_4).fixedCharges(4),
        }, provider);
    }
}
