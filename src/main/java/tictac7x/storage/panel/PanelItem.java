package tictac7x.storage.panel;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import tictac7x.storage.storage.StorageItem;
import tictac7x.storage.utils.ItemContainerId;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class PanelItem extends JLayeredPane {
    private static Optional<BufferedImage> estateIcon = Optional.empty();
    private static final int ITEM_WIDTH = PluginPanel.PANEL_WIDTH - 20;
    private static final int ITEM_HEIGHT = 34;
    private static final int ICON_SIZE = 15;

    public PanelItem(final StorageItem item, final int itemContainerId, final ItemManager itemManager) {
        final boolean hasIcon = itemContainerId != ItemContainerId.INVENTORY;
        final int itemLabelWidth = PluginPanel.PANEL_WIDTH + (hasIcon ? -40 : -20);

        setLayout(null);
        setPreferredSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
        setMinimumSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
        setMaximumSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));

        // Item label with icon and name
        final JLabel itemLabel = new JLabel();
        itemLabel.setPreferredSize(new Dimension(itemLabelWidth, ITEM_HEIGHT));
        itemLabel.setIcon(new ImageIcon(itemManager.getImage(item.id, item.getQuantity(), true)));
        itemLabel.setText(item.name);
        itemLabel.setHorizontalAlignment(SwingConstants.LEFT);
        itemLabel.setBounds(0, 0, itemLabelWidth, ITEM_HEIGHT);
        add(itemLabel, JLayeredPane.DEFAULT_LAYER);

        if (itemContainerId == ItemContainerId.HOME) {
            final BufferedImage smallIcon = getEstateIcon();
            final JLabel iconOverlay = new JLabel(new ImageIcon(smallIcon));

            // Set the fixed icon size to 15x15
            iconOverlay.setBounds(
                PluginPanel.PANEL_WIDTH - 20 - ICON_SIZE,
                (ITEM_HEIGHT - ICON_SIZE) / 2,  // Vertically center the icon
                ICON_SIZE,
                ICON_SIZE
            );

            add(iconOverlay, JLayeredPane.PALETTE_LAYER);
        }
    }

    private BufferedImage getEstateIcon() {
        if (!estateIcon.isPresent()) {
            estateIcon = Optional.of(ImageUtil.loadImageResource(getClass(), "/estate.png"));
        }
        return estateIcon.get();
    }
}
