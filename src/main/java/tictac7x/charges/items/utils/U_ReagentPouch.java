package tictac7x.charges.items.utils;

import net.runelite.api.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.*;
public class U_ReagentPouch extends ChargedItemWithStorageEmptyable {
    public U_ReagentPouch(Provider provider) {
        super(TicTac7xChargesImprovedConfig.reagent_pouch, ItemID.MM_SECONDARY_POUCH, provider);
        storage.emptyIsNegative().setMaximumIndividualQuantity(26).storableItems(
            new StorableItem (ItemID.EYE_OF_NEWT).checkName("Eye of newt"),
            new StorableItem (ItemID.LIMPWURT_ROOT).checkName("Limpwurt root"),
            new StorableItem (ItemID.RED_SPIDERS_EGGS).checkName("Red spider's eggs", "eggs"),
            new StorableItem (ItemID.TOADS_LEGS).checkName("Toad's legs", "toad legs"),
            new StorableItem (ItemID.SNAPE_GRASS).checkName("Snape grass"),
            new StorableItem (ItemID.MORTMYREMUSHROOM).checkName("Mort myre fungus", "Mort Myre fungi"),
            new StorableItem (ItemID.WHITE_BERRIES).checkName("White berries"),
            new StorableItem (ItemID.JANGERBERRIES).checkName("Jangerberries"),
            new StorableItem (ItemID.POISONIVY_BERRIES).checkName("Poison ivy berries"),
            new StorableItem (ItemID.CACTUS_POTATO).checkName("Potato cactus"),
            new StorableItem (ItemID.CACTUS_SPINE).checkName("Cactus spine"),
            new StorableItem (ItemID.ASHES).checkName("Ashes"),
            new StorableItem (ItemID.CHOCOLATE_DUST).checkName("Chocolate dust"),
            new StorableItem (ItemID.DRAGON_SCALE_DUST).checkName("Dragon scale dust", "dragon scale"),
            new StorableItem (ItemID.GROUND_DESERT_GOAT_HORN).checkName("Goat horn dust", "ground desert goat horn"),
            new StorableItem (ItemID.FAIRYTALE2_GROUND_GORAK_CLAWS).checkName("Gorak claw powder"),
            new StorableItem (ItemID.HUNTINGBEAST_SABRETEETH_DUST).checkName("Kebbit teeth dust", "ground kebbit teeth"),
            new StorableItem (ItemID.SILVER_DUST).checkName("Silver dust"),
            new StorableItem (ItemID.UNICORN_HORN_DUST).checkName("Unicorn horn dust", "unicorn horn"),
            new StorableItem (ItemID.WINE_OF_ZAMORAK).checkName("Wine of zamorak", "wine"),
            new StorableItem (ItemID.ALDARIUM).checkName("Aldarium"),
            new StorableItem (ItemID.YEW_ROOTS).checkName("Yew roots"),
            new StorableItem (ItemID.MAGIC_ROOTS).checkName("Magic roots"),
            new StorableItem (ItemID.CRUSHED_BIRD_NEST).checkName("Crushed nest", "crushed bird nest"),
            new StorableItem (ItemID.CRUSHED_DRAGON_BONES).checkName("Crushed superior dragon bones"),
            new StorableItem (ItemID.NAIL_BEAST_NAIL).checkName("Nail beast nails"),
            new StorableItem (ItemID.LILY_OF_THE_SANDS).checkName("Lily of the sands"),
            new StorableItem (ItemID.BRUT_CAVIAR).checkName("Caviar"),
            new StorableItem (ItemID.BRUT_ROE).checkName("Roe"),
            new StorableItem (ItemID.SQUID_PASTE).checkName("Squid paste"),
            new StorableItem (ItemID.DEMONIC_TALLOW).checkName("Demonic tallow"),
            new StorableItem (ItemID.HADDOCK_EYE).checkName("Haddock eye")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.MM_SECONDARY_POUCH),
            new TriggerItem(ItemID.MM_SECONDARY_POUCH_OPEN),
        };

