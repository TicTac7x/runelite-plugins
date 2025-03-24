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

        // Show all items.
        if (search.length() == 0) {
            panelItems.update(storage.getItems());
            return;
        }

        final String searchLowercase = search.toLowerCase();

        // Client thread is required to get item names from compositions.
        clientThread.invoke(() -> {
            final List<StorageItem> list_items_starts_with = new ArrayList<>();
            final List<StorageItem> list_items_contains = new ArrayList<>();

            // Filter items.
            for (final StorageItem item : storage.getItems()) {
                final String name = itemManager.getItemComposition(item.id).getName().toLowerCase();

                // Find items that start with the search first.
                if (name.startsWith(searchLowercase)) {
                    list_items_starts_with.add(item);

                // Find items that match somewhere else too.
                } else if (name.contains(searchLowercase)) {
                    list_items_contains.add(item);
                }
            }

            list_items_starts_with.addAll(list_items_contains);
            panelItems.update(list_items_starts_with);
        });
    }

    public void onConfigChanged(final ConfigChanged event) {
        if (!event.getGroup().equals(TicTac7xStorageConfig.group) || !event.getKey().equals(TicTac7xStorageConfig.bank + TicTac7xStorageConfig.storage)) return;

        loadStorageFromConfig();
        searchItems(search);
    }
}