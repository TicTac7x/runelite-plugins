package tictac7x.charges.items.foods;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class F_Onions extends _Sack {
    public F_Onions(Provider provider) {
        super("onions", new TriggerItem[]{
            new TriggerItem(ItemID.SACK_ONION_1).fixedCharges(1),
            new TriggerItem(ItemID.SACK_ONION_2).fixedCharges(2),
            new TriggerItem(ItemID.SACK_ONION_3).fixedCharges(3),
            new TriggerItem(ItemID.SACK_ONION_4).fixedCharges(4),
            new TriggerItem(ItemID.SACK_ONION_5).fixedCharges(5),
            new TriggerItem(ItemID.SACK_ONION_6).fixedCharges(6),
            new TriggerItem(ItemID.SACK_ONION_7).fixedCharges(7),
            new TriggerItem(ItemID.SACK_ONION_8).fixedCharges(8),
            new TriggerItem(ItemID.SACK_ONION_9).fixedCharges(9),
            new TriggerItem(ItemID.SACK_ONION_10).fixedCharges(10),
        }, provider);
    }
}
