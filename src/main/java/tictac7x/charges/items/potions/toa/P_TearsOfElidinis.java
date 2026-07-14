package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_TearsOfElidinis extends _Potion {
    public P_TearsOfElidinis(Provider provider) {
        super("toa_tears_of_elidinis", new TriggerItem[]{
            new TriggerItem(ItemID.TOA_SUPPLY_PRAYER_1).fixedCharges(1),
            new TriggerItem(ItemID.TOA_SUPPLY_PRAYER_2).fixedCharges(2),
            new TriggerItem(ItemID.TOA_SUPPLY_PRAYER_3).fixedCharges(3),
            new TriggerItem(ItemID.TOA_SUPPLY_PRAYER_4).fixedCharges(4),
        }, provider);
    }
}
