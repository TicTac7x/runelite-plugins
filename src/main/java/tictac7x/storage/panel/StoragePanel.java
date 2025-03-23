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

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoragePanel extends PluginPanel {
    private final ClientThread client_thread;
    private final ItemManager items;
    private final TicTac7xStorageConfig config;
    private final JsonParser parser = new JsonParser();

    private List<DataItem> list_items;
    private String search = "";

    private Search input_search;
    private JScrollPane panel_scoller;
    private PanelItems panel_items;

    public StoragePanel(final ClientThread client_thread, final ItemManager items, final TicTac7xStorageConfig config) {
        super(false);
        this.client_thread = client_thread;
        this.items = items;
        this.config = config;
        loadItemsFromConfig();

        // Panel theme.
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        // Panel components.
        input_search = new Search((this::searchItems));
        add(input_search.get(), BorderLayout.NORTH);

        // Panel items.
        panel_items = new PanelItems(client_thread, items, list_items);

        // Panel scroller.
        panel_scoller = new JScrollPane(panel_items.get());
        add(panel_scoller, BorderLayout.CENTER);
    }

    private void loadItemsFromConfig() {
        list_items = new ArrayList<>();

        final JsonObject bank = (JsonObject) parser.parse(config.getBank());

        for (final Map.Entry<String, JsonElement> item : bank.entrySet()) {
            list_items.add(new DataItem(Integer.parseInt(item.getKey()), item.getValue().getAsInt()));
        }
    }

    public void searchItems(final String search) {
        this.search = search;

        // Show all items.
        if (search.length() == 0) {
            panel_items.update(list_items);
            return;
        }

        final String searchLowercase = search.toLowerCase();

        // Client thread is required to get item names from compositions.
        client_thread.invoke(() -> {
            final List<DataItem> list_items_starts_with = new ArrayList<>();
            final List<DataItem> list_items_contains = new ArrayList<>();

            // Filter items.
            for (final DataItem item : list_items) {
                final String name = items.getItemComposition(item.id).getName().toLowerCase();

                // Find items that start with the search first.
                if (name.startsWith(searchLowercase)) {
                    list_items_starts_with.add(item);

                // Find items that match somewhere else too.
                } else if (name.contains(searchLowercase)) {
                    list_items_contains.add(item);
                }
            }

            list_items_starts_with.addAll(list_items_contains);
            panel_items.update(list_items_starts_with);
        });
    }

    public void onConfigChanged(final ConfigChanged event) {
        if (!event.getGroup().equals(TicTac7xStorageConfig.group) || !event.getKey().equals(TicTac7xStorageConfig.bank)) return;

        loadItemsFromConfig();
        searchItems(search);
    }
}