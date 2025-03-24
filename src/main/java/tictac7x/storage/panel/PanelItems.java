package tictac7x.storage.panel;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;

import javax.swing.*;
import java.util.List;

public class PanelItems extends JPanel {
    private final ItemManager itemManager;

    public PanelItems(final ItemManager itemManager) {
        this.itemManager = itemManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void update(final List<Storage> storages, final String search) {
        removeAll();

        for (final Storage storage : storages) {
            for (final StorageItem item : storage.getItems(search, "", false, true)) {
                add(new PanelItem(item, storage.itemContainerId, itemManager));
            }
        }

        revalidate();
        repaint();
    }
}
