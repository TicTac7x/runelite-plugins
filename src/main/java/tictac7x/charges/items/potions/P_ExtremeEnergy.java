package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_ExtremeEnergy extends _Potion {
    public P_ExtremeEnergy(Provider provider) {
        super("extreme_energy", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE3ENERGY).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE3ENERGY).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE3ENERGY).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE3ENERGY).fixedCharges(4),
        }, provider);
    }
}
