package tictac7x.charges.item.storage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.events.CustomItemContainerChanged;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ChargeId;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.utils.MaximumComboQuantity;

import java.util.Arrays;
import java.util.Optional;

public class Storage {
    private final ChargedItemWithStorage chargedItem;
    private final String storageConfigKey;
    private final Provider provider;

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


    public Storage(final ChargedItemWithStorage chargedItem, final String configKey, final Provider provider) {
        this.chargedItem = chargedItem;
        this.storageConfigKey = configKey + "_storage";
        this.provider = provider;
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
        storage.clear();
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

    public void add(final StorageItem item) {
        add(item.getId(), item.getQuantity());
    }

    public void add(final Optional<StorageItem> item) {
        if (!item.isPresent()) return;
        add(item.get().getId(), item.get().getQuantity());
    }

    public void put(final Optional<StorageItem> item) {
        if (!item.isPresent()) return;
        put(item.get().getId(), item.get().getQuantity());
    }

    public void clearAndPut(final Optional<StorageItem> item) {
        if (!item.isPresent()) return;
        clearAndPut(item.get().getId(), item.get().getQuantity());
    }

    public void clearAndPut(final int itemId, final int quantity) {
        clear();
        put(itemId, quantity);
    }

    public void remove(final Optional<StorageItem> item) {
        if (!item.isPresent()) return;
        remove(item.get().getId(), item.get().getQuantity());
    }

    public void remove(final int itemId, final int quantity) {
        final Optional<StorageItem> item = getItem(itemId);

        // Don't decrease quantity of unlimited storage item.
        if (item.isPresent() && item.get().getQuantity() == ChargeId.UNLIMITED) {
            return;
        }

        put(itemId, (item.isPresent() ? Math.max(0, item.get().getQuantity() - quantity) : 0));
    }

    public void removeAndPrioritizeInventory(final int itemId, final int quantity) {
        this.remove(itemId, Math.max(quantity - provider.store.getInventoryItemQuantity(itemId), 0));
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

        boolean storableCheck = false;
        for (final StorableItem item : storableItems) {
            if (item.getId() == itemId) {
                storableCheck = true;
                break;
            }
        }
        if (!storableCheck) return;

        // Storage holds only one unique item at once check.
        if (holdsSingleType) {
            for (final StorageItem storageItem : storage.getItems()) {
                if (storageItem.getId() != itemId && storageItem.getQuantity() > 0) {
                    return;
                }
            }
        }

        // Check for individual maximum quantity.
        if (maximumIndividualQuantity.isPresent() && quantity > maximumIndividualQuantity.get()) {
            quantity = maximumIndividualQuantity.get();
        }

        // Maximum total quantity.
        final Optional<Integer> maximumTotalQuantity = getMaximumTotalQuantity();
        if (maximumTotalQuantity.isPresent()) {
            int newTotalQuantity = 0;
            for (final StorageItem storageItem : storage.getItems()) {
                if (storageItem.getId() == itemId) continue;
                newTotalQuantity += storageItem.getQuantity();
            }
            newTotalQuantity += quantity; //Add outside the loop in case the item is not currently stored

            if (newTotalQuantity > maximumTotalQuantity.get()) {
                quantity -= newTotalQuantity - maximumTotalQuantity.get();
            }
        }

        // Maximum total combo quantity.
        if (maximumTotalComboQuantity.isPresent() && Arrays.stream(maximumTotalComboQuantity.get().itemIds).filter(id -> id == itemId).findAny().isPresent()) {
            int comboQuantity = 0;

            for (final int comboItemId : maximumTotalComboQuantity.get().itemIds) {
                if (comboItemId == itemId) {
                    comboQuantity += quantity;
                } else {
                    final Optional<StorageItem> comboItem = getItem(comboItemId);
                    if (comboItem.isPresent()) {
                        comboQuantity += comboItem.get().getQuantity();
                    }
                }
            }

            if (comboQuantity > maximumTotalComboQuantity.get().quantity) {
                quantity -= comboQuantity - maximumTotalComboQuantity.get().quantity;
            }
        }

        final Optional<StorageItem> item = getItem(itemId);
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
        for (final StorageItem itemDifference : provider.store.getInventoryItemsDifference().getItems()) {
            if (isStorageItem(itemDifference) && itemDifference.getQuantity() < 0) {
                add(itemDifference.getId(), Math.abs(itemDifference.getQuantity()));
            }
        }
    }

    private boolean isStorageItem(final StorageItem item) {
        for (final StorageItem storageItem : storableItems) {
            if (storageItem.getId() == item.getId()) {
                return true;
            }
        }

        return false;
    }

    public void emptyToInventory() {
        for (final StorageItem itemDifference : provider.store.getInventoryItemsDifference().getItems()) {
            storage.getItem(itemDifference.getId()).ifPresent(item -> item.decreaseQuantity(itemDifference.getQuantity()));
        }
    }

    public void emptyToInventoryWithoutItemContainerChanged() {
        int inventorySpaceFree = provider.store.getInventoryEmptySlots();

        for (final StorageItem storageItem : storage.getItems()) {
            if (storageItem.getQuantity() > 0) {
                final int toRemove = Math.min(storageItem.getQuantity(), inventorySpaceFree);
                remove(storageItem.getId(), toRemove);
                inventorySpaceFree -= toRemove;
            }
        }
    }

    public void emptyToBank() {
        for (final StorageItem itemDifference : provider.store.getBankItemsDifference().getItems()) {
            storage.getItem(itemDifference.getId()).ifPresent(item -> item.decreaseQuantity(itemDifference.getQuantity()));
        }
    }

    public void updateFromItemContainer(final CustomItemContainerChanged itemContainer) {
        storage = new StorageItems(itemContainer);
        save();
    }

    public int getCharges() {
        int charges = 0;

        for (final StorageItem item : storage.getItems()) {
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
            final String jsonString = provider.configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, storageConfigKey);
            final JsonArray jsonStorage = (JsonArray) (new JsonParser()).parse(jsonString);

            for (final JsonElement jsonStorageItem : jsonStorage) {
                final StorageItem loadedItem = new StorageItem(
                    jsonStorageItem.getAsJsonObject().get("itemId").getAsInt(),
                    jsonStorageItem.getAsJsonObject().get("quantity").getAsInt()
                );

                put(loadedItem.getId(), loadedItem.getQuantity());
            }
        } catch (final Exception ignored) {}
    }

