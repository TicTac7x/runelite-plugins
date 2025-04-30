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

public class U_HuntsmansKit extends ChargedItemWithStorage {
    public U_HuntsmansKit(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.huntsmans_kit, ItemId.HUNTSMANS_KIT, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.HUNTSMANS_KIT)
        };

        this.storage = storage.storableItems(
            new StorableItem(ItemId.BIRD_SNARE),
            new StorableItem(ItemId.BUTTERFLY_NET),
            new StorableItem(ItemId.BUTTERFLY_JAR),
            new StorableItem(ItemId.RABBIT_SNARE),
            new StorableItem(ItemId.SMALL_FISHING_NET),
            new StorableItem(ItemId.MAGIC_BOX),
            new StorableItem(ItemId.TEASING_STICK),
            new StorableItem(ItemId.WOOD_CAMO_TOP),
            new StorableItem(ItemId.WOOD_CAMO_LEGS),
            new StorableItem(ItemId.JUNGLE_CAMO_TOP),
            new StorableItem(ItemId.JUNGLE_CAMO_LEGS),
            new StorableItem(ItemId.LARUPIA_HAT),
            new StorableItem(ItemId.LARUPIA_TOP),
            new StorableItem(ItemId.LARUPIA_LEGS),
            new StorableItem(ItemId.KYATT_HAT),
            new StorableItem(ItemId.KYATT_TOP),
            new StorableItem(ItemId.KYATT_LEGS),
            new StorableItem(ItemId.GUILD_HUNTER_HEADWEAR),
            new StorableItem(ItemId.GUILD_HUNTER_TOP),
            new StorableItem(ItemId.GUILD_HUNTER_LEGS),
            new StorableItem(ItemId.GUILD_HUNTER_BOOTS),
            new StorableItem(ItemId.RING_OF_PURSUIT),
            new StorableItem(ItemId.NOOSE_WAND),
            new StorableItem(ItemId.MAGIC_BUTTERFLY_NET),
            new StorableItem(ItemId.BOX_TRAP),
            new StorableItem(ItemId.UNLIT_TORCH),
            new StorableItem(ItemId.ROPE),
            new StorableItem(ItemId.HUNTERS_SPEAR),
            new StorableItem(ItemId.POLAR_CAMO_TOP),
            new StorableItem(ItemId.POLAR_CAMO_LEGS),
            new StorableItem(ItemId.DESERT_CAMO_TOP),
            new StorableItem(ItemId.DESERT_CAMO_LEGS),
            new StorableItem(ItemId.GRAAHK_HEADDRESS),
            new StorableItem(ItemId.GRAAHK_TOP),
            new StorableItem(ItemId.GRAAHK_LEGS),
            new StorableItem(ItemId.HUNTER_HOOD),
            new StorableItem(ItemId.HUNTER_CAPE),
            new StorableItem(ItemId.HUNTER_CAPE_TRIMMED),
            new StorableItem(ItemId.IMPLING_JAR)
        );

        this.triggers = new TriggerBase[]{
            // Fill from inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onMenuOption("Fill", TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory),

            // Empty to inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).emptyStorageToInventory().onMenuOption("Empty", TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory),

            // Update from item container when viewing huntsmans kit contents.
            new OnItemContainerChanged(ItemContainerId.HUNTSMANS_KIT).updateStorage(),

            // Replace "Use" with proper Fill/Empty option.
            new OnMenuEntryAdded("Use").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),
            new OnMenuEntryAdded("Use").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }

    private String getMenuOptionForUse() {
        return storage.isStorableItemInInventory()
                ? TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory
                : TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory;
    }
}
