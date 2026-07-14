package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SuperAttack extends _Potion {
    public P_SuperAttack(Provider provider) {
        super("super_attack", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE2ATTACK).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE2ATTACK).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE2ATTACK).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE2ATTACK).fixedCharges(4),
        }, provider);
    }
}
