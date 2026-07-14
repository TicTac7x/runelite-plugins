package tictac7x.charges.items.shields;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

import java.util.*;

public class S_CrystalShield extends ChargedItem {
    public S_CrystalShield(Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_shield, ItemId.CRYSTAL_SHIELD_DEGRADED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_SHIELD),
            new TriggerItem(ItemId.CRYSTAL_SHIELD_DEGRADED),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your crystal shield has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Get hit.
            new OnHitsplatApplied(HitsplatTarget.SELF, HitsplatGroup.SUCCESSFUL).moreThanZeroDamage().isEquipped().decreaseCharges(1)
        ));
    }
}
