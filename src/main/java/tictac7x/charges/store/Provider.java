package tictac7x.charges.store;

import com.google.gson.*;
import net.runelite.api.*;
import net.runelite.client.*;
import net.runelite.client.callback.*;
import net.runelite.client.chat.*;
import net.runelite.client.config.*;
import net.runelite.client.game.*;
import net.runelite.client.plugins.*;
import net.runelite.client.ui.overlay.infobox.*;
import net.runelite.client.ui.overlay.tooltip.*;
import tictac7x.charges.*;

public class Provider {
    public Client client;
    public ClientThread clientThread;
    public PluginManager pluginManager;
    public MyConfigManager configManager;
    public MyItemManager itemManager;
    public InfoBoxManager infoBoxManager;
    public ChatMessageManager chatMessageManager;
    public TooltipManager tooltipManager;
    public Notifier notifier;
    public TicTac7xChargesImprovedPlugin plugin;
    public MyConfig config;
    public Store store;
    public Gson gson;

    public Provider(
        Client client,
        ClientThread clientThread,
        PluginManager pluginManager,
        MyConfigManager configManager,
        MyItemManager itemManager,
        InfoBoxManager infoBoxManager,
        ChatMessageManager chatMessageManager,
        TooltipManager tooltipManager,
        Notifier notifier,
        TicTac7xChargesImprovedPlugin plugin,
        MyConfig config,
        Store store,
        Gson gson
    ) {
        this.client = client;
        this.clientThread = clientThread;
        this.pluginManager = pluginManager;
        this.configManager = configManager;
        this.itemManager = itemManager;
        this.infoBoxManager = infoBoxManager;
        this.chatMessageManager = chatMessageManager;
        this.tooltipManager = tooltipManager;
        this.notifier = notifier;
        this.plugin = plugin;
        this.config = config;
        this.store = store;
        this.gson = gson;
    }
}
