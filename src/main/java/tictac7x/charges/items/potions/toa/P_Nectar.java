package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Nectar extends _Potion {
    public P_Nectar(Provider provider) {
        super("toa_nectar", new TriggerItem[]{
            new TriggerItem(ItemID.TOA_SUPPLY_HEAL_1).fixedCharges(1),
            new TriggerItem(ItemID.TOA_SUPPLY_HEAL_2).fixedCharges(2),
            new TriggerItem(ItemID.TOA_SUPPLY_HEAL_3).fixedCharges(3),
            new TriggerItem(ItemID.TOA_SUPPLY_HEAL_4).fixedCharges(4),
        }, provider);
    }
}
