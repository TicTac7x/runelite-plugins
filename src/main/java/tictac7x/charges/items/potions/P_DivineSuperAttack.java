package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_DivineSuperAttack extends _Potion {
    public P_DivineSuperAttack(Provider provider) {
        super("divine_super_attack", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEDIVINEATTACK).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEDIVINEATTACK).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEDIVINEATTACK).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEDIVINEATTACK).fixedCharges(4),
        }, provider);
    }
}
