package tictac7x.storage.panel;

import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import tictac7x.storage.storage.StorageItem;
import tictac7x.storage.utils.ItemContainerId;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

import static tictac7x.storage.TicTac7xStoragePlugin.getCachedIcon;

public class PanelItem extends JLayeredPane {
    private final ItemManager itemManager;
    private StorageItem item;
    private int itemContainerId;

    private static Optional<BufferedImage> estateIcon = Optional.empty();
    private static final int ITEM_WIDTH = PluginPanel.PANEL_WIDTH + 10;
    private static final int ITEM_HEIGHT = 36;
    private static final int ICON_SIZE = 15;

    public PanelItem(final StorageItem item, final int itemContainerId, final ItemManager itemManager) {
        this.itemManager = itemManager;
        this.item = item;
        this.itemContainerId = itemContainerId;
        renderItem();
    }

    public void updateItemAndItemContainerId(final StorageItem item, final int itemContainerId) {
        this.item = item;
        this.itemContainerId = itemContainerId;
        renderItem();
    }

    public StorageItem getItem() {
        return item;
    }

    public int getItemContainerId() {
        return itemContainerId;
    }

    private void renderItem() {
        final boolean hasIcon = itemContainerId != ItemContainerId.BANK;
        final int itemLabelWidth = PluginPanel.PANEL_WIDTH + (hasIcon ? -0 : 0);
        setAlignmentX(LEFT_ALIGNMENT);

        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        setPreferredSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
        setMaximumSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));

        // Item label with icon and name
        final JLabel itemLabel = new JLabel();

        itemLabel.setPreferredSize(new Dimension(itemLabelWidth, ITEM_HEIGHT));
        itemLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
//        itemLabel.setBackground(Color.YELLOW);
//        itemLabel.setOpaque(true);
//        itemLabel.setBackground(Color.GRAY);
        itemLabel.setIcon(getCachedIcon(item.id, item.getQuantity(), itemManager));
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

        revalidate();
        repaint();
    }

    private BufferedImage getEstateIcon() {
        if (!estateIcon.isPresent()) {
            estateIcon = Optional.of(ImageUtil.loadImageResource(getClass(), "/estate.png"));
        }
        return estateIcon.get();
    }
}
