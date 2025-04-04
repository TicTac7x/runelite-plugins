package tictac7x.charges.customEvents;

import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.item.storage.StorageItem;

import java.util.ArrayList;
import java.util.List;

public class CustomItemContainerChanged {
    public final int itemContainerId;
    private final List<StorageItem> items;

    public CustomItemContainerChanged(final int itemContainerId, final List<StorageItem> items) {
        this.itemContainerId = itemContainerId;
        this.items = items;
    }

    public CustomItemContainerChanged(final ItemContainerChanged event, final ItemManager itemManager) {
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

    public CustomItemContainerChanged(final CustomItemContainerChanged previousItemContainerChanged) {
        this.itemContainerId = previousItemContainerChanged.itemContainerId;
        this.items = new ArrayList<>();

        for (final StorageItem item : previousItemContainerChanged.getItems()) {
            items.add(new StorageItem(item.getId(), item.getQuantity()));
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

    public void addStackableItem(final StorageItem itemToAdd) {
        for (final StorageItem item : items) {
            if (item.getId() == itemToAdd.getId()) {
                item.increaseQuantity(itemToAdd.getQuantity());
                return;
            }
        }
    }

    public void addNonStackableItem(final StorageItem itemToAdd) {
        for (int i = 0; i < itemToAdd.getQuantity(); i++) {
            items.add(new StorageItem(itemToAdd.getId(), 1));
        }
    }

    @Override
    public String toString() {
        String string = "ITEM CONTAINER CHANGED: | item container id: " + itemContainerId + "\r\n";

        for (final StorageItem item : items) {
            string += item.getId() + ", quantity: " + item.getQuantity() + "\r\n";
        }

        return string;
    }
}
