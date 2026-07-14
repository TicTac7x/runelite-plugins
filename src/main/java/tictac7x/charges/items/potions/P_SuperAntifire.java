package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SuperAntifire extends _Potion {
    public P_SuperAntifire(Provider provider) {
        super("super_antifire", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE3ANTIDRAGON).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE3ANTIDRAGON).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE3ANTIDRAGON).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE3ANTIDRAGON).fixedCharges(4),
        }, provider);
    }
}
