package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

import java.util.List;

public class U_RoyalSeedPod extends ChargedItem {
    public U_RoyalSeedPod(Provider provider) {
        super(TicTac7xChargesImprovedConfig.royal_seed_pod, ItemId.ROYAL_SEED_POD, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ROYAL_SEED_POD).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            // Unify teleport.
            new OnMenuEntryAdded("Commune").replaceOption("Teleport"),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }
}