        this.triggers.addAll(List.of(
            // Check empty.
            new OnChatMessage("The Reagent pouch is empty.").emptyStorage(),

            // Check.
            new OnChatMessage("You look in your Reagent pouch and see:").emptyStorage(),
            new OnChatMessage("(?<amount>.+) x (?<item>.+)").matcherConsumer(m -> {
                Optional<StorageItem> item = getStorageItemFromName(m.group("item"), Integer.parseInt(m.group("amount")));
                storage.put(item);
            }).hasChatMessage("You look in your Reagent pouch and see:"),

            // Pick up.
            new OnChatMessage("You put the (?<item>.+) into your Reagent pouch.").matcherConsumer(m -> {
                Optional<StorageItem> item = getStorageItemFromName(m.group("item"), 1);
                storage.add(item);
            }),

            // Empty to bank.
            new OnChatMessage("You empty your Reagent pouch into the bank.").onItemClick().emptyStorage(),

            // Empty to inventory.
            new OnItemContainerChanged(InventoryID.INV).emptyStorageToInventory().onMenuOption("Empty"),

            // Fill from inventory.
            new OnItemContainerChanged(InventoryID.INV).onInventoryDifference(inventoryDifference -> {
                for (StorageItem inventoryDifferenceItem : inventoryDifference.getItems()) {
                    // Item was put into the reagent pouch, but there is more in inventory, meaning that item is filled to maximum.
                    if (provider.store.inventory.hasItem(inventoryDifferenceItem.itemId)) {
                        storage.put(inventoryDifferenceItem.itemId, 26);
                    }
                }
            }).onMenuOption("Fill", TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory),

            new OnMenuOptionClicked("Fill", TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory).consumer(() -> {
                for (StorageItem item : provider.store.inventory.getItems()) {
                    provider.store.addConsumerToNextTickQueue(() -> storage.add(item));
                }
            }),

            // Mix potions.
            new OnChatMessage("You mix the (?<item>.+) into (your|the unfinished)( antifire)? (potion|antidote\\+\\+).*").matcherConsumer((m) -> {
                Optional<StorageItem> item = getStorageItemFromName(m.group("item"), 1);
                storage.remove(item);
            }).requiredItem (ItemID.MM_SECONDARY_POUCH_OPEN),

            // Harvesting
            new OnXpDrop(Skill.FARMING).onMenuOption("Harvest").onMenuTarget("Snape grass plant").addToStorage (ItemID.SNAPE_GRASS, 1),
            new OnChatMessage("You pick (?<quantity>.+) mushrooms? from the log.").matcherConsumer(m -> {
                storage.add(ItemID.MORTMYREMUSHROOM, getNumberFromWordRepresentation(m.group("quantity")));
            }),
            new OnChatMessage("You pick some whiteberries.").requiredItem (ItemID.MM_SECONDARY_POUCH_OPEN).consumer(() -> {
                storage.add(ItemID.WHITE_BERRIES, 1);
            }),
            new OnChatMessage("You pick some jangerberries.").requiredItem (ItemID.MM_SECONDARY_POUCH_OPEN).consumer(() -> {
                storage.add(ItemID.JANGERBERRIES, 1);
            }),
            new OnChatMessage("You pick some poison ivy berries.").requiredItem (ItemID.MM_SECONDARY_POUCH_OPEN).consumer(() -> {
                storage.add(ItemID.POISONIVY_BERRIES, 1);
            }),
            new OnChatMessage("You carefully pick a potato cactus.").requiredItem (ItemID.MM_SECONDARY_POUCH_OPEN).consumer(() -> {
                storage.add(ItemID.CACTUS_POTATO, 1);
            }),
            new OnChatMessage("You carefully pick a spine from the cactus.").consumer(() -> {
                storage.add(ItemID.CACTUS_SPINE, 1);
            }),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            new OnMenuEntryAdded("Fill").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }
}
