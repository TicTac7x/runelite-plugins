package tictac7x.charges.item.storage;

import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.ItemContainerChanged;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class StorageItems {
    final Map<Integer, StorageItem> items = new LinkedHashMap<>();

    public StorageItems() {}

    public StorageItems(final ItemContainer itemContainer) {
        if (itemContainer == null) return;

        for (final Item item : itemContainer.getItems()) {
            if (item == null || item.getId() <= 0 || items.containsKey(item.getId())) continue;
            items.put(item.getId(), new StorageItem(item.getId(), item.getQuantity()));
        }
    }

    public StorageItems(final ItemContainerChanged event) {
        this(event.getItemContainer());
    }

    public int count(final int itemId) {
        return items.containsKey(itemId)
            ? items.get(itemId).getQuantity()
            : 0;
    }

    public void put(final StorageItem storageItem) {
        items.put(storageItem.itemId, storageItem);
    }

    public Collection<StorageItem> getItems() {
        return items.values();
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
