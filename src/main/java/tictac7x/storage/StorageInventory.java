package tictac7x.storage;

import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.util.ImageUtil;

import javax.annotation.Nullable;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class StorageInventory extends Storage {
    private int INVENTORY_SIZE = 28;
    private int NULL_ITEM = -1;

    private int panel_width = 0;
    private int empty = 0;
    private final BufferedImage inventory_png;
    private ImageComponent inventory_image;

    @Nullable
    private ImageComponent inventory_free;

    public StorageInventory(final String storage_id, final InventoryID item_container_id, final WidgetInfo widget_info, final Client client, final ClientThread client_thread, final ConfigManager configs, final TicTac7xStorageConfig config, final ItemManager items) {
        super(storage_id, item_container_id, widget_info, client, client_thread, configs, config, items);
        this.inventory_png = ImageUtil.loadImageResource(getClass(), "/inventory.png");
        client_thread.invokeLater(() -> updateInventoryItem(INVENTORY_SIZE));
        client_thread.invokeLater(() -> updateInventoryFree(INVENTORY_SIZE));
    }

    @Override
    public void onItemContainerChanged(final ItemContainerChanged event) {
        super.onItemContainerChanged(event);
        this.updateEmpty(event);
    }

    @Override
    protected void renderBefore() {
        switch (config.getInventoryEmpty()) {
            case TOP:
                this.renderFree();
                return;
            case FIRST:
                itemsPanelComponent.getChildren().add(this.inventory_image);
                return;
        }
    }

    @Override
    protected void renderAfter() {
        switch (config.getInventoryEmpty()) {
            case LAST:
                itemsPanelComponent.getChildren().add(this.inventory_image);
                return;
            case BOTTOM:
                this.renderFree();
                return;
        }
    }

    private void renderFree() {
        // Extra checks to re-render the free text.
        if (
            this.inventory_free == null ||
            this.inventory_free.getBounds().width == 0 ||
            itemsPanelComponent.getBounds().width != panel_width
        ) {
            this.updateInventoryFree(this.empty);
            this.panel_width = itemsPanelComponent.getBounds().width;
        }

        if (this.inventory_free != null) panelComponent.getChildren().add(this.inventory_free);
    }

    private void updateEmpty(final ItemContainerChanged event) {
        if (event.getContainerId() != this.item_container_id) return;

        int empty = INVENTORY_SIZE;
        for (final Item item : event.getItemContainer().getItems()) {
            if (item.getId() != NULL_ITEM) empty--;
        }

        this.empty = empty;
        this.updateInventoryItem(empty);
        this.updateInventoryFree(empty);
    }

    private void updateInventoryItem(final int empty) {
        final String free = String.valueOf(empty);

        // Make copy of inventory icon.
        final BufferedImage inventory_image = new BufferedImage(this.inventory_png.getWidth(), this.inventory_png.getHeight(), this.inventory_png.getType());
        final Graphics graphics = inventory_image.getGraphics();
        graphics.drawImage(this.inventory_png, 0, 0, null);

        // Free slots count.
        final FontMetrics fm = graphics.getFontMetrics();
        graphics.setFont(FontManager.getRunescapeSmallFont());

        // Shadow.
        graphics.setColor(Color.BLACK);
        graphics.drawString(free, 1, fm.getAscent());

        // Yellow label.
        graphics.setColor(Color.YELLOW);
        graphics.drawString(free, 0, fm.getAscent() - 1);

        graphics.dispose();
        this.inventory_image = new ImageComponent(inventory_image);
    }

    private void updateInventoryFree(final int empty) {
        try {
            final String free = empty + " free";

            final BufferedImage free_image = new BufferedImage(itemsPanelComponent.getBounds().width - 8, 16, BufferedImage.TYPE_4BYTE_ABGR);
            final Graphics graphics = free_image.getGraphics();
            final FontMetrics font_metrics = graphics.getFontMetrics();
            graphics.setFont(FontManager.getRunescapeFont());

            // Shadow.
            graphics.setColor(Color.BLACK);
            graphics.drawString(free, ((free_image.getWidth() - font_metrics.stringWidth(free)) / 2) + 1, font_metrics.getAscent() + 2);

            // Label.
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.drawString(free, (free_image.getWidth() - font_metrics.stringWidth(free)) / 2, font_metrics.getAscent() + 1);

            graphics.dispose();
            this.inventory_free = new ImageComponent(free_image);
        } catch (final Exception ignored) {}
    }
}
