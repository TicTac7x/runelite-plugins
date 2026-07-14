package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperDefence extends _Potion {
    public P_SuperDefence(Provider provider) {
        super("super_defence", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE2DEFENSE).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE2DEFENSE).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE2DEFENSE).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE2DEFENSE).fixedCharges(4),
        }, provider);
    }
}
