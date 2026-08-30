package tictac7x.charges.items.weapons.venator;

import net.runelite.api.gameval.*;
import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.awt.*;
import java.util.List;
import java.util.regex.*;

public abstract class _VenatorBow extends ChargedItemWithStorage {
    public _VenatorBow(String configKey, int itemId, int itemIdUncharged, String itemName, Provider provider) {
        super(configKey, itemId, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(itemIdUncharged).fixedCharges(0),
            new TriggerItem(itemId),
        };

        this.storage.storableItems(
            new StorableItem(ItemID.ANCIENT_ESSENCE)
        ).emptyIsNegative();

        this.triggers.addAll(List.of(
            // Charging the bow with essence - Check to see if the bow is already fully charged.
            new OnChatMessage(itemName + " is already fully charged.").onItemClick().consumer(() -> {
                storage.clearAndPut(ItemID.ANCIENT_ESSENCE, 50000);
            }),

            // Charging the bow with essence - For charging your echo venator bow, as of March 2026, the game doesn't explicitly say "echo venator bow", but I'll include it just in case
            new OnChatMessage("You use .+ ancient essence to charge your " + itemName.toLowerCase() + ". It now has (?<charges>.+) charges.").onItemClick().matcherConsumer(m -> {
                storage.clearAndPut(ItemID.ANCIENT_ESSENCE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
            }),

            // Uncharge (you can only uncharge ALL charges at once)
            new OnChatMessage("You fully uncharge your " + itemName.toLowerCase() + ", regaining (?<charges>.+) ancient essence in the process.").consumer(() -> storage.clear()),

            // Check.
            new OnChatMessage("Your " + itemName.toLowerCase() + " has (?<charges>.+) charges? remaining.").onItemClick().matcherConsumer(m -> {
                storage.clearAndPut(ItemID.ANCIENT_ESSENCE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
            }),

            // Attack.
            new OnGraphicChanged(2289).isEquipped().consumer(() -> {
                storage.remove(ItemID.ANCIENT_ESSENCE, 1);
            }),

            // Auto-charge.
            new OnAutoChargeMessage(itemName, "Ancient essence", 1, this, ItemID.ANCIENT_ESSENCE)
        ));
    }
}
