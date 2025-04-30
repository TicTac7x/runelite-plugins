package tictac7x.charges.store;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;

public class Provider {
    public final Client client;
    public final ClientThread clientThread;
    public final ConfigManager configManager;
    public final ItemManager itemManager;
    public final InfoBoxManager infoBoxManager;
    public final ChatMessageManager chatMessageManager;
    public final Notifier notifier;
    public final TicTac7xChargesImprovedPlugin plugin;
    public final TicTac7xChargesImprovedConfig config;
    public final Store store;
    public final Gson gson;

    public Provider(
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ChatMessageManager chatMessageManager,
        final Notifier notifier,
        final TicTac7xChargesImprovedPlugin plugin,
        final TicTac7xChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        this.client = client;
        this.clientThread = clientThread;
        this.configManager = configManager;
        this.itemManager = itemManager;
        this.infoBoxManager = infoBoxManager;
        this.chatMessageManager = chatMessageManager;
        this.notifier = notifier;
        this.plugin = plugin;
        this.config = config;
        this.store = store;
        this.gson = gson;
    }
}
