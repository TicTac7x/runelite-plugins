package tictac7x.charges.items.moons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnCombat;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class EclipseMoonTassets extends _MoonItem {
    public EclipseMoonTassets(
        final Provider provider
    ) {
        super("eclipse_tassets", ItemId.ECLIPSE_MOON_TASSETS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ECLIPSE_MOON_TASSETS).fixedCharges(3000),
            new TriggerItem(ItemId.ECLIPSE_MOON_TASSETS_DEGRADED),
            new TriggerItem(ItemId.ECLIPSE_MOON_TASSETS_BROKEN).fixedCharges(0),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your Eclipse moon tassets( only)? has (?<charges>.+) charges? (remaining|left).").setDynamicallyCharges(),

            // In combat.
            new OnCombat(90).isEquipped().decreaseCharges(1),
        };
    }
}