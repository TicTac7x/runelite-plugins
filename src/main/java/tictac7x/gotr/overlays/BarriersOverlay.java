package tictac7x.gotr.overlays;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;
import tictac7x.gotr.store.Barrier;
import tictac7x.gotr.types.BarrierLevel;
import tictac7x.gotr.store.Barriers;

import java.awt.*;
import java.util.Optional;

public class BarriersOverlay extends Overlay {
    private final int GOTR_WIDGET_GROUP = 746;
    private final int GOTR_WIDGET_CHILD = 2;

    private final Client client;
    private final TicTac7xGotrImprovedConfig config;
    private final Barriers barriers;

    public BarriersOverlay(final Client client, final TicTac7xGotrImprovedConfig config, final Barriers barriers) {
        this.client = client;
        this.config = config;
        this.barriers = barriers;

        super.setPosition(OverlayPosition.DYNAMIC);
        super.setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        if (!config.showBarriersOverlay()) return null;

        final Optional<Widget> widgetGotr = Optional.ofNullable(client.getWidget(GOTR_WIDGET_GROUP, GOTR_WIDGET_CHILD));
        if (!widgetGotr.isPresent() || widgetGotr.get().isHidden()) return null;

        int barrierIndex = 0;
        for (final Barrier barrier : barriers.barriers.values()) {
            renderBarrier(graphics, barrier, barrierIndex++);
        }

        return null;
    }

    private void renderBarrier(final Graphics2D graphics, final Barrier barrier, final int index) {
        final int barThickness = 1;
        final int barHeight = 50;
        final int barWidth = 10;
        final int barOffsetX = 40;
        final int barOffsetY = 150;
        final int barGap = 15;
        final int barRoundness = 2;

        final BarrierLevel level = barrier.getLevel();
        final int healthHeight = barrier.getHealth() * barHeight / 100;

        // Border.
        graphics.setColor(new Color(30, 30, 30));
        graphics.drawRoundRect(
            barOffsetX + index * barGap,
            barOffsetY,
            barWidth,
            barHeight,
            barRoundness,
            barRoundness
        );

        // Empty fill.
        graphics.setColor(new Color(0, 0, 0));
        graphics.fillRoundRect(
            barOffsetX + barThickness + index * barGap,
            barOffsetY + barThickness,
            barWidth - barThickness,
            barHeight - barThickness,
            barRoundness,
            barRoundness
        );

        // Health fill.
        graphics.setColor(
            level == BarrierLevel.TWO ? new Color(50, 60, 150) :
            level == BarrierLevel.THREE ? new Color(85, 145, 70) :
            level == BarrierLevel.FOUR ? new Color(165, 30, 25) :
            new Color(170, 155, 155)
        );
        graphics.fillRoundRect(
            barOffsetX + barThickness + index * barGap,
            barOffsetY + 50 - healthHeight + barThickness,
            barWidth - barThickness,
            healthHeight - barThickness,
            barRoundness,
            barRoundness
        );
    }
}
