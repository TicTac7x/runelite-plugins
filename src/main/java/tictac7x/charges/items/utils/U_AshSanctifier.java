package tictac7x.charges.items.utils;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.ItemId;
import net.runelite.api.Skill;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.store.Provider;

import java.util.List;

public class U_AshSanctifier extends ChargedItemWithStatus {
    public U_AshSanctifier(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ash_sanctifier, ItemId.ASH_SANCTIFIER, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ASH_SANCTIFIER),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("(The|Your) ash sanctifier has (?<charges>.+) charges?( left)?. It has been deactivated").setDynamicallyCharges().deactivate(),
            new OnChatMessage("(The|Your) ash sanctifier has (?<charges>.+) charges?( left)?. It is active").setDynamicallyCharges().activate(),
            new OnChatMessage("(The|Your) ash sanctifier has (?<charges>.+) charges?( left)?.").setDynamicallyCharges(),

            // Activate.
            new OnChatMessage("The ash sanctifier is active and ready to scatter ashes.").activate(),

            // Deactivate.
            new OnChatMessage("The ash sanctifier has been deactivated, and will not scatter ashes now.").deactivate(),

            // Automatic scatter.
            new OnXpDrop(Skill.PRAYER).isActivated().decreaseCharges(1),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),

            // Auto-charge.
            new OnAutoChargeMessage("Ash sanctifier", "Death rune", 10, this)
        ));
    }
}
