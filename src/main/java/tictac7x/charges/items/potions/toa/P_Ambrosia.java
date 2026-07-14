package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Ambrosia extends _Potion {
    public P_Ambrosia(Provider provider) {
        super("toa_ambrosia", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_AMBROSIA_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_AMBROSIA_2).fixedCharges(2),
        }, provider);
    }
}
