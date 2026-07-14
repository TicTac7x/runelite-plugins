package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class U_Ectophial extends ChargedItem {
    public U_Ectophial(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ectophial, ItemId.ECTOPHIAL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ECTOPHIAL_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.ECTOPHIAL).fixedCharges(1),
        };

        this.triggers.addAll(List.of(
            // Unify teleport.
            new OnMenuEntryAdded("Empty").replaceOption("Teleport")
        ));
    }
}
