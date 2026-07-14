package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class U_RoyalSeedPod extends ChargedItem {
    public U_RoyalSeedPod(Provider provider) {
        super(TicTac7xChargesImprovedConfig.royal_seed_pod, ItemID.MM2_ROYAL_SEED_POD, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.MM2_ROYAL_SEED_POD).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            // Unify teleport.
            new OnMenuEntryAdded("Commune").replaceOption("Teleport"),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }
}
