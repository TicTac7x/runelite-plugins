package tictac7x.charges.items.utils;

import tictac7x.charges.item.ChargedItemWithStorageEmptyable;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.WidgetId;

import java.util.*;
import java.util.stream.Collectors;

import net.runelite.api.Skill;


import static tictac7x.charges.store.ids.ItemContainerId.INVENTORY;
import static tictac7x.charges.store.ids.ItemContainerId.BANK;

public abstract class U_AbstractGemContainer extends ChargedItemWithStorageEmptyable {
    private final String containerNameRegex;

    protected U_AbstractGemContainer(
        final String configKey,
        final int itemId,
        final int openItemId,
        final int maxQuantity,
        final String containerName,
        final boolean preciousGems,
        final boolean semiPreciousGems,
        final Provider provider
    ) {
        super(configKey, itemId, provider);
        this.containerNameRegex = "(gem bag|" + containerName + ")";

        this.items = new TriggerItem[]{
            new TriggerItem(itemId),
            new TriggerItem(openItemId),
        };

        storage.setMaximumIndividualQuantity(maxQuantity);

        final List<StorableItem> storableGems = new ArrayList<>();
        if (semiPreciousGems) {
            storableGems.addAll(List.of(
                    new StorableItem(ItemId.UNCUT_OPAL).checkName("Opal").displayName("Uncut opal"),
                    new StorableItem(ItemId.UNCUT_JADE).checkName("Jade").displayName("Uncut jade"),
                    new StorableItem(ItemId.UNCUT_RED_TOPAZ).checkName("Red Topaz").displayName("Uncut red topaz")
            ));
        }
        if (preciousGems) {
            storableGems.addAll(List.of(
                new StorableItem(ItemId.UNCUT_SAPPHIRE).checkName("Sapphires", "Sapphire").displayName("Uncut sapphire"),
                new StorableItem(ItemId.UNCUT_EMERALD).checkName("Emeralds", "Emerald").displayName("Uncut emerald"),
                new StorableItem(ItemId.UNCUT_RUBY).checkName("Rubies", "Ruby").displayName("Uncut ruby"),
                new StorableItem(ItemId.UNCUT_DIAMOND).checkName("Diamonds", "Diamond").displayName("Uncut diamond"),
                new StorableItem(ItemId.UNCUT_DRAGONSTONE).checkName("Dragonstones", "Dragonstone").displayName("Uncut dragonstone")
            ));
        }
        storage.storableItems(storableGems.toArray(StorableItem[]::new));

        // Builds a lookahead-based regex that matches any order of gem counts, e.g.:
        // (?s)(?=.*Sapphires:\s*(?<sapphires>\d+))(?=.*Emeralds:\s*(?<emeralds>\d+)).*
        final String checkRegex = "(Left in bag: )?" +
            storableGems.stream()
                .filter(storableGem -> storableGem.checkName.isPresent() && storableGem.checkName.get().length > 0)
                .map(
                    storableGem -> storableGem.checkName.get()[0] + ": (?<" +
                    getStorableGemGroupName(storableGem) + ">\\d+)")
                .collect(Collectors.joining("( | / )"));

        this.triggers.addAll(List.of(
            // Empty to bank.
            new OnChatMessage("You empty (the|your) " + containerNameRegex + " into the bank.").onItemClick().emptyStorage(),

            // Empty to inventory.
            new OnChatMessage("The " + containerNameRegex + " is( now)? empty.").onItemClick().emptyStorage(),

            // Check or empty partially to inventory.
            new OnChatMessage(checkRegex).matcherConsumer(m -> {
                for (final StorableItem storableGem : storableGems) {
                    storage.put(storableGem.itemId, Integer.parseInt(m.group(getStorableGemGroupName(storableGem))));
                }
            }),

            // Mining regular or gem rocks.
            new OnChatMessage("You just (found|mined) (a|an) (?<gem>.+)!")
                .matcherConsumer(m -> storage.add(getStorageItemFromName(m.group("gem"), 1)))
                .requiredItem(openItemId),

            // Pickpocketing.
            new OnChatMessage("The following stolen loot gets added to your " + containerNameRegex + ": Uncut (?<gem>.+) x (?<quantity>.+).")
                .matcherConsumer(m -> storage.add(getStorageItemFromName(m.group("gem"), Integer.parseInt(m.group("quantity"))))),

            // Pickpocketing alternative message
            new OnChatMessage("You put the stolen Uncut (?<gem>.+) into your " + containerNameRegex + ".")
                .matcherConsumer(m -> storage.add(getStorageItemFromName(m.group("gem"), 1))),

            // Stealing from stalls.
            new OnChatMessage("You steal an uncut (?<gem>.+) and add it to your " + containerNameRegex + ".")
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
            storableGems.stream()
                .filter(storableItem -> storableItem.displayName.isPresent())
                .map(storableGem -> new OnXpDrop(Skill.MAGIC)
                    .requiredItem(openItemId)
                    .onMenuOption("Cast")
                    .onMenuTarget(storableGem.displayName.get())
                    .addToStorage(storableGem.itemId, 1))
                .collect(Collectors.toList())
        );
    }

    private String getStorableGemGroupName(final StorableItem storableGem) {
        if (storableGem.checkName.isEmpty() || storableGem.checkName.get().length == 0) {
            return "";
        }

        return storableGem.checkName.get()[0].replace(" ", "").toLowerCase();
    }
}