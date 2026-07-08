package tictac7x.charges.item.storage;

import tictac7x.charges.events.CustomItemContainerChanged;

import java.util.*;

public class StorageItems {
    final Map<Integer, StorageItem> items = new LinkedHashMap<>();

    public StorageItems() {}

    public StorageItems(final CustomItemContainerChanged itemContainerChanged) {
        for (final StorageItem item : itemContainerChanged.getItems()) {
            if (items.containsKey(item.itemId)) continue;
            items.put(item.itemId, new StorageItem(item.itemId, itemContainerChanged.count(item.itemId)));
        }
    }

    public int count(final int itemId) {
        return items.containsKey(itemId)
            ? items.get(itemId).getQuantity()
            : 0;
    }

    public void put(final StorageItem storageItem) {
        items.put(storageItem.itemId, storageItem);
    }

    public List<StorageItem> getItems() {
        final List<StorageItem> items = new ArrayList<>();

        for (final StorageItem item : this.items.values()) {
            items.add(new StorageItem(item.itemId, item.getQuantity()));
        }

        return items;
    }

    public boolean hasItem(final int itemId) {
        return count(itemId) > 0;
    }

    public void clear() {
        items.clear();
    }

    public void remove(final int itemId) {
        items.remove(itemId);
    }

    public Optional<StorageItem> getItem(final int itemId) {
        return items.containsKey(itemId)
            ? Optional.of(items.get(itemId))
            : Optional.empty();
    }
}
