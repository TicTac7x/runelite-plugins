package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_ExtendedAntifire extends _Potion {
    public P_ExtendedAntifire(Provider provider) {
        super("extended_antifire", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE2ANTIDRAGON).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE2ANTIDRAGON).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE2ANTIDRAGON).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE2ANTIDRAGON).fixedCharges(4),
        }, provider);
    }
}
