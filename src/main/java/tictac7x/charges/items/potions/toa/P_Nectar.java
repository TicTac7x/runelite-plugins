package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.items.potions._Potion;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Nectar extends _Potion {
    public P_Nectar(final Provider provider) {
        super("toa_nectar", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_NECTAR_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_NECTAR_2).fixedCharges(2),
            new TriggerItem(ItemId.TOA_NECTAR_3).fixedCharges(3),
            new TriggerItem(ItemId.TOA_NECTAR_4).fixedCharges(4),
        }, provider);
    }
}
