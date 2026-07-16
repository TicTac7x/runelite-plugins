package tictac7x.charges.item.storage;

import com.google.gson.*;
import tictac7x.charges.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.utils.*;

import java.util.*;
import java.util.stream.*;

public class Storage {
    private ChargedItemWithStorage chargedItem;
    private String storageConfigKey;
    private Provider provider;

    protected StorageItems storage = new StorageItems();

    public Optional<Integer> maximumTotalQuantity = Optional.empty();
    public Optional<Integer> maximumTotalQuantityWithItemEquipped = Optional.empty();
    public Optional<int[]> maximumTotalQuantityWithItemEquippedItems = Optional.empty();
    public Optional<Boolean> showIndividualCharges = Optional.empty();
    public boolean holdsSingleType = false;
    public boolean emptyIsNegative = false;
    private Optional<Integer> maximumIndividualQuantity = Optional.empty();
    private StorableItem[] storableItems = new StorableItem[]{};
    public Optional<MaximumComboQuantity> maximumTotalComboQuantity = Optional.empty();


    public Storage(ChargedItemWithStorage chargedItem, String configKey, Provider provider) {
        this.chargedItem = chargedItem;
        this.storageConfigKey = configKey + "_storage";
        this.provider = provider;
    }

    public Storage setMaximumTotalQuantity(int quantity) {
        this.maximumTotalQuantity = Optional.of(quantity);
        return this;
    }

    public Storage emptyIsNegative() {
        this.emptyIsNegative = true;
        return this;
    }
    
    public Storage setHoldsSingleType(boolean holdsSingleType) {
        this.holdsSingleType = holdsSingleType;
        return this;
    }

    public Storage setMaximumTotalQuantityWithEquippedItem(int quantity, int ...itemIds) {
        this.maximumTotalQuantityWithItemEquipped = Optional.of(quantity);
        this.maximumTotalQuantityWithItemEquippedItems = Optional.of(itemIds);
        return this;
    }

    public Storage setMaximumIndividualQuantity(int quantity) {
        this.maximumIndividualQuantity = Optional.of(quantity);
        return this;
    }

    public Storage showIndividualCharges() {
        this.showIndividualCharges = Optional.of(true);
        return this;
    }

    public Storage storableItems(StorableItem... storableItems) {
        this.storableItems = storableItems;
        return this;
    }

    public Storage storableItems(List<StorableItem> storableItems) {
        this.storableItems = storableItems.toArray(StorableItem[]::new);;
        return this;
    }

    public Storage addStorableItems(StorableItem ...storableItems) {
        this.storableItems = Stream.concat(
                Arrays.stream(this.storableItems),
                Arrays.stream(storableItems)
        ).toArray(StorableItem[]::new);
        return this;
    }

    public Storage addStorableItems(List<StorableItem> storableItems) {
        this.storableItems = Stream.concat(
                Arrays.stream(this.storableItems),
                storableItems.stream()
        ).toArray(StorableItem[]::new);
        return this;
    }

    public void clear() {
        storage.clear();
        save();
    }

    public void add(int itemId, int quantity) {
        if (getMaximumTotalQuantity().isPresent()) {
            if (getCharges() == getMaximumTotalQuantity().get()) {
                return;
            }
        }

        Optional<StorageItem> item = getItem(itemId);
        put(itemId, (item.isPresent() ? item.get().getQuantity() : 0) + quantity);
    }

    public void add(StorageItem item) {
        add(item.itemId, item.getQuantity());
    }

    public void add(Optional<StorageItem> item) {
        if (!item.isPresent()) return;
        add(item.get().itemId, item.get().getQuantity());
    }

    public void put(StorageItem item) {
        put(item.itemId, item.getQuantity());
    }

    public void put(Optional<StorageItem> item) {
        if (item.isPresent()) {
            put(item.get());
        }
    }

    public void clearAndPut(StorageItem item) {
        clearAndPut(item.itemId, item.getQuantity());
    }

    public void clearAndPut(Optional<StorageItem> item) {
        clear();
        put(item);
    }

    public void clearAndPut(int itemId, int quantity) {
        clear();
        put(itemId, quantity);
    }

    public void remove(Optional<StorageItem> item) {
        if (!item.isPresent()) return;
        remove(item.get().itemId, item.get().getQuantity());
    }

    public void remove (int itemId) {
        put(itemId, 0);
    }

    public void remove(int itemId, int quantity) {
        Optional<StorageItem> item = getItem(itemId);

        // Don't decrease quantity of unlimited storage item.
        if (item.isPresent() && item.get().getQuantity() == ChargeId.UNLIMITED) {
            return;
        }

        put(itemId, (item.isPresent() ? Math.max(0, item.get().getQuantity() - quantity) : 0));
    }

