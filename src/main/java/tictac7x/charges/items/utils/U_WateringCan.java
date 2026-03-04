package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class U_WateringCan extends ChargedItem {
    public U_WateringCan(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.watering_can, ItemId.WATERING_CAN_0, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.WATERING_CAN_0).fixedCharges(0),
            new TriggerItem(ItemId.WATERING_CAN_1).fixedCharges(1),
            new TriggerItem(ItemId.WATERING_CAN_2).fixedCharges(2),
            new TriggerItem(ItemId.WATERING_CAN_3).fixedCharges(3),
            new TriggerItem(ItemId.WATERING_CAN_4).fixedCharges(4),
            new TriggerItem(ItemId.WATERING_CAN_5).fixedCharges(5),
            new TriggerItem(ItemId.WATERING_CAN_6).fixedCharges(6),
            new TriggerItem(ItemId.WATERING_CAN_7).fixedCharges(7),
            new TriggerItem(ItemId.WATERING_CAN_8).fixedCharges(8),
        };
    }
}
