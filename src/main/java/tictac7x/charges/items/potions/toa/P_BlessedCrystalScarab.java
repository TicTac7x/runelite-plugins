package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.items.potions._Potion;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_BlessedCrystalScarab extends _Potion {
    public P_BlessedCrystalScarab(final Provider provider) {
        super("toa_blessed_crystal_scarab", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_BLESSED_CRYSTAL_SCARAB_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_BLESSED_CRYSTAL_SCARAB_2).fixedCharges(2),
        }, provider);
    }
}
