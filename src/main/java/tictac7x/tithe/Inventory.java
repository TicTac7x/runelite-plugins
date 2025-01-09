package tictac7x.tithe;

import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.events.ItemContainerChanged;

public class Inventory {
    private final TicTac7xTithePlugin plugin;
    private int fruits = 0;

    public Inventory(final TicTac7xTithePlugin plugin) {
        this.plugin = plugin;
    }

    public void onItemContainerChanged(final ItemContainerChanged event) {
        if (plugin.inTitheFarm() && event.getContainerId() == InventoryID.INVENTORY.getId()) {
            final ItemContainer inventory = event.getItemContainer();
            fruits = (
                inventory.count(ItemID.GOLOVANOVA_FRUIT) +
                inventory.count(ItemID.BOLOGANO_FRUIT) +
                inventory.count(ItemID.LOGAVANO_FRUIT)
            );
        }
    }

    public int getFruits() {
        return fruits;
    }
}
