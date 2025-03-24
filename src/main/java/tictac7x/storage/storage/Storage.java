package tictac7x.storage.storage;

import com.google.gson.JsonObject;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import tictac7x.storage.TicTac7xStorageConfig;

import java.util.*;

public class Storage {
    protected final ItemManager itemManager;
    protected final ConfigManager configManager;

    public final String configKey;
    public final int itemContainerId;
    private final Map<Integer, StorageItem> storage = new LinkedHashMap<>();
    private int slotsUsed = 0;
    private List<Runnable> listeners = new ArrayList<>();

    public Storage(final String configKey, final int itemContainerId, final ItemManager itemManager, final ConfigManager configManager) {
        this.configKey = configKey;
        this.itemContainerId = itemContainerId;
        this.itemManager = itemManager;
        this.configManager = configManager;
    }

    public void onItemContainerChanged(final ItemContainerChanged event) {
        if (event.getContainerId() != itemContainerId) return;

        storage.clear();
        slotsUsed = 0;

        for (final Item item : event.getItemContainer().getItems()) {
            // Missing item.
            if (item.getId() == -1) continue;

            final ItemComposition itemComposition = itemManager.getItemComposition(item.getId());

            // Valid item.
            addItem(new StorageItem(
                    itemComposition.getPlaceholderTemplateId() != -1 ? itemComposition.getPlaceholderId() : item.getId(),
                itemComposition.getPlaceholderTemplateId() != -1 ? 0 : item.getQuantity(),
                itemComposition.getName()
            ), false);
        }

        updateConfig();
        notifyListeners();
    }

    protected void addItem(final StorageItem item, final boolean updateConfig) {
        if (storage.containsKey(item.id)) {
            storage.get(item.id).increaseQuantity(item.getQuantity());
        } else {
            storage.put(item.id, item);
        }

        slotsUsed++;

        if (updateConfig) updateConfig();
    }

    public int getSlotsUsed() {
        return slotsUsed;
    }

    public List<StorageItem> getItems() {
        return new ArrayList<>(storage.values());
    }

    public List<StorageItem> getItems(final String visibleString, final String hiddenString, final boolean caseSensitive, final boolean prioritizeStartsWith) {
        final String[] visibleList = visibleString.split(",");
        final String[] hiddenList = hiddenString.split(",");

        final List<StorageItem> prioritizedItems = new ArrayList<>();
        final List<StorageItem> regularItems = new ArrayList<>();

        for (final StorageItem item : storage.values()) {
            if (!isItemHidden(item, hiddenList, caseSensitive) && isItemVisible(item, visibleList, caseSensitive)) {
                if (prioritizeStartsWith && itemStartsWith(item, visibleList, caseSensitive)) {
                    prioritizedItems.add(item);
                } else {
                    regularItems.add(item);
                }
            }
        }

        if (prioritizeStartsWith) {
            prioritizedItems.addAll(regularItems);
            return prioritizedItems;
        } else {
            regularItems.addAll(prioritizedItems);
            return regularItems;
        }
    }

    private boolean isItemVisible(final StorageItem item, final String[] visibleList, final boolean caseSensitive) {
        if (visibleList.length == 0 || visibleList.length == 1 && visibleList[0].isEmpty()) return true;

        for (final String visibleString : visibleList) {
            if (caseSensitive ?
                item.name.contains(visibleString) :
                item.name.toLowerCase().contains(visibleString.toLowerCase())
            ) {
                return true;
            }
        }

        return false;
    }

    private boolean itemStartsWith(final StorageItem item, final String[] visibleList, final boolean caseSensitive) {
        for (final String visibleString : visibleList) {
            if (caseSensitive ?
                item.name.startsWith(visibleString) :
                item.name.toLowerCase().startsWith(visibleString.toLowerCase())
            ) {
                return true;
            }
        }

        return false;
    }

    private boolean isItemHidden(final StorageItem item, final String[] hiddenList, final boolean caseSensitive) {
        if (hiddenList.length == 0 || hiddenList.length == 1 && hiddenList[0].isEmpty()) return false;

        for (final String hiddenString : hiddenList) {
            if (caseSensitive ?
                item.name.contains(hiddenString) :
                item.name.toLowerCase().contains(hiddenString.toLowerCase())
            ) {
                return true;
            }
        }

        return false;
    }


    private String getJsonString() {
        final JsonObject jsonObject = new JsonObject();

        for (final StorageItem item : storage.values()) {
            jsonObject.addProperty(String.valueOf(item.id), item.getQuantity());
        }

        return jsonObject.toString();
    }

    protected void updateConfig() {
        configManager.setConfiguration(TicTac7xStorageConfig.group, configKey + TicTac7xStorageConfig.storage, getJsonString());
    }

    public void onChange(final Runnable listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (final Runnable listener : listeners) {
            listener.run();
        }
    }

    public Optional<StorageItem> getItem(final int itemId) {
        return Optional.ofNullable(storage.get(itemId));
    }
}
