package tictac7x.storage.panel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import tictac7x.storage.TicTac7xStorageConfig;
import tictac7x.storage.storage.StorageItem;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoragePanel extends PluginPanel {
    private final ClientThread clientThread;
    private final ItemManager itemManager;
    private final TicTac7xStorageConfig config;

    private List<StorageItem> list_items;
    private String search = "";

    private PanelItems panelItems;

    public StoragePanel(final ClientThread clientThread, final ItemManager itemManager, final TicTac7xStorageConfig config) {
        super(false);
        this.clientThread = clientThread;
        this.itemManager = itemManager;
        this.config = config;
        loadItemsFromConfig();

        // Panel theme.
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        // Panel components.
        final PanelSearch panelSearch = new PanelSearch((this::searchItems));
        add(panelSearch.get(), BorderLayout.NORTH);

        // Panel items.
        panelItems = new PanelItems(clientThread, itemManager, list_items);

        // Panel scroller.
        final JScrollPane scroller = new JScrollPane(panelItems);
        add(scroller, BorderLayout.CENTER);
    }

    private void loadItemsFromConfig() {
        try {
            list_items = new ArrayList<>();

            final JsonObject bank = (JsonObject) new JsonParser().parse(config.getBankStorage());

            for (final Map.Entry<String, JsonElement> item : bank.entrySet()) {
                list_items.add(new StorageItem(Integer.parseInt(item.getKey()), item.getValue().getAsInt()));
            }
        } catch (final Exception ignored) {}
    }

    public void searchItems(final String search) {
        this.search = search;

        // Show all items.
        if (search.length() == 0) {
            panelItems.update(list_items);
            return;
        }

        final String searchLowercase = search.toLowerCase();

        // Client thread is required to get item names from compositions.
        clientThread.invoke(() -> {
            final List<StorageItem> list_items_starts_with = new ArrayList<>();
            final List<StorageItem> list_items_contains = new ArrayList<>();

            // Filter items.
            for (final StorageItem item : list_items) {
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
        if (!event.getGroup().equals(TicTac7xStorageConfig.group) || !event.getKey().equals(TicTac7xStorageConfig.bank)) return;

        loadItemsFromConfig();
        searchItems(search);
    }
}