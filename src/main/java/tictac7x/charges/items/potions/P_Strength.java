package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Strength extends _Potion {
    public P_Strength(Provider provider) {
        super("strength", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE1STRENGTH).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE1STRENGTH).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE1STRENGTH).fixedCharges(3),
            new TriggerItem(ItemID.STRENGTH4).fixedCharges(4),
        }, provider);
    }
}
