package tictac7x.charges.items.utils;

import tictac7x.charges.store.*;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemContainerId;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.ids.WidgetId;

public class U_TackleBox extends ChargedItemWithStorage {
    public U_TackleBox(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.tackle_box, ItemId.TACKLE_BOX, provider);
        this.storage = storage.storableItems(
            new StorableItem(ItemId.ANGLER_HAT),
            new StorableItem(ItemId.ANGLER_TOP),
            new StorableItem(ItemId.ANGLER_WADERS),
            new StorableItem(ItemId.ANGLER_BOOTS),
            new StorableItem(ItemId.SPIRIT_ANGLER_HEADBAND),
            new StorableItem(ItemId.SPIRIT_ANGLER_TOP),
            new StorableItem(ItemId.SPIRIT_ANGLER_WADERS),
            new StorableItem(ItemId.SPIRIT_ANGLER_BOOTS),
            new StorableItem(ItemId.SPIRIT_FLAKES),
            new StorableItem(ItemId.FISHBOWL_HELMET),
            new StorableItem(ItemId.FLIPPERS),
            new StorableItem(ItemId.DARK_FLIPPERS),
            new StorableItem(ItemId.DIVING_APPARATUS),
            new StorableItem(ItemId.TINY_NET),
            new StorableItem(ItemId.RADAS_BLESSING_1),
            new StorableItem(ItemId.RADAS_BLESSING_2),
            new StorableItem(ItemId.RADAS_BLESSING_3),
            new StorableItem(ItemId.RADAS_BLESSING_4),
            new StorableItem(ItemId.HARPOON),
            new StorableItem(ItemId.BARBTAIL_HARPOON),
            new StorableItem(ItemId.DRAGON_HARPOON),
            new StorableItem(ItemId.DRAGON_HARPOON_OR),
            new StorableItem(ItemId.DRAGON_HARPOON_OR_30349),
            new StorableItem(ItemId.INFERNAL_HARPOON),
            new StorableItem(ItemId.INFERNAL_HARPOON_UNCHARGED),
            new StorableItem(ItemId.INFERNAL_HARPOON_UNCHARGED_25367),
            new StorableItem(ItemId.INFERNAL_HARPOON_UNCHARGED_30343),
            new StorableItem(ItemId.INFERNAL_HARPOON_OR),
            new StorableItem(ItemId.INFERNAL_HARPOON_OR_30342),
            new StorableItem(ItemId.CRYSTAL_HARPOON),
            new StorableItem(ItemId.CRYSTAL_HARPOON_23864),
            new StorableItem(ItemId.CRYSTAL_HARPOON_INACTIVE),
            new StorableItem(ItemId.MERFOLK_TRIDENT),
            new StorableItem(ItemId.FISHING_ROD),
            new StorableItem(ItemId.PEARL_FISHING_ROD),
            new StorableItem(ItemId.FLY_FISHING_ROD),
            new StorableItem(ItemId.PEARL_FLY_FISHING_ROD),
            new StorableItem(ItemId.OILY_FISHING_ROD),
            new StorableItem(ItemId.OILY_PEARL_FISHING_ROD),
            new StorableItem(ItemId.BARBARIAN_ROD),
            new StorableItem(ItemId.PEARL_BARBARIAN_ROD),
            new StorableItem(ItemId.SMALL_FISHING_NET),
            new StorableItem(ItemId.BIG_FISHING_NET),
            new StorableItem(ItemId.DRIFT_NET),
            new StorableItem(ItemId.LOBSTER_POT),
            new StorableItem(ItemId.KARAMBWAN_VESSEL),
            new StorableItem(ItemId.KARAMBWAN_VESSEL_3159),
            new StorableItem(ItemId.RAW_KARAMBWANJI),
            new StorableItem(ItemId.FISHING_BAIT),
            new StorableItem(ItemId.FEATHER),
            new StorableItem(ItemId.DARK_FISHING_BAIT),
            new StorableItem(ItemId.SANDWORMS),
            new StorableItem(ItemId.FISH_OFFCUTS),
            new StorableItem(ItemId.FISH_CHUNKS),
            new StorableItem(ItemId.FISHING_POTION_1),
            new StorableItem(ItemId.FISHING_POTION_2),
            new StorableItem(ItemId.FISHING_POTION_3),
            new StorableItem(ItemId.FISHING_POTION_4),
            new StorableItem(ItemId.MOLCH_PEARL),
            new StorableItem(ItemId.STRIPY_FEATHER)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TACKLE_BOX),
        };

        this.triggers = new TriggerBase[]{
            // Fill from inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onMenuOption("Fill", TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory),

            // Empty to inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).emptyStorageToInventory().onMenuOption("Empty", TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory),

            // Use storable item on kit.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onUseChargedItemOnStorageItem(storage.getStorableItems()),
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Update from item container when viewing huntsmans kit contents.
            new OnItemContainerChanged(ItemContainerId.TACKLE_BOX).updateStorage(),

            // Replace "Use" with proper Fill/Empty option.
            new OnMenuEntryAdded("Use").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),
            new OnMenuEntryAdded("Use").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }

    private String getMenuOptionForUse() {
        return storage.isStorableItemInInventory()
            ? TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory
            : TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory;
    }
}
