package tictac7x.charges.item.storage;

import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.game.ItemManager;

import java.util.ArrayList;
import java.util.List;

public class StorageItemContainerChanged {
    public final int itemContainerId;
    private final List<StorageItem> items;

    public StorageItemContainerChanged(final int itemContainerId, final List<StorageItem> items) {
        this.itemContainerId = itemContainerId;
        this.items = items;
    }

    public StorageItemContainerChanged(final ItemContainerChanged event, final ItemManager itemManager) {
        this.itemContainerId = event.getContainerId();
        this.items = new ArrayList<>();

        for (final Item item : event.getItemContainer().getItems()) {
            if (item == null || item.getId() == -1 || item.getId() == 6512) continue;

            final ItemComposition itemComposition = itemManager.getItemComposition(item.getId());
            items.add(new StorageItem(
                itemComposition.getPlaceholderTemplateId() != -1 ? itemComposition.getPlaceholderId() : item.getId(),
                itemComposition.getPlaceholderTemplateId() != -1 ? 0 : item.getQuantity()
            ));
        }
    }

    public List<StorageItem> getItems() {
        return items;
    }

    public int size() {
        return items.size();
    }

    public int getContainerId() {
        return itemContainerId;
    }

    public int count(final int itemId) {
        int count = 0;

        for (final StorageItem item : items) {
            if (item.getId() == itemId) {
                count += item.getQuantity();
            }
        }

        return count;
    }

    public boolean hasItem(final int itemId) {
        for (final StorageItem item : items) {
            if (item.getId() == itemId) {
                return true;
            }
        }

        return false;
    }
}
