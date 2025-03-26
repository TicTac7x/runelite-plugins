package tictac7x.storage.panel;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;

import javax.swing.*;
import java.util.*;

public class PanelItems extends JPanel {
    private final ItemManager itemManager;
    private final Map<String, PanelItem> itemPanels = new LinkedHashMap<>();

    public PanelItems(final ItemManager itemManager) {
        this.itemManager = itemManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(7, 7, 0, -7));
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
    }

    public void update(final List<Storage> storages, final String search) {
        final Set<String> visibleMultiKeys = new LinkedHashSet<>();

        for (final Storage storage : storages) {
            for (final StorageItem item : storage.getItems(search, "", false, true)) {
                final String multiKey = storage.itemContainerId + "_" + item.id + "_" + item.getQuantity();
                visibleMultiKeys.add(multiKey);

                if (!itemPanels.containsKey(multiKey)) {
                    final PanelItem panelItem = new PanelItem(new StorageItem(item), storage.itemContainerId, itemManager);
                    itemPanels.put(multiKey, panelItem);
                }
            }
        }

        SwingUtilities.invokeLater(() -> {
            removeAll();

            for (final String multiKey : visibleMultiKeys) {
                add(itemPanels.get(multiKey));
            }

            revalidate();
            repaint();
        });
    }
}
