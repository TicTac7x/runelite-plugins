package tictac7x.storage.storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import tictac7x.storage.TicTac7xStorageConfig;

public class StorageFromConfig extends Storage {
    public StorageFromConfig(String configKey, int itemContainerId, ItemManager itemManager, ConfigManager configManager) {
        super(configKey, itemContainerId, itemManager, configManager);
        loadStorageFromConfig();
    }

    private void loadStorageFromConfig() {
        final String storageJsonString = configManager.getConfiguration(TicTac7xStorageConfig.group, configKey + TicTac7xStorageConfig.storage);

        try {
            final JsonObject jsonObject = (JsonObject) new JsonParser().parse(storageJsonString);
            for (final String itemId : jsonObject.keySet()) {
                final int itemQuantity = jsonObject.get(itemId).getAsInt();
                addItem(new StorageItem(Integer.parseInt(itemId), itemQuantity));
            }

        } catch (final Exception ignored) {}
    }
}
