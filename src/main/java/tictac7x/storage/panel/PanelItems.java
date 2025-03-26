package tictac7x.storage.panel;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;

import javax.swing.*;
import java.util.*;

public class PanelItems extends JPanel {
    private final ItemManager itemManager;
    private final Map<String, PanelItem> itemPanels = new HashMap<>();

    public PanelItems(final ItemManager itemManager) {
        this.itemManager = itemManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(7, 7, 0, -7));
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
    }

    public void update(final List<Storage> storages, final String search) {
        final Set<String> visibleMultiKeys = new HashSet<>();

        for (final Storage storage : storages) {
            for (final StorageItem item : storage.getItems(search, "", false, true)) {
                final String multiKey = storage.itemContainerId + "_" + item.id;
                visibleMultiKeys.add(multiKey);

                if (!itemPanels.containsKey(multiKey)) {
                    final PanelItem panelItem = new PanelItem(new StorageItem(item), storage.itemContainerId, itemManager);
                    itemPanels.put(multiKey, panelItem);
                    add(panelItem);
                } else if (itemPanels.get(multiKey).getItem().getQuantity() != item.getQuantity()) {
                    remove(itemPanels.get(multiKey));
                    itemPanels.put(multiKey, new PanelItem(new StorageItem(item), storage.itemContainerId, itemManager));
                    add(itemPanels.get(multiKey));
                }
            }
        }

        SwingUtilities.invokeLater(() -> {
            for (final PanelItem panelItem : itemPanels.values()) {
                final String multiKey = panelItem.itemContainerId + "_" + panelItem.getItem().id;
                panelItem.setVisible(visibleMultiKeys.contains(multiKey));
            }
            revalidate();
            repaint();
        });
    }
}
