package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_GuthixBalance extends _Potion {
    public P_GuthixBalance(Provider provider) {
        super("guthix_balance", new TriggerItem[]{
            new TriggerItem(ItemID.BURGH_GUTHIX_BALANCE_1).fixedCharges(1),
            new TriggerItem(ItemID.BURGH_GUTHIX_BALANCE_2).fixedCharges(2),
            new TriggerItem(ItemID.BURGH_GUTHIX_BALANCE_3).fixedCharges(3),
            new TriggerItem(ItemID.BURGH_GUTHIX_BALANCE_4).fixedCharges(4),
        }, provider);
    }
}
