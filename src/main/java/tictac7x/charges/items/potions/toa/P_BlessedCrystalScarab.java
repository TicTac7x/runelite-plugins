package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_BlessedCrystalScarab extends _Potion {
    public P_BlessedCrystalScarab(Provider provider) {
        super("toa_blessed_crystal_scarab", new TriggerItem[]{
            new TriggerItem(ItemID.TOA_SUPPLY_PRAYER_OVERTIME_1).fixedCharges(1),
            new TriggerItem(ItemID.TOA_SUPPLY_PRAYER_OVERTIME_2).fixedCharges(2),
        }, provider);
    }
}
