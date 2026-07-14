package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_ExtendedSuperAntifire extends _Potion {
    public P_ExtendedSuperAntifire(Provider provider) {
        super("extended_super_antifire", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE4ANTIDRAGON).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE4ANTIDRAGON).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE4ANTIDRAGON).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE4ANTIDRAGON).fixedCharges(4),
        }, provider);
    }
}
