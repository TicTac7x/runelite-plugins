package tictac7x.charges.items.moons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class BloodMoonTassets extends _MoonItem {
    public BloodMoonTassets(
        final Provider provider
    ) {
        super("blood_tassets", ItemId.BLOOD_MOON_TASSETS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLOOD_MOON_TASSETS).fixedCharges(3000),
            new TriggerItem(ItemId.BLOOD_MOON_TASSETS_DEGRADED),
            new TriggerItem(ItemId.BLOOD_MOON_TASSETS_BROKEN).fixedCharges(0),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your Blood moon tassets has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // In combat.
            new OnCombat(90).isEquipped().decreaseCharges(1),
        };
    }
}