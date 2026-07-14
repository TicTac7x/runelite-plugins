package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Defence extends _Potion {
    public P_Defence(Provider provider) {
        super("defence", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE1DEFENSE).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE1DEFENSE).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE1DEFENSE).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE1DEFENSE).fixedCharges(4),
        }, provider);
    }
}
