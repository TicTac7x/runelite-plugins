package tictac7x.charges.items.moons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class BlueMoonChestplate extends _MoonItem {
    public BlueMoonChestplate(
        final Provider provider
    ) {
        super("blue_chestplate", ItemId.BLUE_MOON_CHESTPLATE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLUE_MOON_CHESTPLATE).fixedCharges(3000),
            new TriggerItem(ItemId.BLUE_MOON_CHESTPLATE_DEGRADED),
            new TriggerItem(ItemId.BLUE_MOON_CHESTPLATE_BROKEN).fixedCharges(0),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your Blue moon chestplate has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // In combat.
            new OnCombat(90).isEquipped().decreaseCharges(1),
        };
    }
}