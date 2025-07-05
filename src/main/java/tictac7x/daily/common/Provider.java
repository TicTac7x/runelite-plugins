package tictac7x.daily.common;

import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.daily.TicTac7xDailyTasksConfig;
import tictac7x.daily.TicTac7xDailyTasksPlugin;

public class Provider {
    public final Client client;
    public final ItemManager itemManager;
    public final InfoBoxManager infoBoxManager;
    public final ConfigManager configManager;
    public final TicTac7xDailyTasksConfig config;
    public final TicTac7xDailyTasksPlugin plugin;

    public Provider(
        final Client client,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ConfigManager configManager,
        final TicTac7xDailyTasksConfig config,
        final TicTac7xDailyTasksPlugin plugin
    ) {
        this.client = client;
        this.itemManager = itemManager;
        this.infoBoxManager = infoBoxManager;
        this.configManager = configManager;
        this.config = config;
        this.plugin = plugin;
    }
}
