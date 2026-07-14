package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class J_DigsitePendant extends ChargedItem {
    public J_DigsitePendant(Provider provider) {
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
