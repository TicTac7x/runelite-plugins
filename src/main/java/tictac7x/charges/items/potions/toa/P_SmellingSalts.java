package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_SmellingSalts extends _Potion {
    public P_SmellingSalts(Provider provider) {
        super("toa_smelling_salts", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_SMELLING_SALTS_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_SMELLING_SALTS_2).fixedCharges(2),
        }, provider);
    }
}
