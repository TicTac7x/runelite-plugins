package tictac7x.storage.panel;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import tictac7x.storage.storage.StorageItem;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Component;
import java.util.List;

public class PanelItems {
    private final ClientThread client_thread;
    private final ItemManager items;
    private JPanel panel;

    public PanelItems(final ClientThread client_thread, final ItemManager items, final List<StorageItem> list_items) {
        this.client_thread = client_thread;
        this.items = items;

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        addItemsToPanel(list_items);
    }

    public Component get() {
        return panel;
    }

    public void update(final List<StorageItem> list_items) {
        panel.removeAll();

        SwingUtilities.invokeLater(() -> {
            addItemsToPanel(list_items);
        });

        panel.revalidate();
        panel.repaint();
    }

    private void addItemsToPanel(final List<StorageItem> list_items) {
        for (final StorageItem item : list_items) {
            final PanelItem panel_item = new PanelItem(item.id, item.quantity, client_thread, items);
            panel.add(panel_item.get());
        }
    }
}
