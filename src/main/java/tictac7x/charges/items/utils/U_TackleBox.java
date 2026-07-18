package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class U_TackleBox extends ChargedItemWithStorage {
    public U_TackleBox(Provider provider) {
        super(TicTac7xChargesImprovedConfig.tackle_box, ItemID.TACKLE_BOX, provider);

        this.storage = storage.storableItems(
            new StorableItem(ItemID.TRAWLER_REWARD_HAT),
            new StorableItem(ItemID.TRAWLER_REWARD_TOP),
            new StorableItem(ItemID.TRAWLER_REWARD_LEGS),
            new StorableItem(ItemID.TRAWLER_REWARD_BOOTS),
            new StorableItem(ItemID.SPIRIT_ANGLER_HAT),
            new StorableItem(ItemID.SPIRIT_ANGLER_TOP),
            new StorableItem(ItemID.SPIRIT_ANGLER_LEGS),
            new StorableItem(ItemID.SPIRIT_ANGLER_BOOTS),
            new StorableItem(ItemID.SPIRIT_FLAKES),
            new StorableItem(ItemID.HUNDRED_PIRATE_DIVING_HELMET),
            new StorableItem(ItemID.MUDSKIPPER_FLIPPERS),
            new StorableItem(ItemID.DARK_FLIPPERS),
            new StorableItem(ItemID.HUNDRED_PIRATE_DIVING_BACKPACK),
            new StorableItem(ItemID.TINY_NET),
            new StorableItem(ItemID.ZEAH_BLESSING_EASY),
            new StorableItem(ItemID.ZEAH_BLESSING_MEDIUM),
            new StorableItem(ItemID.ZEAH_BLESSING_HARD),
            new StorableItem(ItemID.ZEAH_BLESSING_ELITE),
            new StorableItem(ItemID.HARPOON),
            new StorableItem(ItemID.HUNTING_BARBED_HARPOON),
            new StorableItem(ItemID.DRAGON_HARPOON),
            new StorableItem(ItemID.TRAILBLAZER_HARPOON_NO_INFERNAL),
            new StorableItem(ItemID.TRAILBLAZER_RELOADED_HARPOON_NO_INFERNAL),
            new StorableItem(ItemID.INFERNAL_HARPOON),
            new StorableItem(ItemID.INFERNAL_HARPOON_EMPTY),
            new StorableItem(ItemID.TRAILBLAZER_HARPOON_EMPTY),
            new StorableItem(ItemID.TRAILBLAZER_RELOADED_HARPOON_EMPTY),
            new StorableItem(ItemID.TRAILBLAZER_HARPOON),
            new StorableItem(ItemID.TRAILBLAZER_RELOADED_HARPOON),
            new StorableItem(ItemID.CRYSTAL_HARPOON),
            new StorableItem(ItemID.GAUNTLET_HARPOON),
            new StorableItem(ItemID.CRYSTAL_HARPOON_INACTIVE),
            new StorableItem(ItemID.MERFOLK_TRIDENT),
            new StorableItem(ItemID.FISHING_ROD),
            new StorableItem(ItemID.FISHINGROD_PEARL),
            new StorableItem(ItemID.FLY_FISHING_ROD),
            new StorableItem(ItemID.FISHINGROD_PEARL_FLY),
            new StorableItem(ItemID.OILY_FISHING_ROD),
            new StorableItem(ItemID.FISHINGROD_PEARL_OILY),
            new StorableItem(ItemID.BRUT_FISHING_ROD),
            new StorableItem(ItemID.FISHINGROD_PEARL_BRUT),
            new StorableItem(ItemID.NET),
            new StorableItem(ItemID.BIG_NET),
            new StorableItem(ItemID.FOSSIL_DRIFT_NET),
            new StorableItem(ItemID.LOBSTER_POT),
            new StorableItem(ItemID.TBWT_KARAMBWAN_VESSEL),
            new StorableItem(ItemID.TBWT_KARAMBWAN_VESSEL_LOADED_WITH_KARAMBWANJI),
            new StorableItem(ItemID.TBWT_RAW_KARAMBWANJI),
            new StorableItem(ItemID.FISHING_BAIT),
            new StorableItem(ItemID.FEATHER),
            new StorableItem(ItemID.WILDERNESS_FISHING_BAIT),
            new StorableItem(ItemID.PISCARILIUS_SANDWORMS),
            new StorableItem(ItemID.BRUT_FISH_CUTS),
            new StorableItem(ItemID.FISH_CHUNKS),
            new StorableItem(ItemID._1DOSEFISHERSPOTION),
            new StorableItem(ItemID._2DOSEFISHERSPOTION),
            new StorableItem(ItemID._3DOSEFISHERSPOTION),
            new StorableItem(ItemID._4DOSEFISHERSPOTION),
            new StorableItem(ItemID.AERIAL_FISHING_PEARL),
            new StorableItem(ItemID.HUNTING_STRIPY_BIRD_FEATHER),
            new StorableItem(ItemID.DIABOLIC_WORMS),
            new StorableItem(ItemID.SHARK_LURE)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.TACKLE_BOX),
        };

        this.triggers.addAll(List.of(
            // Fill from inventory.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onMenuOption("Fill", TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory),

            // Empty to inventory.
            new OnItemContainerChanged(InventoryID.INV).emptyStorageToInventory().onMenuOption("Empty", TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory),

            // Use storable item on tackle box.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseChargedItemOnStorageItem(storage.getStorableItems()),
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Update from item container when viewing tackle box contents.
            new OnItemContainerChanged(InventoryID.TACKLE_BOX).updateStorage(),

            // Replace "Use" with proper Fill/Empty option.
            new OnMenuEntryAdded("Use").replaceOptionConsumer(this::getMenuOptionForUse).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),
            new OnMenuEntryAdded("Use").replaceOptionConsumer(this::getMenuOptionForUse).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }

    private String getMenuOptionForUse() {
        return storage.isStorableItemInInventory()
            ? TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory
            : TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory;
    }
}
