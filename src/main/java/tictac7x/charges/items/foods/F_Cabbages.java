package tictac7x.charges.items.foods;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class F_Cabbages extends _Sack {
    public F_Cabbages(Provider provider) {
        super("cabbages", new TriggerItem[]{
            new TriggerItem(ItemID.SACK_CABBAGE_1).fixedCharges(1),
            new TriggerItem(ItemID.SACK_CABBAGE_2).fixedCharges(2),
            new TriggerItem(ItemID.SACK_CABBAGE_3).fixedCharges(3),
            new TriggerItem(ItemID.SACK_CABBAGE_4).fixedCharges(4),
            new TriggerItem(ItemID.SACK_CABBAGE_5).fixedCharges(5),
            new TriggerItem(ItemID.SACK_CABBAGE_6).fixedCharges(6),
            new TriggerItem(ItemID.SACK_CABBAGE_7).fixedCharges(7),
            new TriggerItem(ItemID.SACK_CABBAGE_8).fixedCharges(8),
            new TriggerItem(ItemID.SACK_CABBAGE_9).fixedCharges(9),
            new TriggerItem(ItemID.SACK_CABBAGE_10).fixedCharges(10),
        }, provider);
    }
}
