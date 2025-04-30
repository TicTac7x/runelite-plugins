package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Serum_208 extends _Potion {
    public P_Serum_208(final Provider provider) {
        super("serum_208", new TriggerItem[]{
            new TriggerItem(ItemId.SERUM_208_1).fixedCharges(1),
            new TriggerItem(ItemId.SERUM_208_2).fixedCharges(2),
            new TriggerItem(ItemId.SERUM_208_3).fixedCharges(3),
            new TriggerItem(ItemId.SERUM_208_4).fixedCharges(4),
        }, provider);
    }
}
