package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_DivineSuperDefence extends _Potion {
    public P_DivineSuperDefence(Provider provider) {
        super("divine_super_defence", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEDIVINEDEFENCE).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEDIVINEDEFENCE).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEDIVINEDEFENCE).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEDIVINEDEFENCE).fixedCharges(4),
        }, provider);
    }
}
