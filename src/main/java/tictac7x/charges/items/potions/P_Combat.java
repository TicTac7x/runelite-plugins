package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Combat extends _Potion {
    public P_Combat(Provider provider) {
        super("combat", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSECOMBAT).fixedCharges(1),
            new TriggerItem(ItemID._2DOSECOMBAT).fixedCharges(2),
            new TriggerItem(ItemID._3DOSECOMBAT).fixedCharges(3),
            new TriggerItem(ItemID._4DOSECOMBAT).fixedCharges(4),
        }, provider);
    }
}
