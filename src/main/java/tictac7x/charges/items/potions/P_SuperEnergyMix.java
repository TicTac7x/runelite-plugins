package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperEnergyMix extends _Potion {
    public P_SuperEnergyMix(Provider provider) {
        super("super_energy_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE2ENERGY).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE2ENERGY).fixedCharges(2),
        }, provider);
    }
}
