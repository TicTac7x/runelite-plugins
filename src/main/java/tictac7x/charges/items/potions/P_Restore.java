package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Restore extends _Potion {
    public P_Restore(Provider provider) {
        super("restore", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSESTATRESTORE).fixedCharges(1),
            new TriggerItem(ItemID._2DOSESTATRESTORE).fixedCharges(2),
            new TriggerItem(ItemID._3DOSESTATRESTORE).fixedCharges(3),
            new TriggerItem(ItemID._4DOSESTATRESTORE).fixedCharges(4),
        }, provider);
    }
}
