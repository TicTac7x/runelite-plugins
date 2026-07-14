package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Antifire extends _Potion {
    public P_Antifire(Provider provider) {
        super("antifire", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE1ANTIDRAGON).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE1ANTIDRAGON).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE1ANTIDRAGON).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE1ANTIDRAGON).fixedCharges(4),
        }, provider);
    }
}
