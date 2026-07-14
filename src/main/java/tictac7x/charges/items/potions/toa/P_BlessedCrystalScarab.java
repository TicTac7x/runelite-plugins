package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_BlessedCrystalScarab extends _Potion {
    public P_BlessedCrystalScarab(Provider provider) {
        super("toa_blessed_crystal_scarab", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_BLESSED_CRYSTAL_SCARAB_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_BLESSED_CRYSTAL_SCARAB_2).fixedCharges(2),
        }, provider);
    }
}
