package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Magic extends _Potion {
    public P_Magic(Provider provider) {
        super("magic", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE1MAGIC).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE1MAGIC).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE1MAGIC).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE1MAGIC).fixedCharges(4),
        }, provider);
    }
}
