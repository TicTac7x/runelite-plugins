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

public class U_MeatPouch extends ChargedItemWithStorageEmptyable {
    public U_MeatPouch(Provider provider) {
        super(TicTac7xChargesImprovedConfig.meat_pouch, ItemID.HG_MEATPOUCH_SMALL, provider);
        this.storage = storage.storableItems(
            // Tracking.
            new StorableItem (ItemID.SPIT_RAW_BEAST_MEAT),

            // Deadfall.
            new StorableItem (ItemID.HUNTINGBEAST_WILD_MEAT),
            new StorableItem (ItemID.HUNTINGBEAST_BARBED_MEAT),
            new StorableItem (ItemID.HUNTING_FENNECFOX_MEAT),

            // Pitfalls.
            new StorableItem (ItemID.HUNTING_LARUPIA_MEAT),
            new StorableItem (ItemID.HUNTING_GRAAHK_MEAT),
            new StorableItem (ItemID.HUNTING_KYATT_MEAT),
            new StorableItem (ItemID.HUNTING_ANTELOPESUN_MEAT),
            new StorableItem (ItemID.HUNTING_ANTELOPEMOON_MEAT),

            // Aerial.
            new StorableItem (ItemID.HUNTINGBEAST_SPEEDY2_MEAT)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.HG_MEATPOUCH_SMALL).maxCharges(14),
            new TriggerItem(ItemID.HG_MEATPOUCH_SMALL_OPEN).maxCharges(14),
            new TriggerItem(ItemID.HG_MEATPOUCH_LARGE).maxCharges(28),
            new TriggerItem(ItemID.HG_MEATPOUCH_LARGE_OPEN).maxCharges(28),
        };

        this.triggers.addAll(List.of(
            // Empty.
            new OnChatMessage("Your meat pouch is currently holding 0 meat").emptyStorage(),
            new OnChatMessage("Your meat pouch is empty.").emptyStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(InventoryID.INV).emptyStorageToInventory().onMenuOption("Empty"),

            // Empty to bank.
            new OnItemContainerChanged(InventoryID.BANK).emptyStorageToBank().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank),

            // Empty from deposit box.
            new OnMenuOptionClicked(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).onItemClick().isWidgetVisible(WidgetId.DEPOSIT_BOX).emptyStorage(),

            // Use meat on pouch.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide(),

            // Tracking.
            new OnChatMessage("You manage to noose a polar kebbit that is hiding in the snowdrift.").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.SPIT_RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a common kebbit that is hiding in the bush.").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.SPIT_RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a Feldip weasel that is hiding in the bush.").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.SPIT_RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a desert devil that is hiding in the sand.").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.SPIT_RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a razor-backed kebbit that is hiding in the bush.").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.SPIT_RAW_BEAST_MEAT),

            // Deadfall.
            new OnChatMessage("You've caught a wild kebbit.").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.HUNTINGBEAST_WILD_MEAT),
            new OnChatMessage("You've caught a barb-tailed kebbit.").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.HUNTINGBEAST_BARBED_MEAT),
            new OnChatMessage("You've caught a pyre fox.").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.HUNTING_FENNECFOX_MEAT),

            // Pitfalls.
            new OnChatMessage("You've caught a spined larupia!").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.HUNTING_LARUPIA_MEAT),
            new OnChatMessage("You've caught a horned graahk!").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.HUNTING_GRAAHK_MEAT),
            new OnChatMessage("You've caught a sabre-?toothed kyatt!").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.HUNTING_KYATT_MEAT),
            new OnChatMessage("You've caught a sunlight antelope!").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.HUNTING_ANTELOPESUN_MEAT),
            new OnChatMessage("You've caught a moonlight antelope!").requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).addToStorage (ItemID.HUNTING_ANTELOPEMOON_MEAT),

            // Aerial.
            new OnXpDrop(Skill.HUNTER, 156).requiredItem (ItemID.HG_MEATPOUCH_SMALL_OPEN, ItemID.HG_MEATPOUCH_LARGE_OPEN).hasChatMessage("You retrieve the falcon as well as the fur of the dead kebbit.").consumer(() -> {
                storage.add(ItemID.HUNTINGBEAST_SPEEDY2_MEAT, 1);
            })
        ));
    }
}
