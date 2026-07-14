package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_EnergyMix extends _Potion {
    public P_EnergyMix(Provider provider) {
        super("energy_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE1ENERGY).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE1ENERGY).fixedCharges(2),
        }, provider);
    }
}
