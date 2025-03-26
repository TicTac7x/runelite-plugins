package tictac7x.storage.panel;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PanelItems extends JPanel {
    private final ItemManager itemManager;
    private final List<PanelItem> panelItems = new ArrayList<>();

    public PanelItems(final ItemManager itemManager) {
        this.itemManager = itemManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    public void update(final List<Storage> storages, final String search) {
        int itemIndex = 0;

        for (final Storage storage : storages) {
            for (final StorageItem item : storage.getItems(search, "", false, true)) {
                // New item.
                if (itemIndex + 1 > panelItems.size()) {
                    final PanelItem panelItem = new PanelItem(item, storage.itemContainerId, itemManager);
                    panelItems.add(panelItem);
                    add(panelItem);
                } else {
                    final PanelItem panelItemOld = panelItems.get(itemIndex);
                    if (
                        item.id != panelItemOld.getItem().id ||
                        item.getQuantity() != panelItemOld.getItem().getQuantity() ||
                        storage.itemContainerId != panelItemOld.getItemContainerId()
                    ) {
                        remove(itemIndex);
                        add(new PanelItem(item, storage.itemContainerId, itemManager), itemIndex);
                    }
                }

                itemIndex++;
            }
        }

        while (itemIndex < panelItems.size()) {
            panelItems.remove(itemIndex);
            remove(itemIndex);
        }

        System.out.println(panelItems.size() + " " + itemIndex);

        revalidate();
        repaint();
    }
}