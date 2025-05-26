package tictac7x.storage.panel;

import net.runelite.client.ui.ColorScheme;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;
import tictac7x.storage.utils.Provider;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PanelItems extends JPanel {
    private final Provider provider;
    private final Map<String, PanelItem> itemPanels = new LinkedHashMap<>();

    public PanelItems(final Provider provider) {
        this.provider = provider;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(7, 7, 0, -7));
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
    }

    public void update(final List<Storage> storages, final String search) {
        final Set<String> visibleMultiKeys = new LinkedHashSet<>();

        for (final Storage storage : storages) {
            for (final StorageItem item : storage.getItems(search, "", true)) {
                final String multiKey = storage.itemContainerId + "_" + item.id + "_" + item.getQuantity();
                visibleMultiKeys.add(multiKey);

                if (!itemPanels.containsKey(multiKey)) {
                    final PanelItem panelItem = new PanelItem(new StorageItem(item), storage.itemContainerId, provider);
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
