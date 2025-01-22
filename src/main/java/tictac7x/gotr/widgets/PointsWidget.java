package tictac7x.gotr.widgets;

import net.runelite.api.Client;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;
import tictac7x.gotr.TicTac7xGotrImprovedPlugin;
import tictac7x.gotr.store.Points;
import tictac7x.gotr.types.PointsWidgetStyle;

import java.awt.*;
import java.util.Optional;

public class PointsWidget extends Overlay {
    private final int GOTR_WIDGET_GROUP = 746;
    private final int GOTR_WIDGET_CHILD = 2;
    private final int GOTR_WIDGET_ELEMENTAL_POINTS_CHILD = 21;
    private final int GOTR_WIDGET_CATALYTIC_POINTS_CHILD = 24;

    private Optional<Widget> gotrWidget = Optional.empty();
    private Optional<Widget> elementalPointsWidget = Optional.empty();
    private Optional<Widget> catalyticPointsWidget = Optional.empty();

    private final Client client;
    private final TicTac7xGotrImprovedConfig config;
    private final Points points;

    public PointsWidget(final Client client, final TicTac7xGotrImprovedConfig config, final Points points) {
        this.client = client;
        this.config = config;
        this.points = points;
        setPosition(OverlayPosition.DYNAMIC);
    }

    public void onWidgetLoaded(final WidgetLoaded event) {
        if (event.getGroupId() == GOTR_WIDGET_GROUP) {
            gotrWidget = Optional.ofNullable(client.getWidget(GOTR_WIDGET_GROUP, GOTR_WIDGET_CHILD));
            elementalPointsWidget = Optional.ofNullable(client.getWidget(GOTR_WIDGET_GROUP, GOTR_WIDGET_ELEMENTAL_POINTS_CHILD));
            catalyticPointsWidget = Optional.ofNullable(client.getWidget(GOTR_WIDGET_GROUP, GOTR_WIDGET_CATALYTIC_POINTS_CHILD));
        }
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        if (!gotrWidget.isPresent() || gotrWidget.get().isHidden() || !elementalPointsWidget.isPresent() || !catalyticPointsWidget.isPresent()) return null;

        elementalPointsWidget.get().setHidden(true);
        catalyticPointsWidget.get().setHidden(true);

        int x = gotrWidget.get().getCanvasLocation().getX();
        int y = gotrWidget.get().getCanvasLocation().getY();

        TicTac7xGotrImprovedPlugin.drawCenteredString(
            graphics,
            "Elemental:",
            new Rectangle(x + 46, y + 79, 0, 0),
            config.getElementalColor(),
            FontManager.getRunescapeFont()
        );
        TicTac7xGotrImprovedPlugin.drawCenteredString(
            graphics,
            getPointsString(points.getTotalElementalPoints(), points.getGameElementalPoints()),
            new Rectangle(x + 46, y + 95, 0, 0),
            config.getElementalColor(),
            FontManager.getRunescapeFont()
        );

        TicTac7xGotrImprovedPlugin.drawCenteredString(
            graphics,
            "Catalytic:",
            new Rectangle(x + 124, y + 79, 0, 0),
            config.getCatalyticColor(),
            FontManager.getRunescapeFont()
        );
        TicTac7xGotrImprovedPlugin.drawCenteredString(
            graphics,
                getPointsString(points.getTotalCatalyticPoints(), points.getGameCatalyticPoints()),
            new Rectangle(x + 124, y + 95, 0, 0),
            config.getCatalyticColor(),
            FontManager.getRunescapeFont()
        );

        return null;
    }

    public void shutDown() {
        if (elementalPointsWidget.isPresent()) {
            elementalPointsWidget.get().setHidden(false);
        }

        if (catalyticPointsWidget.isPresent()) {
            catalyticPointsWidget.get().setHidden(false);
        }
    }

    private String getPointsString(final int totalPoints, final int gamePoints) {
        return config.getPointsWidgetStyle() == PointsWidgetStyle.SEPARATE
            ? String.format("%d + %.2f", totalPoints, (double) gamePoints / 100)
            : String.format("%.2f", totalPoints + (double) gamePoints / 100);
    }
}
