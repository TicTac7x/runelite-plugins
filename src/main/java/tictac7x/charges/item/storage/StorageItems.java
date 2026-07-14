package tictac7x.charges.item.storage;

import tictac7x.charges.events.*;
import java.util.*;

public class StorageItems {
    Map<Integer, StorageItem> items = new LinkedHashMap<>();

    public StorageItems() {}

    public StorageItems(CustomItemContainerChanged itemContainerChanged) {
        for (StorageItem item : itemContainerChanged.getItems()) {
            if (items.containsKey(item.itemId)) continue;
            items.put(item.itemId, new StorageItem(item.itemId, itemContainerChanged.count(item.itemId)));
        }
    }

    public int count(int itemId) {
        return items.containsKey(itemId)
            ? items.get(itemId).getQuantity()
            : 0;
    }

    public void put(StorageItem storageItem) {
        items.put(storageItem.itemId, storageItem);
    }

    public List<StorageItem> getItems() {
        List<StorageItem> items = new ArrayList<>();

        for (StorageItem item : this.items.values()) {
            items.add(new StorageItem(item.itemId, item.getQuantity()));
        }

        return items;
    }

    public boolean hasItem(int itemId) {
        return count(itemId) > 0;
    }

    public void clear() {
        items.clear();
    }

    public void remove(int itemId) {
        items.remove(itemId);
    }

    public Optional<StorageItem> getItem(int itemId) {
        return items.containsKey(itemId)
            ? Optional.of(items.get(itemId))
            : Optional.empty();
    }
}
