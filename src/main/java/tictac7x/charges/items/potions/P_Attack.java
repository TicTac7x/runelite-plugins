package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Attack extends _Potion {
    public P_Attack(Provider provider) {
        super("attack", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE1ATTACK).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE1ATTACK).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE1ATTACK).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE1ATTACK).fixedCharges(4),
        }, provider);
    }
}
