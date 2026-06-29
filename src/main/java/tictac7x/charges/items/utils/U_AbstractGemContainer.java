package tictac7x.charges.items.utils;

import tictac7x.charges.item.ChargedItemWithStorageEmptyable;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.WidgetId;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.runelite.api.Skill;


import static tictac7x.charges.store.ids.ItemContainerId.INVENTORY;
import static tictac7x.charges.store.ids.ItemContainerId.BANK;

public abstract class U_AbstractGemContainer extends ChargedItemWithStorageEmptyable {

    private static final Map<Integer, GemInfo> gemMap = new HashMap<>();
    private final List<Map.Entry<Integer, GemInfo>> filteredGemMap;

    static {
        gemMap.put(ItemId.UNCUT_SAPPHIRE, new GemInfo("sapphires", "Sapphires", "Uncut sapphire"));
        gemMap.put(ItemId.UNCUT_EMERALD, new GemInfo("emeralds", "Emeralds", "Uncut emerald"));
        gemMap.put(ItemId.UNCUT_RUBY, new GemInfo("rubies", "Rubies", "Uncut ruby"));
        gemMap.put(ItemId.UNCUT_DIAMOND, new GemInfo("diamonds", "Diamonds", "Uncut diamond"));
        gemMap.put(ItemId.UNCUT_DRAGONSTONE, new GemInfo("dragonstones", "Dragonstones", "Uncut dragonstone"));
        gemMap.put(ItemId.UNCUT_OPAL, new GemInfo("opals", "Opal", "Uncut opal"));
        gemMap.put(ItemId.UNCUT_JADE, new GemInfo("jades", "Jade", "Uncut jade"));
        gemMap.put(ItemId.UNCUT_RED_TOPAZ, new GemInfo("redtopazes", "Red Topaz", "Uncut red topaz"));
    }

    protected U_AbstractGemContainer(final String configKey, final int itemId, int openItemId, int maxQuantity, final StorableItem[] gems, final Provider provider) {
        super(configKey, itemId, provider);

        this.items = new TriggerItem[]{
                new TriggerItem(itemId),
                new TriggerItem(openItemId),
        };

        storage.setMaximumIndividualQuantity(maxQuantity).storableItems(gems);

        Set<Integer> storableGemIds = Arrays.stream(gems)
                .map(StorageItem::getId)
                .collect(Collectors.toSet());

        filteredGemMap = gemMap.entrySet().stream()
                .filter(e -> storableGemIds.contains(e.getKey()))
                .collect(Collectors.toList());

        // Builds a lookahead-based regex that matches any order of gem counts, e.g.:
        // (?s)(?=.*Sapphires:\s*(?<sapphires>\d+))(?=.*Emeralds:\s*(?<emeralds>\d+)).*
        String checkGemsInContainerRegex = "(?s)" + filteredGemMap.stream()
                .map(e -> "(?=.*" + Pattern.quote(e.getValue().displayName)
                        + ":\\s*(?<" + e.getValue().groupName + ">\\d+))")
                .collect(Collectors.joining()) + ".*";

        this.triggers.addAll(List.of(
                // Empty to bank.
                new OnChatMessage("You empty your gem bag into the bank").emptyStorage(),

                // Empty to bank or inventory.
                new OnChatMessage("The gem bag is( now)? empty.").emptyStorage(),

                // Check container
                new OnChatMessage(checkGemsInContainerRegex).matcherConsumer(m -> {
                    for (final Map.Entry<Integer, GemInfo> entry : filteredGemMap) {
                        storage.put(entry.getKey(), Integer.parseInt(m.group(entry.getValue().groupName)));
                    }
                }),

                // Mining regular or gem rocks.
                new OnChatMessage("You just (found|mined) (a|an) (?<gem>.+)!")
                        .matcherConsumer(m -> storage.add(getStorageItemFromName(m.group("gem"), 1)))
                        .requiredItem(openItemId),

                // Pickpocketing.
                new OnChatMessage("The following stolen loot gets added to your gem bag: Uncut (?<gem>.+) x (?<quantity>.+).")
                        .matcherConsumer(m -> storage.add(getStorageItemFromName(m.group("gem"), Integer.parseInt(m.group("quantity"))))),

                // Stealing from stalls.
                new OnChatMessage("You steal an uncut (?<gem>.+) and add it to your gem bag.")
                        .matcherConsumer(m -> storage.add(getStorageItemFromName(m.group("gem"), 1))),

                // Fill from inventory.
                new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

                // Empty to bank.
                new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank),

                // Use gem on container.
                new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseChargedItemOnStorageItem(storage.getStorableItems()),
                // Use container on gem
                new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

                // Pick up.
                new OnItemPickup(storage.getStorableItems()).isByOne().requiredItem(openItemId).pickUpToStorage(),

                // Replace "Empty" with proper "Empty to bank".
                new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

                // Hide destroy.
                new OnMenuEntryAdded("Destroy").hide()
        ));

        // Telegrab - only add trigger if this container holds that gem type.
        this.triggers.addAll(
                filteredGemMap.stream()
                        .map(e -> new OnXpDrop(Skill.MAGIC)
                                .requiredItem(openItemId)
                                .onMenuOption("Cast")
                                .onMenuTarget(e.getValue().uncutName)
                                .addToStorage(e.getKey(), 1))
                        .collect(Collectors.toList())
        );
    }

    private static final class GemInfo {
        final String groupName;
        final String displayName;
        final String uncutName;

        private GemInfo(String groupName, String displayName, String uncutName) {
            this.groupName = groupName;
            this.displayName = displayName;
            this.uncutName = uncutName;
        }
    }
}