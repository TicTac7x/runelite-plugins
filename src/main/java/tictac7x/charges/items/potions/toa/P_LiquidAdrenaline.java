package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_LiquidAdrenaline extends _Potion {
    public P_LiquidAdrenaline(Provider provider) {
        super("toa_liquid_adrenaline", new TriggerItem[]{
            new TriggerItem(ItemID.TOA_SUPPLY_ENERGY_1).fixedCharges(1),
            new TriggerItem(ItemID.TOA_SUPPLY_ENERGY_2).fixedCharges(2),
        }, provider);
    }
}
