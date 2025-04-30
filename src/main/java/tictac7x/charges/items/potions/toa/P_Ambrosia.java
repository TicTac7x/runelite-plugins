package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.items.potions._Potion;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Ambrosia extends _Potion {
    public P_Ambrosia(final Provider provider) {
        super("toa_ambrosia", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_AMBROSIA_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_AMBROSIA_2).fixedCharges(2),
        }, provider);
    }
}
