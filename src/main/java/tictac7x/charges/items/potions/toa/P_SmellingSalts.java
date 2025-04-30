package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.items.potions._Potion;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SmellingSalts extends _Potion {
    public P_SmellingSalts(final Provider provider) {
        super("toa_smelling_salts", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_SMELLING_SALTS_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_SMELLING_SALTS_2).fixedCharges(2),
        }, provider);
    }
}
