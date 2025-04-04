package tictac7x.charges.item.storage;

import tictac7x.charges.customEvents.CustomItemContainerChanged;

import java.util.*;

public class StorageItems {
    final Map<Integer, StorageItem> items = new LinkedHashMap<>();

    public StorageItems() {}

    public StorageItems(final CustomItemContainerChanged itemContainerChanged) {
        for (final StorageItem item : itemContainerChanged.getItems()) {
            if (items.containsKey(item.getId())) continue;
            items.put(item.getId(), new StorageItem(item.getId(), itemContainerChanged.count(item.getId())));
        }
    }

    public int count(final int itemId) {
        return items.containsKey(itemId)
            ? items.get(itemId).getQuantity()
            : 0;
    }

    public void put(final StorageItem storageItem) {
        items.put(storageItem.getId(), storageItem);
    }

    public List<StorageItem> getItems() {
        final List<StorageItem> items = new ArrayList<>();

        for (final StorageItem item : this.items.values()) {
            items.add(new StorageItem(item.getId(), item.getQuantity()));
        }

        return items;
    }

    public boolean hasItem(final int itemId) {
        return items.containsKey(itemId);
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