    private void save() {
        final JsonArray jsonStorage = new JsonArray();

        for (final StorageItem storageItem : storage.getItems()) {
            final JsonObject jsonItem = new JsonObject();
            jsonItem.addProperty("itemId", storageItem.getId());
            jsonItem.addProperty("quantity", storageItem.getQuantity());
            jsonStorage.add(jsonItem);
        }

        provider.configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, storageConfigKey, provider.gson.toJson(jsonStorage));
    }

    private Optional<StorageItem> getItem(final int itemId) {
        return storage.getItem(itemId);
    }

    public boolean isEmpty() {
        for (final StorageItem storageItem : storage.getItems()) {
            if (storageItem.getQuantity() > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isFull() {
        if (maximumTotalQuantity.isPresent()) {
            int quantity = 0;

            for (final StorageItem storageItem : storage.getItems()) {
                quantity += storageItem.getQuantity();
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
        if (maximumTotalQuantityWithItemEquipped.isPresent() && maximumTotalQuantityWithItemEquippedItems.isPresent() && provider.store.equipmentContainsItem(maximumTotalQuantityWithItemEquippedItems.get())) {
            return maximumTotalQuantityWithItemEquipped;
        }

        // Maximum storage.
        if (maximumTotalQuantity.isPresent()) {
            return maximumTotalQuantity;
        }

        return Optional.empty();
    }

    public final Optional<StorageItem> getStorageItemFromName(final String name, final int quantity) {
        for (final StorableItem storableItem : storableItems) {
            // Based on checkName.
            if (storableItem.checkName.isPresent()) {
                for (final String checkName :storableItem.checkName.get()) {
                    if (
                        name.equalsIgnoreCase(checkName) ||
                        name.toLowerCase().contains(checkName.toLowerCase()) ||
                        name.contains(provider.itemManager.getItemComposition(storableItem.getId()).getName())
                    ) {
                        return Optional.of(new StorageItem(storableItem.getId(), quantity));
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
            if (storableItem.getId() == storageItem.getId()) {
                if (storableItem.order.isPresent()) {
                    return storableItem.order.get();
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    public String getStorageItemName(final StorageItem storageItem) {
        for (final StorableItem storableItem : storableItems) {
            if (storableItem.getId() == storageItem.getId()) {
                if (storableItem.displayName.isPresent()) {
                    return storableItem.displayName.get();
                }
            }
        }

        return provider.itemManager.getItemComposition(storageItem.getId()).getName();
    }

    public boolean isStorableItemInInventory() {
        for (final StorageItem inventoryItem : provider.store.inventory.getItems()) {
            for (final StorableItem storableItem : storableItems) {
                if (inventoryItem.getId() == storableItem.getId()) {
                    return true;
                }
            }
        }

        return false;
    }

    public Storage setMaximumComboQuantity(final int[] itemIds, final int quantity) {
        this.maximumTotalComboQuantity = Optional.of(new MaximumComboQuantity(itemIds, quantity));
        return this;
    }
}
