package tictac7x.tithe;

import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ProgressPieComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class OverlayPlants extends OverlayPanel {
    private final TicTac7xTithePlugin plugin;
    private final PlantsManager plantsManager;

    public OverlayPlants(final TicTac7xTithePlugin plugin, final PlantsManager plantsManager) {
        this.plugin = plugin;
        this.plantsManager = plantsManager;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        if (!plugin.inTitheFarm()) return null;

        for (final Plant plant : this.plantsManager.getPlants().values()) {
            renderPie(graphics, plant.getGameObject(), plant.getCycleColor(), plant.getCycleProgress());
        }

        return null;
    }

    private void renderPie(final Graphics2D graphics, final TileObject object, final Color color, final float progress) {
        if (color == null || color.getAlpha() == 0) return;

        try {
            final ProgressPieComponent pie = new ProgressPieComponent();
            pie.setPosition(object.getCanvasLocation(0));
            pie.setProgress(-progress);
            pie.setBorderColor(color.darker());
            pie.setFill(color);
            pie.render(graphics);
        } catch (final Exception ignored) {}
    }
}
