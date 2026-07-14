package tictac7x.charges.items.foods;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class F_Potatoes extends _Sack {
    public F_Potatoes(Provider provider) {
        super("potatoes", new TriggerItem[]{
            new TriggerItem(ItemID.SACK_POTATO_1).fixedCharges(1),
            new TriggerItem(ItemID.SACK_POTATO_2).fixedCharges(2),
            new TriggerItem(ItemID.SACK_POTATO_3).fixedCharges(3),
            new TriggerItem(ItemID.SACK_POTATO_4).fixedCharges(4),
            new TriggerItem(ItemID.SACK_POTATO_5).fixedCharges(5),
            new TriggerItem(ItemID.SACK_POTATO_6).fixedCharges(6),
            new TriggerItem(ItemID.SACK_POTATO_7).fixedCharges(7),
            new TriggerItem(ItemID.SACK_POTATO_8).fixedCharges(8),
            new TriggerItem(ItemID.SACK_POTATO_9).fixedCharges(9),
            new TriggerItem(ItemID.SACK_POTATO_10).fixedCharges(10),
        }, provider);
    }
}
