package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Bastion extends _Potion {
    public P_Bastion(Provider provider) {
        super("bastion", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEBASTION).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEBASTION).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEBASTION).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEBASTION).fixedCharges(4),
        }, provider);
    }
}
