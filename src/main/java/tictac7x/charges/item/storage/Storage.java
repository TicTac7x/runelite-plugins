package tictac7x.charges.item.storage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.ItemWithQuantity;
import tictac7x.charges.store.Store;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Storage {
    private final ChargedItemWithStorage chargedItem;
    private final String storageConfigKey;
    private final ItemManager itemManager;
    private final ConfigManager configManager;
    private final Store store;
    private final Gson gson;

    protected Map<Integer, StorageItem> storage = new LinkedHashMap<>();

    public Optional<Integer> maximumTotalQuantity = Optional.empty();
    public Optional<Integer> maximumTotalQuantityWithItemEquipped = Optional.empty();
    public Optional<int[]> maximumTotalQuantityWithItemEquippedItems = Optional.empty();
    public Optional<Boolean> showIndividualCharges = Optional.empty();
    public boolean holdsSingleType = false;
    public boolean emptyIsNegative = false;
    private Optional<Integer> maximumIndividualQuantity = Optional.empty();
    private StorableItem[] storableItems = new StorableItem[]{};


    public Storage(final ChargedItemWithStorage chargedItem, final String configKey, final ItemManager itemManager, final ConfigManager configManager, final Store store, final Gson gson) {
        this.chargedItem = chargedItem;
        this.storageConfigKey = configKey + "_storage";
        this.itemManager = itemManager;
        this.configManager = configManager;
        this.store = store;
        this.gson = gson;
    }

    public Storage setMaximumTotalQuantity(final int quantity) {
        this.maximumTotalQuantity = Optional.of(quantity);
        return this;
    }

    public Storage emptyIsNegative() {
        this.emptyIsNegative = true;
        return this;
    }
    
    public Storage setHoldsSingleType(final boolean holdsSingleType) {
        this.holdsSingleType = holdsSingleType;
        return this;
    }

    public Storage setMaximumTotalQuantityWithEquippedItem(int quantity, final int ...itemIds) {
        this.maximumTotalQuantityWithItemEquipped = Optional.of(quantity);
        this.maximumTotalQuantityWithItemEquippedItems = Optional.of(itemIds);
        return this;
    }

    public Storage setMaximumIndividualQuantity(final int quantity) {
        this.maximumIndividualQuantity = Optional.of(quantity);
        return this;
    }

    public Storage showIndividualCharges() {
        this.showIndividualCharges = Optional.of(true);
        return this;
    }

    public Storage storableItems(final StorableItem... storableItems) {
        this.storableItems = storableItems;
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
        put(itemId, (item.isPresent() ? item.get().quantity : 0) + quantity);
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
        clearAndPut(item.get().itemId, quantity);
    }

    public void clearAndPut(final int itemId, final int quantity) {
        clear();
        put(itemId, quantity);
    }

    public void remove(final Optional<StorageItem> item, final int quantity) {
        if (!item.isPresent()) return;
        remove(item.get().itemId, quantity);
    }

    public void remove(final int itemId, final int quantity) {
        final Optional<StorageItem> item = getItem(itemId);

        // Don't decrease quantity of unlimited storage item.
        if (item.isPresent() && item.get().quantity == Charges.UNLIMITED) {
            return;
        }

        put(itemId, (item.isPresent() ? Math.max(0, item.get().quantity - quantity) : 0));
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

        // Storage holds only one unique item at once check.
        if (holdsSingleType) {
            for (final StorageItem storageItem : storage.values()) {
                if (storageItem.itemId != itemId && storageItem.quantity > 0) {
                    return;
                }
            }
        }

        // Check for individual maximum quantity.
        if (maximumIndividualQuantity.isPresent() && quantity > maximumIndividualQuantity.get()) {
            quantity = maximumIndividualQuantity.get();
        }

        final Optional<Integer> maximumTotalQuantity = getMaximumTotalQuantity();
        if (maximumTotalQuantity.isPresent()) {
            int newTotalQuantity = 0;
            for (final StorageItem storageItem : storage.values()) {
                if (storageItem.itemId == itemId) continue;
                newTotalQuantity += storageItem.quantity;
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
            item.get().quantity = quantity;
        } else {
            storage.put(itemId, new StorageItem(itemId, quantity));
        }

        save();
    }

    public void fillFromInventory() {
        for (final ItemWithQuantity itemDifference : store.getInventoryItemsDifference().items) {
            if (isStorageItem(itemDifference) && itemDifference.quantity < 0) {
                add(itemDifference.itemId, Math.abs(itemDifference.quantity));
            }
        }
    }

    private boolean isStorageItem(final ItemWithQuantity item) {
        for (final StorageItem storageItem : storableItems) {
            if (storageItem.itemId == item.itemId) {
                return true;
            }
        }

        return false;
    }

    public void emptyToInventory() {
        for (final ItemWithQuantity itemDifference : store.getInventoryItemsDifference().items) {
            if (storage.containsKey(itemDifference.itemId)) {
                storage.get(itemDifference.itemId).quantity -= itemDifference.quantity;
            }
        }
    }

    public void emptyToInventoryWithoutItemContainerChanged() {
        int inventorySpaceFree = store.getInventoryEmptySlots();

        for (final StorageItem storageItem : storage.values()) {
            if (storageItem.quantity > 0) {
                final int toRemove = Math.min(storageItem.quantity, inventorySpaceFree);
                remove(storageItem.itemId, toRemove);
                inventorySpaceFree -= toRemove;
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
            charges += item.quantity;
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
            final String jsonString = configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, storageConfigKey);
            final JsonArray jsonStorage = (JsonArray) (new JsonParser()).parse(jsonString);

            for (final JsonElement jsonStorageItem : jsonStorage) {
                final StorageItem loadedItem = new StorageItem(
                    jsonStorageItem.getAsJsonObject().get("itemId").getAsInt(),
                    jsonStorageItem.getAsJsonObject().get("quantity").getAsInt()
                );

                // Update previous item quantity or put new into the storage.
                if (storage.containsKey(loadedItem.itemId)) {
                    storage.get(loadedItem.itemId).quantity = loadedItem.quantity;
                } else {
                    put(loadedItem.itemId, loadedItem.quantity);
                }
            }
        } catch (final Exception ignored) {}
    }

    private void save() {
        final JsonArray jsonStorage = new JsonArray();

        for (final StorageItem storageItem : storage.values()) {
            final JsonObject jsonItem = new JsonObject();
            jsonItem.addProperty("itemId", storageItem.itemId);
            jsonItem.addProperty("quantity", storageItem.quantity);
            jsonStorage.add(jsonItem);
        }

        configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, storageConfigKey, gson.toJson(jsonStorage));
    }

    private Optional<StorageItem> getItem(final int itemId) {
        if (storage.containsKey(itemId)) {
            return Optional.of(storage.get(itemId));
        } else {
            return Optional.empty();
        }
    }

    public boolean isEmpty() {
        for (final StorageItem storageItem : storage.values()) {
            if (storageItem.quantity > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isFull() {
        if (maximumTotalQuantity.isPresent()) {
            int quantity = 0;

            for (final StorageItem storageItem : storage.values()) {
                quantity += storageItem.quantity;
            }

            return quantity == maximumTotalQuantity.get();
        }

        return false;
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
        // Based on checkName.
        for (final StorableItem storableItem : storableItems) {
            if (storableItem.checkName.isPresent()) {
                for (final String checkName :storableItem.checkName.get()) {
                    if (
                        name.equalsIgnoreCase(checkName) ||
                        name.toLowerCase().contains(checkName.toLowerCase()) ||
                        name.contains(itemManager.getItemComposition(storableItem.itemId).getName())
                    ) {
                        return Optional.of(storableItem);
                    }
                }
            }
        }

        return Optional.empty();
    }

    public StorableItem[] getStorableItems() {
        return storableItems;
    }

    public int getStorageItemOrder(final StorageItem storageItem) {
        for (final StorableItem storableItem : storableItems) {
            if (storableItem.itemId == storageItem.itemId) {
                if (storableItem.order.isPresent()) {
                    return storableItem.order.get();
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    public String getStorageItemName(final StorageItem storageItem) {
        for (final StorableItem storableItem : storableItems) {
            if (storableItem.itemId == storageItem.itemId) {
                if (storableItem.displayName.isPresent()) {
                    return storableItem.displayName.get();
                }
            }
        }

        return itemManager.getItemComposition(storageItem.itemId).getName();
    }
}
