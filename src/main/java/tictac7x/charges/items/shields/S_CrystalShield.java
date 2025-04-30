package tictac7x.charges.items.shields;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.HitsplatTarget;
import tictac7x.charges.store.Provider;

public class S_CrystalShield extends ChargedItem {
    public S_CrystalShield(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_shield, ItemId.CRYSTAL_SHIELD_DEGRADED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_SHIELD),
            new TriggerItem(ItemId.CRYSTAL_SHIELD_DEGRADED),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your crystal shield has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Get hit.
            new OnHitsplatApplied(HitsplatTarget.SELF).moreThanZeroDamage().isEquipped().decreaseCharges(1)
        };
    }
}
