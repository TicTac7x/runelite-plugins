package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ChargeId;
import tictac7x.charges.store.ids.ItemId;

public class U_Ectophial extends ChargedItem {
    public U_Ectophial(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ectophial, ItemId.ECTOPHIAL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ECTOPHIAL_EMPTY).fixedCharges(0),
            new TriggerItem(ItemId.ECTOPHIAL).fixedCharges(1),
        };

        this.triggers = new TriggerBase[]{
            // Unify teleport.
            new OnMenuEntryAdded("Empty").replaceOption("Teleport"),
        };
    }
}
