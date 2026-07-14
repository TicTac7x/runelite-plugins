package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperHunting extends _Potion {
    public P_SuperHunting(Provider provider) {
        super("super_hunting", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE2HUNTING).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE2HUNTING).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE2HUNTING).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE2HUNTING).fixedCharges(4),
        }, provider);
    }
}
