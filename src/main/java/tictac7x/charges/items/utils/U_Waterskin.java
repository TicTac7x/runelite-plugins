package tictac7x.charges.items.utils;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class U_Waterskin extends ChargedItem {
    public U_Waterskin(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.waterskin, ItemId.WATERSKIN_0, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.WATERSKIN_0).fixedCharges(0),
            new TriggerItem(ItemId.WATERSKIN_1).fixedCharges(1),
            new TriggerItem(ItemId.WATERSKIN_2).fixedCharges(2),
            new TriggerItem(ItemId.WATERSKIN_3).fixedCharges(3),
            new TriggerItem(ItemId.WATERSKIN_4).fixedCharges(4),
        };
    }
}
