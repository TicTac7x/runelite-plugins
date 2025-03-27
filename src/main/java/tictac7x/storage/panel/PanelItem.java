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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelItem extends JLayeredPane {
    private final ItemManager itemManager;
    private final StorageItem item;
    public final int itemContainerId;

    private static Optional<BufferedImage> pohIcon = Optional.empty();
    private static final int ITEM_WIDTH = PluginPanel.PANEL_WIDTH + 3;
    private static final int ITEM_HEIGHT = 36;
    private static final int ICON_SIZE = 15;


    public PanelItem(final StorageItem item, final int itemContainerId, final ItemManager itemManager) {
        this.itemManager = itemManager;
        this.item = item;
        this.itemContainerId = itemContainerId;

        renderItem();
        addHoverEffect();
    }

    protected void renderItem() {
        setAlignmentX(LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
        setMinimumSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
        setMaximumSize(new Dimension(ITEM_WIDTH, ITEM_HEIGHT));
        setOpaque(true);
        setBackground(ColorScheme.DARKER_GRAY_COLOR);

        final boolean hasIcon = itemContainerId != ItemContainerId.BANK;
        final int itemLabelWidth = ITEM_WIDTH + (hasIcon ? - ICON_SIZE - 7 : -0);
        final JLabel itemLabel = new JLabel();
        itemLabel.setPreferredSize(new Dimension(itemLabelWidth, ITEM_HEIGHT));
        itemLabel.setIcon(getCachedIcon(item.id, item.getQuantity(), itemManager));
        itemLabel.setText(item.name);
        itemLabel.setHorizontalAlignment(SwingConstants.LEFT);
        itemLabel.setBounds(5, 0, itemLabelWidth - 5, ITEM_HEIGHT);
        add(itemLabel, JLayeredPane.DEFAULT_LAYER);

        if (itemContainerId == ItemContainerId.HOME) {
            final BufferedImage smallIcon = getPohIcon();
            final JLabel iconOverlay = new JLabel(new ImageIcon(smallIcon));

            iconOverlay.setBounds(
                itemLabelWidth,
                (ITEM_HEIGHT - ICON_SIZE) / 2,  // Vertically center the icon
                ICON_SIZE,
                ICON_SIZE
            );

            add(iconOverlay, JLayeredPane.PALETTE_LAYER);
        }
    }

    private void addHoverEffect() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent event) {
                setBackground(ColorScheme.DARK_GRAY_HOVER_COLOR);
                setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR, 1));
                repaint();
            }

            @Override
            public void mouseExited(final MouseEvent event) {
                setBackground(ColorScheme.DARKER_GRAY_COLOR);
                setBorder(BorderFactory.createEmptyBorder());
                repaint();
            }
        });
    }

    private BufferedImage getPohIcon() {
        if (!pohIcon.isPresent()) {
            pohIcon = Optional.of(ImageUtil.loadImageResource(getClass(), "/poh.png"));
        }

        return pohIcon.get();
    }
}

