package tictac7x.charges.items.utils;

import tictac7x.charges.store.ids.ItemId;
import net.runelite.api.Skill;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.WidgetId;

public class U_CoalBag extends ChargedItemWithStorage {
    public U_CoalBag(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.coal_bag, ItemId.COAL_BAG, provider);
        this.storage = storage
            .storableItems(new StorableItem(ItemId.COAL).checkName("Coal"))
            .setMaximumTotalQuantity(27)
            .setMaximumTotalQuantityWithEquippedItem(36, ItemId.SMITHING_CAPE, ItemId.SMITHING_CAPE_TRIMMED);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.COAL_BAG),
            new TriggerItem(ItemId.COAL_BAG_OPEN),
        };
        this.triggers = new TriggerBase[] {
            // Check or empty.
            new OnChatMessage("The coal bag is( now)? empty.").emptyStorage(),

            // Fill or check with 1 coal.
            new OnChatMessage("The coal bag( still)? contains one piece of coal.").consumer(() -> {
                storage.put(ItemId.COAL, 1);
            }),

            // Check or empty with not enough inventory space.
            new OnChatMessage("The coal bag( still)? contains (?<charges>.+) pieces of coal.").matcherConsumer((m) -> {
                storage.put(ItemId.COAL, Integer.parseInt(m.group("charges")));
            }),

            // Replace "Fill" with proper "Fill from bank".
            new OnMenuEntryAdded("Fill").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionFillFromBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Mine coal with open bag.
            // Extra coal mined by celestial ring.
            // Extra coal mined by varrock platebody.
            new OnChatMessage(
                "(You manage to mine some coal.|Your Celestial ring allows you to mine an additional ore.|The Varrock platebody enabled you to mine an additional ore.)"
            ).onMenuOption("Mine").onMenuTarget("Coal rocks").requiredItem(ItemId.COAL_BAG_OPEN).addToStorage(ItemId.COAL, 1),

            // Superheat spells.
            new OnXpDrop(Skill.SMITHING).onMenuOption("Cast").onMenuTarget(
                "Superheat Item -> Lovakite ore",
                "Superheat Item -> Iron ore"
            ).consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemId.COAL, 2);
            }),
            new OnXpDrop(Skill.SMITHING).onMenuOption("Cast").onMenuTarget(
                "Superheat Item -> Mithril ore"
            ).consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemId.COAL, 4);
            }),
            new OnXpDrop(Skill.SMITHING).onMenuOption("Cast").onMenuTarget(
                "Superheat Item -> Adamantite ore"
            ).consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemId.COAL, 6);
            }),
            new OnXpDrop(Skill.SMITHING).onMenuOption("Cast").onMenuTarget(
                "Superheat Item -> Runite ore"
            ).consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemId.COAL, 8);
            }),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }
}
