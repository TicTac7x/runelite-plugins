package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_DivineBastion extends _Potion {
    public P_DivineBastion(Provider provider) {
        super("divine_bastion", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEDIVINEBASTION).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEDIVINEBASTION).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEDIVINEBASTION).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEDIVINEBASTION).fixedCharges(4),
        }, provider);
    }
}
