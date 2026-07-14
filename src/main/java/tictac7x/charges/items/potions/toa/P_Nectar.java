package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Nectar extends _Potion {
    public P_Nectar(Provider provider) {
        super("toa_nectar", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_NECTAR_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_NECTAR_2).fixedCharges(2),
            new TriggerItem(ItemId.TOA_NECTAR_3).fixedCharges(3),
            new TriggerItem(ItemId.TOA_NECTAR_4).fixedCharges(4),
        }, provider);
    }
}
