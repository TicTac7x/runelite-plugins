package tictac7x.charges.item.storage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.client.config.ConfigManager;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.ItemWithQuantity;
import tictac7x.charges.store.Store;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Storage {
    private final ChargedItemWithStorage chargedItem;
    private final String storageConfigKey;
    private final ConfigManager configManager;
    private final Store store;
    private final Gson gson;

    protected Map<Integer, StorageItem> storage = new LinkedHashMap<>();

    public Optional<Integer> maximumTotalQuantity = Optional.empty();
    public Optional<Integer> maximumTotalQuantityWithItemEquipped = Optional.empty();
    public Optional<int[]> maximumTotalQuantityWithItemEquippedItems = Optional.empty();
    public Optional<Boolean> showIndividualCharges = Optional.empty();
    private Optional<Integer> maximumIndividualQuantity = Optional.empty();
    private StorageItem[] storeableItems = new StorageItem[]{};


    public Storage(final ChargedItemWithStorage chargedItem, final String configKey, final ConfigManager configManager, final Store store, final Gson gson) {
        this.chargedItem = chargedItem;
        this.storageConfigKey = configKey + "_storage";
        this.configManager = configManager;
        this.store = store;
        this.gson = gson;
    }

    public Storage maximumTotalQuantity(final int quantity) {
        this.maximumTotalQuantity = Optional.of(quantity);
        return this;
    }

    public Storage maximumTotalQuantityWithEquippedItem(int quantity, final int ...itemIds) {
        this.maximumTotalQuantityWithItemEquipped = Optional.of(quantity);
        this.maximumTotalQuantityWithItemEquippedItems = Optional.of(itemIds);
        return this;
    }

    public Storage maximumIndividualQuantity(final int quantity) {
        this.maximumIndividualQuantity = Optional.of(quantity);
        return this;
    }

    public Storage showIndividualCharges() {
        this.showIndividualCharges = Optional.of(true);
        return this;
    }

    public Storage storeableItems(final StorageItem... storageItems) {
        this.storeableItems = storageItems;
        return this;
    }

    public void clear() {
        storage = new LinkedHashMap<>();
        save();
    }

    public void add(final int itemId, final int quantity) {
        if (getMaximumTotalQuantity().isPresent()) {
            if (getCharges() == getMaximumTotalQuantity().get()) {
                return;
            }
        }

        final Optional<StorageItem> item = getItem(itemId);
        put(itemId, (item.isPresent() ? item.get().getQuantity() : 0) + quantity);
    }

    public void add(final Optional<StorageItem> item, final int quantity) {
        if (!item.isPresent()) return;
        add(item.get().itemId, quantity);
    }

    public void put(final Optional<StorageItem> item, final int quantity) {
        if (!item.isPresent()) return;
        put(item.get().itemId, quantity);
    }

    public void clearAndPut(final Optional<StorageItem> item, final int quantity) {
        if (!item.isPresent()) return;
        clear();
        put(item.get().itemId, quantity);
    }

    public void remove(final Optional<StorageItem> item, final int quantity) {
        if (!item.isPresent()) return;
        remove(item.get().itemId, quantity);
    }

    public void remove(final int itemId, final int quantity) {
        final Optional<StorageItem> item = getItem(itemId);

        // Don't decrease quantity of unlimited storage item.
        if (item.isPresent() && item.get().getQuantity() == Charges.UNLIMITED) {
            return;
        }

        put(itemId, (item.isPresent() ? Math.max(0, item.get().getQuantity() - quantity) : 0));
    }

    public void removeAndPrioritizeInventory(final int itemId, final int quantity) {
        this.remove(itemId, Math.max(quantity - store.getInventoryItemQuantity(itemId), 0));
    }

    public void removeAndPrioritizeInventory(final Optional<Integer> itemId, final int quantity) {
        if (itemId.isPresent()) {
            this.removeAndPrioritizeInventory(itemId.get(), quantity);
        }
    }

    public void put(final int itemId, int quantity) {
        // -1 = item that was previously in the array, but that slot no longer has an item.
        // 6512 = empty item inside huntsmans kit.
        if (itemId == -1 || itemId == 6512) return;

        // Check for individual maximum quantity.
        if (maximumIndividualQuantity.isPresent() && quantity > maximumIndividualQuantity.get()) {
            quantity = maximumIndividualQuantity.get();
        }

        final Optional<Integer> maximumTotalQuantity = getMaximumTotalQuantity();
        if (maximumTotalQuantity.isPresent()) {
            int newTotalQuantity = 0;
            for (final StorageItem storageItem : storage.values()) {
                if (storageItem.itemId == itemId) continue;
                newTotalQuantity += storageItem.getQuantity();
            }
            newTotalQuantity += quantity; //Add outside the loop in case the item is not currently stored

            if (newTotalQuantity > maximumTotalQuantity.get()) {
                quantity -= newTotalQuantity - maximumTotalQuantity.get();
            }
        }

        final Optional<StorageItem> item = getItem(itemId);
        if (quantity == 0) {
            storage.remove(itemId);
        } else if (item.isPresent()) {
            item.get().setQuantity(quantity);
        } else {
            storage.put(itemId, new StorageItem(itemId, quantity));
        }

        save();
    }

    public void fillFromInventory() {
        for (final StorageItem storageItem : storeableItems) {
            add(storageItem.getId(), store.getPreviousInventoryItemQuantity(storageItem.getId()));
        }
    }

    public void fillFromInventoryIndividually(final int itemId) {
        for (final StorageItem storageItem : storeableItems) {
            if (storageItem.getId() == itemId) {
                add(storageItem.getId(), store.getPreviousInventoryItemQuantity(storageItem.getId()));
                return;
            }
        }
    }

    public void emptyToInventory() {
        for (final ItemWithQuantity itemDifference : store.getInventoryItemsDifference().items) {
            if (storage.containsKey(itemDifference.itemId)) {
                storage.get(itemDifference.itemId).quantity -= itemDifference.quantity;
            }
        }
    }

    public void emptyToBank() {
        for (final ItemWithQuantity itemDifference : store.getBankItemsDifference().items) {
            if (storage.containsKey(itemDifference.itemId)) {
                storage.get(itemDifference.itemId).quantity -= itemDifference.quantity;
            }
        }
    }

    public void emptyToInventoryReversed() {
        if (!store.inventory.isPresent()) return;

        int inventoryEmptySlots = store.getInventoryPreviouslyEmptySlots();

        // Reverse loop through the storage list
        for (int i = storage.size() - 1; i >= 0; i--) {
            final StorageItem storageItem = storage.get(i);

            // Empty storage until 0 inventory slots left.
            if (inventoryEmptySlots == 0) break;

            final int toEmptyStorageSlots = Math.min(inventoryEmptySlots, storageItem.getQuantity());
            put(storageItem.itemId, storageItem.getQuantity() - toEmptyStorageSlots);
            inventoryEmptySlots -= toEmptyStorageSlots;
        }
    }

    public void updateFromItemContainer(final ItemContainer itemContainer) {
        if (itemContainer == null) return;

        storage = new LinkedHashMap<>();

        for (final Item item : itemContainer.getItems()) {
            if (item != null) {
                put(item.getId(), item.getQuantity());
            }
        }
    }

    public int getCharges() {
        int charges = 0;

        for (final StorageItem item : storage.values()) {
            charges += item.getQuantity();
        }

        return charges;
    }

    public Map<Integer, StorageItem> getStorage() {
        return storage;
    }

    public void loadStorage() {
        storage = new LinkedHashMap<>();

        // Load storage from config.
        try {
            final String jsonString = configManager.getConfiguration(ChargesImprovedConfig.group, storageConfigKey);
            final JsonArray jsonStorage = (JsonArray) (new JsonParser()).parse(jsonString);

            for (final JsonElement jsonStorageItem : jsonStorage) {
                final StorageItem loadedItem = new StorageItem(
                    jsonStorageItem.getAsJsonObject().get("itemId").getAsInt(),
                    jsonStorageItem.getAsJsonObject().get("quantity").getAsInt()
                );

                // Update previous item quantity or put new into the storage.
                if (storage.containsKey(loadedItem.itemId)) {
                    storage.get(loadedItem.itemId).setQuantity(loadedItem.quantity);
                } else {
                    put(loadedItem.itemId, loadedItem.getQuantity());
                }
            }
        } catch (final Exception ignored) {}
    }

    private void save() {
        final JsonArray jsonStorage = new JsonArray();

        for (final StorageItem storageItem : storage.values()) {
            final JsonObject jsonItem = new JsonObject();
            jsonItem.addProperty("itemId", storageItem.itemId);
            jsonItem.addProperty("quantity", storageItem.getQuantity());
            jsonStorage.add(jsonItem);
        }

        configManager.setConfiguration(ChargesImprovedConfig.group, storageConfigKey, gson.toJson(jsonStorage));
    }

    private Optional<StorageItem> getItem(final int itemId) {
        if (storage.containsKey(itemId)) {
            return Optional.of(storage.get(itemId));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Integer> getMaximumTotalQuantity() {
        // Maximum storage from trigger item.
        for (final TriggerItem item : chargedItem.items) {
            if (chargedItem.itemId == item.itemId && item.maxCharges.isPresent()) {
                return item.maxCharges;
            }
        }

        // Maximum storage with specific item equipped.
        if (maximumTotalQuantityWithItemEquipped.isPresent() && maximumTotalQuantityWithItemEquippedItems.isPresent() && store.equipmentContainsItem(maximumTotalQuantityWithItemEquippedItems.get())) {
            return maximumTotalQuantityWithItemEquipped;
        }

        // Maximum storage.
        if (maximumTotalQuantity.isPresent()) {
            return maximumTotalQuantity;
        }

        return Optional.empty();
    }

    public final Optional<StorageItem> getStorageItemFromName(final String name) {
        // Perfect match.
        for (final StorageItem storageItem : storeableItems) {
            if (storageItem.checkName.isPresent() && name.equalsIgnoreCase(storageItem.checkName.get())) {
                return Optional.of(storageItem);
            }
        }

        // Partial match.
        for (final StorageItem storageItem : storeableItems) {
            if (storageItem.checkName.isPresent() && name.toLowerCase().contains(storageItem.checkName.get().toLowerCase())) {
                return Optional.of(storageItem);
            }
        }

        return Optional.empty();
    }

    public StorageItem[] getStoreableItems() {
        return storeableItems;
    }
}
