package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class U_WateringCan extends ChargedItem {
    public U_WateringCan(Provider provider) {
        super(TicTac7xChargesImprovedConfig.watering_can, ItemID.WATERING_CAN_0, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.WATERING_CAN_0).fixedCharges(0),
            new TriggerItem(ItemID.WATERING_CAN_1).fixedCharges(1),
            new TriggerItem(ItemID.WATERING_CAN_2).fixedCharges(2),
            new TriggerItem(ItemID.WATERING_CAN_3).fixedCharges(3),
            new TriggerItem(ItemID.WATERING_CAN_4).fixedCharges(4),
            new TriggerItem(ItemID.WATERING_CAN_5).fixedCharges(5),
            new TriggerItem(ItemID.WATERING_CAN_6).fixedCharges(6),
            new TriggerItem(ItemID.WATERING_CAN_7).fixedCharges(7),
            new TriggerItem(ItemID.WATERING_CAN_8).fixedCharges(8),
        };
    }
}