    public void removeAndPrioritizeInventory(int itemId, int quantity) {
        this.remove(itemId, Math.max(quantity - provider.store.getInventoryItemQuantity(itemId), 0));
    }

    public void removeAndPrioritizeInventory(Optional<Integer> itemId, int quantity) {
        if  (itemId.isPresent()) {
            this.removeAndPrioritizeInventory (itemId.get(), quantity);
        }
    }

    public void put(int itemId, int quantity) {
        // -1 = item that was previously in the array, but that slot no longer has an item.
        // 6512 = empty item inside huntsmans kit.
        if (itemId == -1 || itemId == 6512) return;

        boolean storableCheck = false;
        for (StorableItem item : storableItems) {
            if (item.itemId == itemId) {
                storableCheck = true;
                break;
            }
        }
        if (!storableCheck) return;

        // Storage holds only one unique item at once check.
        if (holdsSingleType) {
            for (StorageItem storageItem : storage.getItems()) {
                if (storageItem.itemId != itemId && storageItem.getQuantity() > 0) {
                    return;
                }
            }
        }

        // Check for individual maximum quantity.
        if (maximumIndividualQuantity.isPresent() && quantity > maximumIndividualQuantity.get()) {
            quantity = maximumIndividualQuantity.get();
        }

        // Maximum total quantity.
        Optional<Integer> maximumTotalQuantity = getMaximumTotalQuantity();
        if (maximumTotalQuantity.isPresent()) {
            int newTotalQuantity = 0;
            for (StorageItem storageItem : storage.getItems()) {
                if (storageItem.itemId == itemId) continue;
                newTotalQuantity += storageItem.getQuantity();
            }
            newTotalQuantity += quantity; //Add outside the loop in case the item is not currently stored

            if (newTotalQuantity > maximumTotalQuantity.get()) {
                quantity -= newTotalQuantity - maximumTotalQuantity.get();
            }
        }

        // Maximum total combo quantity.
        if (maximumTotalComboQuantity.isPresent() && maximumTotalComboQuantity.get().itemIds.stream().anyMatch(id -> id == itemId)) {
            int comboQuantity = 0;

            for (int comboItemId : maximumTotalComboQuantity.get().itemIds) {
                if (comboItemId == itemId) {
                    comboQuantity += quantity;
                } else {
                    Optional<StorageItem> comboItem = getItem(comboItemId);
                    if (comboItem.isPresent()) {
                        comboQuantity += comboItem.get().getQuantity();
                    }
                }
            }

            if (comboQuantity > maximumTotalComboQuantity.get().quantity) {
                quantity -= comboQuantity - maximumTotalComboQuantity.get().quantity;
            }
        }

        Optional<StorageItem> item = getItem(itemId);
        if (quantity == 0) {
            storage.remove(itemId);
        } else if (item.isPresent()) {
            item.get().setQuantity(quantity);
        } else {
            storage.put(new StorageItem(itemId, quantity));
        }

        save();
    }

    public void fillFromInventory() {
        for (StorageItem itemDifference : provider.store.getInventoryItemsDifference().getItems()) {
            if (isStorageItem(itemDifference) && itemDifference.getQuantity() < 0) {
                add(itemDifference.itemId, Math.abs(itemDifference.getQuantity()));
            }
        }
    }

    private boolean isStorageItem(StorageItem item) {
        for (StorageItem storageItem : storableItems) {
            if (storageItem.itemId == item.itemId) {
                return true;
            }
        }

        return false;
    }

    public void emptyToInventory() {
        for (StorageItem itemDifference : provider.store.getInventoryItemsDifference().getItems()) {
            storage.getItem(itemDifference.itemId).ifPresent(item -> item.decreaseQuantity(itemDifference.getQuantity()));
        }
    }

    public void emptyToInventoryWithoutItemContainerChanged() {
        int inventorySpaceFree = provider.store.getInventoryEmptySlots();

        for (StorageItem storageItem : storage.getItems()) {
            if (storageItem.getQuantity() > 0) {
                int toRemove = Math.min(storageItem.getQuantity(), inventorySpaceFree);
                remove(storageItem.itemId, toRemove);
                inventorySpaceFree -= toRemove;
            }
        }
    }

    public void emptyToBank() {
        for (StorageItem itemDifference : provider.store.getBankItemsDifference().getItems()) {
            storage.getItem(itemDifference.itemId).ifPresent(item -> item.decreaseQuantity(itemDifference.getQuantity()));
        }
    }

