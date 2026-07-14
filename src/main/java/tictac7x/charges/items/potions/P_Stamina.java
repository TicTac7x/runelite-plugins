package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Stamina extends _Potion {
    public P_Stamina(Provider provider) {
        super("stamina", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSESTAMINA).fixedCharges(1),
            new TriggerItem(ItemID._2DOSESTAMINA).fixedCharges(2),
            new TriggerItem(ItemID._3DOSESTAMINA).fixedCharges(3),
            new TriggerItem(ItemID._4DOSESTAMINA).fixedCharges(4),
        }, provider);
    }
}
