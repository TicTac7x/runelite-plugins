package tictac7x.charges.items.utils;

import net.runelite.api.*;
import net.runelite.api.gameval.InventoryID;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

import java.util.*;
import java.util.stream.*;

public abstract class U_AbstractGemContainer extends ChargedItemWithStorageEmptyable {
    private String containerNameRegex;

    protected U_AbstractGemContainer(
        String configKey,
        int itemId,
        int openItemId,
        int maxQuantity,
        String containerName,
        boolean preciousGems,
        boolean semiPreciousGems,
        Provider provider
    ) {
        super(configKey, itemId, provider);
        this.containerNameRegex = "(gem bag|" + containerName + ")";

        this.items = new TriggerItem[]{
            new TriggerItem(itemId),
            new TriggerItem(openItemId),
        };

        storage.setMaximumIndividualQuantity(maxQuantity);

        List<StorableItem> storableGems = new ArrayList<>();
        if (semiPreciousGems) {
            storableGems.addAll(List.of(
                    new StorableItem(ItemID.UNCUT_OPAL).checkName("Opal").displayName("Uncut opal"),
                    new StorableItem(ItemID.UNCUT_JADE).checkName("Jade").displayName("Uncut jade"),
                    new StorableItem(ItemID.UNCUT_RED_TOPAZ).checkName("Red Topaz").displayName("Uncut red topaz")
            ));
        }
        if (preciousGems) {
            storableGems.addAll(List.of(
                new StorableItem(ItemID.UNCUT_SAPPHIRE).checkName("Sapphires", "Sapphire").displayName("Uncut sapphire"),
                new StorableItem(ItemID.UNCUT_EMERALD).checkName("Emeralds", "Emerald").displayName("Uncut emerald"),
                new StorableItem(ItemID.UNCUT_RUBY).checkName("Rubies", "Ruby").displayName("Uncut ruby"),
                new StorableItem(ItemID.UNCUT_DIAMOND).checkName("Diamonds", "Diamond").displayName("Uncut diamond"),
                new StorableItem(ItemID.UNCUT_DRAGONSTONE).checkName("Dragonstones", "Dragonstone").displayName("Uncut dragonstone")
            ));
        }
        storage.storableItems(storableGems.toArray(StorableItem[]::new));

        // Builds a lookahead-based regex that matches any order of gem counts, e.g.:
        // (?s)(?=.*Sapphires:\s*(?<sapphires>\d+))(?=.*Emeralds:\s*(?<emeralds>\d+)).*
        String checkRegex = "(Left in bag: )?" +
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
                for (StorableItem storableGem : storableGems) {
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
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to bank.
            new OnItemContainerChanged(InventoryID.BANK).emptyStorageToBank().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank),

            // Use gem on container.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseChargedItemOnStorageItem(storage.getStorableItems()),
            // Use container on gem
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

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

    private String getStorableGemGroupName(StorableItem storableGem) {
        if (storableGem.checkName.isEmpty() || storableGem.checkName.get().length == 0) {
            return "";
        }

        return storableGem.checkName.get()[0].replace(" ", "").toLowerCase();
    }
}