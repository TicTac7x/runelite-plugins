package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_EnergyMix extends _Potion {
    public P_EnergyMix(Provider provider) {
        super("energy_mix", new TriggerItem[]{
            new TriggerItem(ItemId.ENERGY_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.ENERGY_MIX_2).fixedCharges(2),
        }, provider);
    }
}
