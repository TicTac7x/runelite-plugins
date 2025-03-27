package tictac7x.storage.storage;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import tictac7x.storage.TicTac7xStorageConfig;
import tictac7x.storage.utils.ItemContainerId;

import java.util.List;

public class BankStorage extends ConfigStorage {
    public BankStorage(ClientThread clientThread, ConfigManager configManager) {
        super(TicTac7xStorageConfig.bank, ItemContainerId.BANK, clientThread, configManager);
    }

    public void depositItems(final List<StorageItem> items) {
        for (final StorageItem item : items) {
            addItem(item);
        }

        notifyListeners();
        updateConfig();
    }
}
