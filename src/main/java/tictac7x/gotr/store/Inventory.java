package tictac7x.gotr.store;

import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;

import java.util.Optional;

public class Inventory {
    private Optional<ItemContainer> inventory = Optional.empty();

    public void onItemContainerChanged(final ItemContainer itemContainer) {
        if (itemContainer.getId() == InventoryID.INVENTORY.getId()) {
            inventory = Optional.of(itemContainer);
        }
    }

    public boolean hasTeleporterTalisman(final Teleporter teleporter) {
        return inventory.isPresent() && inventory.get().contains(teleporter.talismanItemId);
    }

    public boolean hasGuardianStones() {
        return hasElementalGuardianStones() || hasCatalyticGuardianStones() || hasPolyelementalGuardianStones();
    }

    public boolean hasGuardianEssence() {
        return inventory.isPresent() && inventory.get().contains(ItemID.GUARDIAN_ESSENCE);
    }

    public boolean hasElementalGuardianStones() {
        return inventory.isPresent() && inventory.get().contains(ItemID.ELEMENTAL_GUARDIAN_STONE);
    }

    public boolean hasPolyelementalGuardianStones() {
        return inventory.isPresent() && inventory.get().contains(ItemID.POLYELEMENTAL_GUARDIAN_STONE);
    }

    public boolean hasCatalyticGuardianStones() {
        return inventory.isPresent() && inventory.get().contains(ItemID.CATALYTIC_GUARDIAN_STONE);
    }

    public boolean hasCell() {
        return inventory.isPresent() && (inventory.get().contains(ItemID.WEAK_CELL) || inventory.get().contains(ItemID.MEDIUM_CELL) || inventory.get().contains(ItemID.STRONG_CELL) || inventory.get().contains(ItemID.OVERCHARGED_CELL));
    }

    public boolean hasUnchargedCells() {
        return inventory.isPresent() && inventory.get().contains(ItemID.UNCHARGED_CELL);
    }

    public boolean hasPieceOfEyeEquipmentInInventory() {
        if (inventory.isPresent()) {
            return (
                inventory.get().contains(ItemID.HAT_OF_THE_EYE) ||
                inventory.get().contains(ItemID.HAT_OF_THE_EYE_BLUE) ||
                inventory.get().contains(ItemID.HAT_OF_THE_EYE_GREEN) ||
                inventory.get().contains(ItemID.HAT_OF_THE_EYE_RED) ||

                inventory.get().contains(ItemID.ROBE_TOP_OF_THE_EYE) ||
                inventory.get().contains(ItemID.ROBE_TOP_OF_THE_EYE_BLUE) ||
                inventory.get().contains(ItemID.ROBE_TOP_OF_THE_EYE_GREEN) ||
                inventory.get().contains(ItemID.ROBE_TOP_OF_THE_EYE_RED) ||

                inventory.get().contains(ItemID.ROBE_BOTTOMS_OF_THE_EYE) ||
                inventory.get().contains(ItemID.ROBE_BOTTOMS_OF_THE_EYE_BLUE) ||
                inventory.get().contains(ItemID.ROBE_BOTTOMS_OF_THE_EYE_GREEN) ||
                inventory.get().contains(ItemID.ROBE_BOTTOMS_OF_THE_EYE_RED) ||

                inventory.get().contains(ItemID.BOOTS_OF_THE_EYE)
            );
        }

        return false;
    }
}
