package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Antipoison extends _Potion {
    public P_Antipoison(Provider provider) {
        super("antipoison", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEANTIPOISON).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEANTIPOISON).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEANTIPOISON).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEANTIPOISON).fixedCharges(4),
        }, provider);
    }
}
