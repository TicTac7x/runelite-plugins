package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_TearsOfElidinis extends _Potion {
    public P_TearsOfElidinis(Provider provider) {
        super("toa_tears_of_elidinis", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_TEARS_OF_ELIDINIS_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_TEARS_OF_ELIDINIS_2).fixedCharges(2),
            new TriggerItem(ItemId.TOA_TEARS_OF_ELIDINIS_3).fixedCharges(3),
            new TriggerItem(ItemId.TOA_TEARS_OF_ELIDINIS_4).fixedCharges(4),
        }, provider);
    }
}
