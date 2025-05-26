package tictac7x.storage.overlays;

import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.util.ImageUtil;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.utils.Provider;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class InventoryOverlay extends StorageOverlay {
    private int panelWidth = 0;
    private final BufferedImage inventoryIcon;

    private Optional<ImageComponent> freeWithInventoryImage = Optional.empty();
    private Optional<ImageComponent> freeWithLabel = Optional.empty();

    public InventoryOverlay(final String configKey, final Storage storage, final int[] widgetIds, final Provider provider) {
        super(configKey, storage, widgetIds, provider);
        this.inventoryIcon = ImageUtil.loadImageResource(getClass(), "/inventory.png");
        storage.addOnChangeListener(this::updateFreeImages);
    }

    private void updateFreeImages() {
        updateFreeInventoryItem(28 - storage.getSlotsUsed());
        updateFreeWithLabel(28 - storage.getSlotsUsed());
    }

    @Override
    protected void renderBefore() {
        switch (provider.config.getInventoryEmpty()) {
            case TOP:
                this.renderFreeWithLabel();
                return;
            case FIRST:
                if (freeWithInventoryImage.isPresent()) {
                    itemsPanelComponent.getChildren().add(freeWithInventoryImage.get());
                }
        }
    }

    @Override
    protected void renderAfter() {
        switch (provider.config.getInventoryEmpty()) {
            case LAST:
                if (freeWithInventoryImage.isPresent()) {
                    itemsPanelComponent.getChildren().add(freeWithInventoryImage.get());
                }
                return;
            case BOTTOM:
                this.renderFreeWithLabel();
        }
    }

    private void renderFreeWithLabel() {
        // Extra checks to re-render the free text.
        if (
            !freeWithLabel.isPresent() ||
            freeWithLabel.get().getBounds().width == 0 ||
            itemsPanelComponent.getBounds().width != panelWidth
        ) {
            updateFreeWithLabel(28 - storage.getSlotsUsed());
            panelWidth = itemsPanelComponent.getBounds().width;
        }

        if (freeWithLabel.isPresent()) {
            panelComponent.getChildren().add(freeWithLabel.get());
        }
    }

    private void updateFreeInventoryItem(final int empty) {
        final String free = String.valueOf(empty);

        // Make copy of inventory icon.
        final BufferedImage inventoryImage = new BufferedImage(this.inventoryIcon.getWidth(), this.inventoryIcon.getHeight(), this.inventoryIcon.getType());
        final Graphics graphics = inventoryImage.getGraphics();
        graphics.drawImage(this.inventoryIcon, 0, 1, null);

        // Free slots count.
        final FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.setFont(FontManager.getRunescapeSmallFont());

        // Shadow.
        graphics.setColor(Color.BLACK);
        graphics.drawString(free, 1, 11);

        // Yellow label.
        graphics.setColor(Color.YELLOW);
        graphics.drawString(free, 0, 10);

        graphics.dispose();
        freeWithInventoryImage = Optional.of(new ImageComponent(inventoryImage));
    }

    private void updateFreeWithLabel(final int empty) {
        try {
            final String freeText = empty + " free";

            final BufferedImage freeImage = new BufferedImage(itemsPanelComponent.getBounds().width - 8, 16, BufferedImage.TYPE_4BYTE_ABGR);
            final Graphics graphics = freeImage.getGraphics();
            final FontMetrics fontMetrics = graphics.getFontMetrics();
            graphics.setFont(FontManager.getRunescapeFont());

            // Shadow.
            graphics.setColor(Color.BLACK);
            graphics.drawString(freeText, ((freeImage.getWidth() - fontMetrics.stringWidth(freeText)) / 2) + 1, fontMetrics.getAscent() + 2);

            // Label.
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.drawString(freeText, (freeImage.getWidth() - fontMetrics.stringWidth(freeText)) / 2, fontMetrics.getAscent() + 1);

            graphics.dispose();
            freeWithLabel = Optional.of(new ImageComponent(freeImage));
        } catch (final Exception ignored) {}
    }
}
