package tictac7x.charges.items.weapons.venator;

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
            new StorableItem(ItemId.ANCIENT_ESSENCE)
        );

        String itemNameRegex = Pattern.quote(itemName);

        this.triggers.addAll(List.of(
            // Charging the bow with essence - Check to see if the bow is already fully charged.
            new OnChatMessage(itemNameRegex + " is already fully charged.").onItemClick().consumer(() -> {
                storage.clearAndPut(ItemId.ANCIENT_ESSENCE, 50000);
            }),

            // Charging the bow with essence - For charging your echo venator bow, as of March 2026, the game doesn't explicitly say "echo venator bow", but I'll include it just in case
            new OnChatMessage("You use .+ ancient essence to charge your " + itemNameRegex + ". It now has (?<charges>.+) charges.").onItemClick().matcherConsumer(m -> {
                storage.clearAndPut(ItemId.ANCIENT_ESSENCE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
            }),

            // Uncharge (you can only uncharge ALL charges at once)
            new OnChatMessage("You fully uncharge your " + itemNameRegex + ", regaining (?<charges>.+) ancient essence in the process.").consumer(() -> storage.clear()),

            // Check.
            new OnChatMessage("Your " + itemNameRegex + "  has (?<charges>.+) charges? remaining.").onItemClick().matcherConsumer(m -> {
                storage.clearAndPut(ItemId.ANCIENT_ESSENCE, TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
            }),

            // Attack.
            new OnGraphicChanged(2289).isEquipped().consumer(() -> {
                storage.remove(ItemId.ANCIENT_ESSENCE, 1);
            })
        ));
    }

    @Override
    public Color getTextColor(int itemId) {
        return this.getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        if (this.storage.getStorage().count(ItemId.ANCIENT_ESSENCE) == 0) {
            return provider.config.getColorEmpty();
        }

        return super.getTotalTextColor();
    }
}
