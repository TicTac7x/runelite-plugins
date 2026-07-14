package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class U_Ectophial extends ChargedItem {
    public U_Ectophial(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ectophial, ItemID.ECTOPHIAL, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ECTOPHIAL_EMPTY).fixedCharges(0),
            new TriggerItem(ItemID.ECTOPHIAL).fixedCharges(1),
        };

        this.triggers.addAll(List.of(
            // Unify teleport.
            new OnMenuEntryAdded("Empty").replaceOption("Teleport")
        ));
    }
}
