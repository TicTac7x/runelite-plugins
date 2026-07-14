package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Goading extends _Potion {
    public P_Goading(Provider provider) {
        super("goading", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEGOADING).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEGOADING).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEGOADING).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEGOADING).fixedCharges(4),
        }, provider);
    }
}
