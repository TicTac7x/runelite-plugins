package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class U_Waterskin extends ChargedItem {
    public U_Waterskin(Provider provider) {
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
