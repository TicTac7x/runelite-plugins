package tictac7x.storage.storage;

import com.google.gson.JsonObject;
import net.runelite.api.Item;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import tictac7x.storage.TicTac7xStorageConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    private final ItemManager itemManager;
    protected final ConfigManager configManager;

    public final String configKey;
    public final int itemContainerId;
    private final Map<Integer, StorageItem> storage = new LinkedHashMap<>();
    private int slotsUsed = 0;

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

            // Placeholder item.
            if (itemManager.getItemComposition(item.getId()).getPlaceholderTemplateId() != -1) continue;

            // Valid item.
            addItem(new StorageItem(item.getId(), item.getQuantity()));
        }

        configManager.setConfiguration(TicTac7xStorageConfig.group, configKey + TicTac7xStorageConfig.storage, getJsonString());
    }

    protected void addItem(final StorageItem item) {
        if (storage.containsKey(item.id)) {
            storage.get(item.id).increaseQuantity(item.getQuantity());
        } else {
            storage.put(item.id, item);
        }

        slotsUsed++;
    }

    public int getSlotsUsed() {
        return slotsUsed;
    }

    public List<StorageItem> getItems() {
        return new ArrayList<>(storage.values());
    }

    private String getJsonString() {
        final JsonObject jsonObject = new JsonObject();

        for (final StorageItem item : storage.values()) {
            jsonObject.addProperty(String.valueOf(item.id), item.getQuantity());
        }

        return jsonObject.toString();
    }
}
