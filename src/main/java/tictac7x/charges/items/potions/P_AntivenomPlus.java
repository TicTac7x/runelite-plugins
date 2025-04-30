package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_AntivenomPlus extends _Potion {
    public P_AntivenomPlus(final Provider provider) {
        super("antivenom_plus", new TriggerItem[]{
            new TriggerItem(ItemId.ANTIVENOM_PLUS_1).fixedCharges(1),
            new TriggerItem(ItemId.ANTIVENOM_PLUS_2).fixedCharges(2),
            new TriggerItem(ItemId.ANTIVENOM_PLUS_3).fixedCharges(3),
            new TriggerItem(ItemId.ANTIVENOM_PLUS_4).fixedCharges(4),
        }, provider);
    }
}
