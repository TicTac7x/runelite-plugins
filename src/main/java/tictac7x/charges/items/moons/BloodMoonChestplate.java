package tictac7x.charges.items.moons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class BloodMoonChestplate extends _MoonItem {
    public BloodMoonChestplate(
        final Provider provider
    ) {
        super("blood_chestplate", ItemId.BLOOD_MOON_CHESTPLATE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLOOD_MOON_CHESTPLATE).fixedCharges(3000),
            new TriggerItem(ItemId.BLOOD_MOON_CHESTPLATE_DEGRADED),
            new TriggerItem(ItemId.BLOOD_MOON_CHESTPLATE_BROKEN).fixedCharges(0),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your Blood moon chestplate( only)? has (?<charges>.+) charges? (remaining|left).").setDynamicallyCharges(),

            // In combat.
            new OnCombat(90).isEquipped().decreaseCharges(1),
        };
    }
}