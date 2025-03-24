package tictac7x.storage.panel;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import tictac7x.storage.TicTac7xStorageConfig;
import tictac7x.storage.storage.Storage;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;

public class StoragePanel extends PluginPanel {
    private final ClientThread clientThread;

    private final Storage storage;
    private String search = "";

    private PanelItems panelItems;

    public StoragePanel(final Storage storage, final ClientThread clientThread, final ItemManager itemManager) {
        super(false);
        this.clientThread = clientThread;
        this.storage = storage;

        // Panel theme.
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        // Panel components.
        final PanelSearch panelSearch = new PanelSearch((this::searchItems));
        add(panelSearch.get(), BorderLayout.NORTH);

        // Panel items.
        panelItems = new PanelItems(itemManager);

        // Panel scroller.
        final JScrollPane scroller = new JScrollPane(panelItems);
        add(scroller, BorderLayout.CENTER);

        storage.addOnChangeListener(this::bankStorageChanged);
    }

    private void bankStorageChanged() {
        searchItems(search);
    }

    public void searchItems(final String search) {
        this.search = search;

        clientThread.invoke(() -> {
            panelItems.update(storage.getItems(search.toLowerCase(), "", false, true));
        });
    }
}