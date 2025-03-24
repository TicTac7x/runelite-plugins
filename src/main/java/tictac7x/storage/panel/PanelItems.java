package tictac7x.storage.panel;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import tictac7x.storage.storage.StorageItem;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.List;

public class PanelItems extends JPanel {
    private final ClientThread clientThread;
    private final ItemManager itemManager;

    public PanelItems(final ClientThread clientThread, final ItemManager itemManager, final List<StorageItem> items) {
        this.clientThread = clientThread;
        this.itemManager = itemManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        addItemsToPanel(items);
    }

    public void update(final List<StorageItem> list_items) {
        removeAll();

        SwingUtilities.invokeLater(() -> {
            addItemsToPanel(list_items);
        });

        revalidate();
        repaint();
    }

    private void addItemsToPanel(final List<StorageItem> list_items) {
        for (final StorageItem item : list_items) {
            final PanelItem panelItem = new PanelItem(item, clientThread, itemManager);
            add(panelItem);
        }
    }
}
