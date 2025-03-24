package tictac7x.storage.panel;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import tictac7x.storage.storage.Storage;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.List;

public class StoragePanel extends PluginPanel {
    private final ClientThread clientThread;

    private final PanelItems panelItems;
    private final List<Storage> storages;
    private String search = "";

    public StoragePanel(final List<Storage> storages, final ClientThread clientThread, final ItemManager itemManager) {
        super(false);
        this.clientThread = clientThread;
        this.storages = storages;

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

        for (final Storage storage : storages) {
            storage.addOnChangeListener(this::storagesChanged);
        }
    }

    private void storagesChanged() {
        searchItems(search);
    }

    public void searchItems(final String search) {
        this.search = search;

        clientThread.invoke(() -> {
            panelItems.update(storages, search);
        });
    }
}