package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Battlemage extends _Potion {
    public P_Battlemage(Provider provider) {
        super("battlemage", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEBATTLEMAGE).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEBATTLEMAGE).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEBATTLEMAGE).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEBATTLEMAGE).fixedCharges(4),
        }, provider);
    }
}
