package tictac7x.storage.panel;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import tictac7x.storage.TicTac7xStorageConfig;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageFromConfig;
import tictac7x.storage.storage.StorageItem;
import tictac7x.storage.utils.ItemContainerId;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

public class StoragePanel extends PluginPanel {
    private final ClientThread clientThread;
    private final ItemManager itemManager;
    private final ConfigManager configManager;
    private final TicTac7xStorageConfig config;

    private Storage storage;
    private String search = "";

    private PanelItems panelItems;

    public StoragePanel(final ClientThread clientThread, final ItemManager itemManager, final ConfigManager configManager, final TicTac7xStorageConfig config) {
        super(false);
        this.clientThread = clientThread;
        this.itemManager = itemManager;
        this.configManager = configManager;
        this.config = config;

        // Panel theme.
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        // Panel components.
        final PanelSearch panelSearch = new PanelSearch((this::searchItems));
        add(panelSearch.get(), BorderLayout.NORTH);

        // Panel items.
        panelItems = new PanelItems(clientThread, itemManager, new ArrayList<>());

        // Panel scroller.
        final JScrollPane scroller = new JScrollPane(panelItems);
        add(scroller, BorderLayout.CENTER);

        clientThread.invoke(() -> {
            loadStorageFromConfig();
            searchItems("");
        });
    }

    private void loadStorageFromConfig() {
        storage = new StorageFromConfig(TicTac7xStorageConfig.bank, ItemContainerId.BANK, itemManager, configManager);
    }

    public void searchItems(final String search) {
        this.search = search;

        clientThread.invoke(() -> {
            panelItems.update(storage.getItems(search.toLowerCase(), "", false, true));
        });
    }

    public void onConfigChanged(final ConfigChanged event) {
        if (event.getKey().equals(TicTac7xStorageConfig.bank + TicTac7xStorageConfig.storage)) {
            clientThread.invoke(() -> {
                loadStorageFromConfig();
                searchItems(search);
            });
        }
    }
}