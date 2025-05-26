package tictac7x.storage.storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.runelite.client.game.ItemManager;
import tictac7x.storage.TicTac7xStorageConfig;
import tictac7x.storage.utils.Provider;

import java.util.ArrayList;
import java.util.List;

public class ConfigStorage extends Storage {
    public final String configKey;
    public final Provider provider;

    public ConfigStorage(final String configKey, final int itemContainerId, final Provider provider) {
        super(itemContainerId);
        this.configKey = configKey;
        this.provider = provider;
    }

    public void loadFromConfig(final ItemManager itemManager) {
        final String storageJsonString = provider.configManager.getConfiguration(TicTac7xStorageConfig.group, configKey + TicTac7xStorageConfig.storage);

        try {
            final JsonObject jsonObject = (JsonObject) new JsonParser().parse(storageJsonString);

            provider.clientThread.invoke(() -> {
                final List<StorageItem> items = new ArrayList<>();

                for (final String itemKey : jsonObject.keySet()) {
                    final int itemId = Integer.parseInt(itemKey);
                    final int itemQuantity = jsonObject.get(itemKey).getAsInt();
                    final String itemName = itemManager.getItemComposition(itemId).getName();
                    items.add(new StorageItem(itemId, itemQuantity, itemName));
                }

                addItems(items);
            });
        } catch (final Exception ignored) {}
    }

    @Override
    public void addItems(final List<StorageItem> items) {
        super.addItems(items);
        updateConfig();
    }

    protected void updateConfig() {
        provider.configManager.setConfiguration(TicTac7xStorageConfig.group, configKey + TicTac7xStorageConfig.storage, getJsonString());
    }

    private String getJsonString() {
        final JsonObject jsonObject = new JsonObject();

        for (final StorageItem item : storage.values()) {
            jsonObject.addProperty(String.valueOf(item.id), item.getQuantity());
        }

        return jsonObject.toString();
    }
}
