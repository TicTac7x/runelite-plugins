package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperEnergy extends _Potion {
    public P_SuperEnergy(Provider provider) {
        super("super_energy", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE2ENERGY).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE2ENERGY).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE2ENERGY).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE2ENERGY).fixedCharges(4),
        }, provider);
    }
}
