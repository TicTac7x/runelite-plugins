package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SuperDefence extends _Potion {
    public P_SuperDefence(final Provider provider) {
        super("super_defence", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_DEFENCE_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_DEFENCE_2).fixedCharges(2),
            new TriggerItem(ItemId.SUPER_DEFENCE_3).fixedCharges(3),
            new TriggerItem(ItemId.SUPER_DEFENCE_4).fixedCharges(4),
        }, provider);
    }
}
