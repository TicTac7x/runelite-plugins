package tictac7x.storage.utils;

import net.runelite.api.Client;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import tictac7x.storage.TicTac7xStorageConfig;

public class Provider {
    public final ClientThread clientThread;
    public final Client client;
    public final ConfigManager configManager;
    public final ItemManager itemManager;
    public final TicTac7xStorageConfig config;

    public Provider(
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final TicTac7xStorageConfig config
    ) {
        this.client = client;
        this.clientThread = clientThread;
        this.configManager = configManager;
        this.itemManager = itemManager;
        this.config = config;
    }
}
