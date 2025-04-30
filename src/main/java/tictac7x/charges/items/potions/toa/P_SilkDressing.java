package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.items.potions._Potion;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SilkDressing extends _Potion {
    public P_SilkDressing(final Provider provider) {
        super("toa_silk_dressing", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_SILK_DRESSING_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_SILK_DRESSING_2).fixedCharges(2),
        }, provider);
    }
}
