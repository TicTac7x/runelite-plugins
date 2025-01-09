package tictac7x.tithe;

import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import java.awt.Graphics2D;

public class OverlayInventory extends WidgetItemOverlay {
    private final TicTac7xTithePlugin plugin;
    private final TicTac7xTitheConfig config;

    public OverlayInventory(final TicTac7xTithePlugin plugin, final TicTac7xTitheConfig config) {
        this.plugin = plugin;
        this.config = config;
        super.showOnInventory();
    }

    @Override
    public void renderItemOverlay(final Graphics2D graphics, int itemId, final WidgetItem widgetItem) {
        if (!plugin.inTitheFarm() || config.getHighlightSeedsColor().getAlpha() == 0) return;

        switch (itemId) {
            case ItemID.GOLOVANOVA_SEED:
            case ItemID.BOLOGANO_SEED:
            case ItemID.LOGAVANO_SEED:
                graphics.setColor(config.getHighlightSeedsColor());
                graphics.fill(widgetItem.getCanvasBounds());
                return;
            case ItemID.WATERING_CAN1:
            case ItemID.WATERING_CAN2:
            case ItemID.WATERING_CAN3:
            case ItemID.WATERING_CAN4:
            case ItemID.WATERING_CAN5:
            case ItemID.WATERING_CAN6:
            case ItemID.WATERING_CAN7:
            case ItemID.WATERING_CAN8:
            case ItemID.GRICOLLERS_CAN:
                graphics.setColor(config.getHighlightWateringCanColor());
                graphics.fill(widgetItem.getCanvasBounds());
        }
    }
}