package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

public class J_DigsitePendant extends ChargedItem {
    public J_DigsitePendant(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.digsite_pendant, ItemId.DIGSITE_PENDANT_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.DIGSITE_PENDANT_1).fixedCharges(1),
            new TriggerItem(ItemId.DIGSITE_PENDANT_2).fixedCharges(2),
            new TriggerItem(ItemId.DIGSITE_PENDANT_3).fixedCharges(3),
            new TriggerItem(ItemId.DIGSITE_PENDANT_4).fixedCharges(4),
            new TriggerItem(ItemId.DIGSITE_PENDANT_5).fixedCharges(5),
        };
    }
}
