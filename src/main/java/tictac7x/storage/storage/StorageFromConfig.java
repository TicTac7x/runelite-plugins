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

            for (final String itemKey : jsonObject.keySet()) {
                final int itemId = Integer.parseInt(itemKey);
                final int itemQuantity = jsonObject.get(itemKey).getAsInt();
                final String itemName = itemManager.getItemComposition(itemId).getName();

                addItem(new StorageItem(itemId, itemQuantity, itemName));
            }
        } catch (final Exception ignored) {}
    }
}