    public void fillFromBank() {
        for (StorageItem itemDifference : provider.store.getBankItemsDifference().getItems()) {
            Optional<StorageItem> item = storage.getItem(itemDifference.itemId);
            if (item.isPresent()) {
                item.get().increaseQuantity(Math.abs(itemDifference.getQuantity()));
            } else {
                storage.put(new StorageItem(itemDifference.itemId, Math.abs(itemDifference.getQuantity())));
            }
        }
    }

    public void updateFromItemContainer(CustomItemContainerChanged itemContainer) {
        storage = new StorageItems(itemContainer);
        save();
    }

    public int getCharges() {
        int charges = 0;

        for (StorageItem item : storage.getItems()) {
            charges += item.getQuantity();
        }

        return charges;
    }

    public StorageItems getStorage() {
        return storage;
    }

    public void loadStorage() {
        storage = new StorageItems();

        // Load storage from config.
        try {
            String jsonString = provider.configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, storageConfigKey);
            JsonArray jsonStorage = (JsonArray) (new JsonParser()).parse(jsonString);

            for (JsonElement jsonStorageItem : jsonStorage) {
                StorageItem loadedItem = new StorageItem(
                    jsonStorageItem.getAsJsonObject().get("itemId").getAsInt(),
                    jsonStorageItem.getAsJsonObject().get("quantity").getAsInt()
                );

                put(loadedItem.itemId, loadedItem.getQuantity());
            }
        } catch (Exception ignored) {}
    }

    private void save() {
        JsonArray jsonStorage = new JsonArray();

        for (StorageItem storageItem : storage.getItems()) {
            JsonObject jsonItem = new JsonObject();
            jsonItem.addProperty("itemId", storageItem.itemId);
            jsonItem.addProperty("quantity", storageItem.getQuantity());
            jsonStorage.add(jsonItem);
        }

        provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, storageConfigKey, provider.gson.toJson(jsonStorage));
    }

    public Optional<StorageItem> getItem(int itemId) {
        return storage.getItem(itemId);
    }

    public boolean isEmpty() {
        for (StorageItem storageItem : storage.getItems()) {
            if (storageItem.getQuantity() > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isFull() {
        if (maximumTotalQuantity.isPresent()) {
            int quantity = 0;

            for (StorageItem storageItem : storage.getItems()) {
                quantity += storageItem.getQuantity();
            }

            return quantity == maximumTotalQuantity.get();
        }

        return false;
    }

    public Optional<Integer> getMaximumTotalQuantity() {
        // Maximum storage from trigger item.
        for (TriggerItem item : chargedItem.items) {
            if (chargedItem.itemId == item.itemId && item.maxCharges.isPresent()) {
                return item.maxCharges;
            }
        }

        // Maximum storage with specific item equipped.
        if (maximumTotalQuantityWithItemEquipped.isPresent() && maximumTotalQuantityWithItemEquippedItems.isPresent() && provider.store.equipmentContainsItem(maximumTotalQuantityWithItemEquippedItems.get())) {
            return maximumTotalQuantityWithItemEquipped;
        }

        // Maximum storage.
        if (maximumTotalQuantity.isPresent()) {
            return maximumTotalQuantity;
        }

        return Optional.empty();
    }

    public Optional<StorageItem> getStorageItemFromName(String name, int quantity) {
        for (StorableItem storableItem : storableItems) {
            // Based on checkName.
            if (storableItem.checkName.isPresent()) {
                for (String checkName :storableItem.checkName.get()) {
                    if (
                        name.equalsIgnoreCase(checkName) ||
                        name.toLowerCase().contains(checkName.toLowerCase()) ||
                        name.contains(provider.itemManager.getItemComposition(storableItem.itemId).getName())
                    ) {
                        return Optional.of(new StorageItem(storableItem.itemId, quantity));
                    }
                }
            }
        }

        return Optional.empty();
    }

    public StorableItem[] getStorableItems() {
        return storableItems;
    }

    public boolean isStorableItemInInventory() {
        for (StorageItem inventoryItem : provider.store.inventory.getItems()) {
            for (StorableItem storableItem : storableItems) {
                if (inventoryItem.itemId == storableItem.itemId) {
                    return true;
                }
            }
        }

        return false;
    }

    public Storage setMaximumComboQuantity(List<Integer> itemIds, int quantity) {
        this.maximumTotalComboQuantity = Optional.of(new MaximumComboQuantity(itemIds, quantity));
        return this;
    }

    public boolean hasItem(int itemId) {
        return storage.hasItem(itemId);
    }
}
