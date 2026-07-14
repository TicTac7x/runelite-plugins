package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperRestore extends _Potion {
    public P_SuperRestore(Provider provider) {
        super("super_restore", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE2RESTORE).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE2RESTORE).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE2RESTORE).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE2RESTORE).fixedCharges(4),
        }, provider);
    }
}
