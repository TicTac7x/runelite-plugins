package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class U_ImpInABox extends ChargedItemWithStatus {
    public U_ImpInABox(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.imp_in_a_box, ItemId.IMP_IN_A_BOX_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.IMP_IN_A_BOX_1).fixedCharges(1),
            new TriggerItem(ItemId.IMP_IN_A_BOX_2).fixedCharges(2),
        };
    }
}
