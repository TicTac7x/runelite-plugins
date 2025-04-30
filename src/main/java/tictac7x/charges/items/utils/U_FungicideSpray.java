package tictac7x.charges.items.utils;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class U_FungicideSpray extends ChargedItem {
    public U_FungicideSpray(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.fungicide_spray, ItemId.FUNGICIDE_SPRAY_0, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_0).fixedCharges(0),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_1).fixedCharges(1),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_2).fixedCharges(2),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_3).fixedCharges(3),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_4).fixedCharges(4),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_5).fixedCharges(5),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_6).fixedCharges(6),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_7).fixedCharges(7),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_8).fixedCharges(8),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_9).fixedCharges(9),
            new TriggerItem(ItemId.FUNGICIDE_SPRAY_10).fixedCharges(10),
        };
    }
}
