package tictac7x.charges.items.utils;

import net.runelite.api.*;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.*;

import java.util.List;

public class U_FurPouch extends ChargedItemWithStorageEmptyable {
    public U_FurPouch(Provider provider) {
        super(TicTac7xChargesImprovedConfig.fur_pouch, ItemID.HG_FURPOUCH_SMALL, provider);
        this.storage = storage.storableItems(
            // Tracking.
            new StorableItem(ItemID.HUNTINGBEAST_POLAR_FUR),
            new StorableItem(ItemID.HUNTINGBEAST_WOODLAND_FUR),
            new StorableItem(ItemID.HUNTINGBEAST_JUNGLE_FUR),
            new StorableItem(ItemID.HUNTINGBEAST_DESERT_FUR),

            // Deadfall.
            new StorableItem(ItemID.HUNTING_FENNECFOX_FUR),

            // Pitfalls.
            new StorableItem(ItemID.HUNTING_FUR_JAGUAR_PERFECT),
            new StorableItem(ItemID.HUNTING_FUR_LEOPARD_PERFECT),
            new StorableItem(ItemID.HUNTING_FUR_TIGER_PERFECT),
            new StorableItem(ItemID.HUNTING_ANTELOPESUN_FUR),
            new StorableItem(ItemID.HUNTING_ANTELOPEMOON_FUR),

            // Aerial.
            new StorableItem(ItemID.HUNTINGBEAST_SPEEDY_FUR),
            new StorableItem(ItemID.HUNTINGBEAST_SILENT_FUR),
            new StorableItem(ItemID.HUNTINGBEAST_SPEEDY2_FUR)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.HG_FURPOUCH_SMALL).maxCharges(14),
            new TriggerItem(ItemID.HG_FURPOUCH_SMALL_OPEN).maxCharges(14),
            new TriggerItem(ItemID.HG_FURPOUCH_MED).maxCharges(21),
            new TriggerItem(ItemID.HG_FURPOUCH_MED_OPEN).maxCharges(21),
            new TriggerItem(ItemID.HG_FURPOUCH_LARGE).maxCharges(28),
            new TriggerItem(ItemID.HG_FURPOUCH_LARGE_OPEN).maxCharges(28),
        };

        this.triggers.addAll(List.of(
            // Empty.
            new OnChatMessage("Your fur pouch is currently holding 0 fur.").emptyStorage(),
            new OnChatMessage("Your fur pouch is empty.").emptyStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(InventoryID.INV).emptyStorageToInventory().onMenuOption("Empty"),

            // Empty to bank.
            new OnItemContainerChanged(InventoryID.BANK).emptyStorageToBank().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank),

            // Empty from deposit box.
            new OnMenuOptionClicked(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).onItemClick().isWidgetVisible(WidgetId.DEPOSIT_BOX).emptyStorage(),

            // Use fur on pouch.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide(),

            // Tracking.
            new OnChatMessage("You manage to noose a polar kebbit that is hiding in the snowdrift.").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTINGBEAST_POLAR_FUR),
            new OnChatMessage("You manage to noose a common kebbit that is hiding in the bush.").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTINGBEAST_WOODLAND_FUR),
            new OnChatMessage("You manage to noose a Feldip weasel that is hiding in the bush.").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTINGBEAST_JUNGLE_FUR),
            new OnChatMessage("You manage to noose a desert devil that is hiding in the sand.").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTINGBEAST_DESERT_FUR),

            // Deadfalls.
            new OnChatMessage("You've caught a pyre fox.").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTING_FENNECFOX_FUR),

            // Pitfalls.
            new OnChatMessage("You've caught a spined larupia!").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTING_FUR_JAGUAR_PERFECT),
            new OnItemContainerChanged(InventoryID.INV).hasChatMessage("You've caught a spined larupia!").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).onInventoryDifference(itemsDifference -> {
                if (itemsDifference.hasItem(ItemID.HUNTING_FUR_JAGUAR_SHABBY)) {
                    storage.remove(ItemID.HUNTING_FUR_JAGUAR_PERFECT, 1);
                }
            }),
            new OnChatMessage("You've caught a horned graahk!").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTING_FUR_LEOPARD_PERFECT),
            new OnItemContainerChanged(InventoryID.INV).hasChatMessage("You've caught a horned graahk!").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).onInventoryDifference(itemsDifference -> {
                if (itemsDifference.hasItem(ItemID.HUNTING_FUR_LEOPARD_SHABBY)) {
                    storage.remove(ItemID.HUNTING_FUR_LEOPARD_PERFECT, 1);
                }
            }),
            new OnChatMessage("You've caught a sabre-?toothed kyatt!").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTING_FUR_TIGER_PERFECT),
            new OnItemContainerChanged(InventoryID.INV).hasChatMessage("You've caught a sabre-?toothed kyatt!").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).onInventoryDifference(itemsDifference -> {
                if (itemsDifference.hasItem(ItemID.HUNTING_FUR_TIGER_SHABBY)) {
                    storage.remove(ItemID.HUNTING_FUR_TIGER_PERFECT, 1);
                }
            }),
            new OnChatMessage("You've caught a sunlight antelope!").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTING_ANTELOPESUN_FUR),
            new OnChatMessage("You've caught a moonlight antelope!").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).addToStorage(ItemID.HUNTING_ANTELOPEMOON_FUR),

            // Aerial.
            new OnXpDrop(Skill.HUNTER, 104).hasChatMessage("You retrieve the falcon as well as the fur of the dead kebbit.").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).consumer(() -> {
                storage.add(ItemID.HUNTINGBEAST_SPEEDY_FUR, 1);
            }),
            new OnXpDrop(Skill.HUNTER, 132).hasChatMessage("You retrieve the falcon as well as the fur of the dead kebbit.").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).consumer(() -> {
                storage.add(ItemID.HUNTINGBEAST_SILENT_FUR, 1);
            }),
            new OnXpDrop(Skill.HUNTER, 156).hasChatMessage("You retrieve the falcon as well as the fur of the dead kebbit.").requiredItem(ItemID.HG_FURPOUCH_SMALL_OPEN, ItemID.HG_FURPOUCH_MED_OPEN, ItemID.HG_FURPOUCH_LARGE_OPEN).consumer(() -> {
                storage.add(ItemID.HUNTINGBEAST_SPEEDY2_FUR, 1);
            })
        ));
    }
}
