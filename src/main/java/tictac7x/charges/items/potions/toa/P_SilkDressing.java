package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SilkDressing extends _Potion {
    public P_SilkDressing(Provider provider) {
        super("toa_silk_dressing", new TriggerItem[]{
            new TriggerItem(ItemID.TOA_SUPPLY_HEAL_OVERTIME_1).fixedCharges(1),
            new TriggerItem(ItemID.TOA_SUPPLY_HEAL_OVERTIME_2).fixedCharges(2),
        }, provider);
    }
}
