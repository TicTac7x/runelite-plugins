package tictac7x.charges.items.moons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class EclipseMoonChestplate extends _MoonItem {
    public EclipseMoonChestplate(
        final Provider provider
    ) {
        super("eclipse_chestplate", ItemId.ECLIPSE_MOON_CHESTPLATE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ECLIPSE_MOON_CHESTPLATE).fixedCharges(3000),
            new TriggerItem(ItemId.ECLIPSE_MOON_CHESTPLATE_DEGRADED),
            new TriggerItem(ItemId.ECLIPSE_MOON_CHESTPLATE_BROKEN).fixedCharges(0),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your Eclipse moon chestplate has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // In combat.
            new OnCombat(90).isEquipped().decreaseCharges(1),
        };
    }
}