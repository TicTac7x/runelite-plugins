package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class U_HuntsmansKit extends ChargedItemWithStorage {
    public U_HuntsmansKit(Provider provider) {
        super(TicTac7xChargesImprovedConfig.huntsmans_kit, ItemID.HUNTSMANS_KIT, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.HUNTSMANS_KIT)
        };

        this.storage = storage.storableItems(
            new StorableItem (ItemID.HUNTING_OJIBWAY_BIRD_SNARE),
            new StorableItem (ItemID.HUNTING_BUTTERFLY_NET),
            new StorableItem (ItemID.BUTTERFLY_JAR),
            new StorableItem (ItemID.HUNTING_SNARE),
            new StorableItem (ItemID.NET),
            new StorableItem (ItemID.MAGIC_IMP_BOX),
            new StorableItem (ItemID.HUNTING_TEASING_STICK),
            new StorableItem (ItemID.HUNTING_CAMOFLAUGE_ROBE_WOOD),
            new StorableItem (ItemID.HUNTING_TROUSERS_WOOD),
            new StorableItem (ItemID.HUNTING_CAMOFLAUGE_ROBE_JUNGLE),
            new StorableItem (ItemID.HUNTING_TROUSERS_JUNGLE),
            new StorableItem (ItemID.HUNTING_HAT_JAGUAR),
            new StorableItem (ItemID.HUNTING_TORSO_JAGUAR),
            new StorableItem (ItemID.HUNTING_TROUSERS_JAGUAR),
            new StorableItem (ItemID.HUNTING_HAT_TIGER),
            new StorableItem (ItemID.HUNTING_TORSO_TIGER),
            new StorableItem (ItemID.HUNTING_TROUSERS_TIGER),
            new StorableItem (ItemID.HG_HUNTER_HOOD),
            new StorableItem (ItemID.HG_HUNTER_TOP),
            new StorableItem (ItemID.HG_HUNTER_LEGS),
            new StorableItem (ItemID.HG_HUNTER_BOOTS),
            new StorableItem (ItemID.RING_OF_PURSUIT),
            new StorableItem (ItemID.NOOSE_WAND),
            new StorableItem (ItemID.II_MAGIC_BUTTERFLY_NET),
            new StorableItem (ItemID.HUNTING_BOX_TRAP),
            new StorableItem (ItemID.TORCH_UNLIT),
            new StorableItem (ItemID.ROPE),
            new StorableItem (ItemID.HG_HUNTER_SPEAR),
            new StorableItem (ItemID.HUNTING_CAMOFLAUGE_ROBE_POLAR),
            new StorableItem (ItemID.HUNTING_TROUSERS_POLAR),
            new StorableItem (ItemID.HUNTING_CAMOFLAUGE_ROBE_DESERT),
            new StorableItem (ItemID.HUNTING_TROUSERS_DESERT),
            new StorableItem (ItemID.HUNTING_HAT_LEOPARD),
            new StorableItem (ItemID.HUNTING_TORSO_LEOPARD),
            new StorableItem (ItemID.HUNTING_TROUSERS_LEOPARD),
            new StorableItem (ItemID.SKILLCAPE_HUNTING_HOOD),
            new StorableItem (ItemID.SKILLCAPE_HUNTING),
            new StorableItem (ItemID.SKILLCAPE_HUNTING_TRIMMED),
            new StorableItem (ItemID.II_IMPLING_JAR)
        );

        this.triggers.addAll(List.of(
            // Fill from inventory.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onMenuOption("Fill", TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory),

            // Empty to inventory.
            new OnItemContainerChanged(InventoryID.INV).emptyStorageToInventory().onMenuOption("Empty", TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory),

            // Update from item container when viewing huntsmans kit contents.
            new OnItemContainerChanged(InventoryID.HUNTSMANS_KIT).updateStorage(),

            // Replace "Use" with proper Fill/Empty option.
            new OnMenuEntryAdded("Use").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),
            new OnMenuEntryAdded("Use").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }

    private String getMenuOptionForUse() {
        return storage.isStorableItemInInventory()
                ? TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory
                : TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory;
    }
}
