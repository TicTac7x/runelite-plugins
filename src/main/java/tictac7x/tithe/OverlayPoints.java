package tictac7x.tithe;

import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.ui.overlay.OverlayPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Optional;

import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

public class OverlayPoints extends OverlayPanel {
    private final int TITHE_FARM_POINTS = Varbits.TITHE_FARM_POINTS;
    private final int TITHE_FARM_SACK = Varbits.TITHE_FARM_SACK_AMOUNT;

    private final int TITHE_FARM_WIDGET_PARENT = 241;
    private final int TITHE_FARM_WIDGET_CHILD = 2;

    private final TicTac7xTithePlugin plugin;
    private final TicTac7xTitheConfig config;
    private final Client client;
    private final Inventory inventory;

    public int totalPoints = 0;
    public int fruitsInSack = 0;


    public OverlayPoints(final TicTac7xTithePlugin plugin, final TicTac7xTitheConfig config, final Client client, final Inventory inventory) {
        this.plugin = plugin;
        this.config = config;
        this.client = client;
        this.inventory = inventory;

        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    public void onWidgetLoaded(final WidgetLoaded event) {
        if (event.getGroupId() != TITHE_FARM_WIDGET_PARENT) return;

        if (config.showCustomPoints()) {
            this.hideNativePoints();
        } else {
            this.showNativePoints();
        }
    }

    public void onConfigChanged(final ConfigChanged event) {
        // Wrong config changed.
        if (!plugin.inTitheFarm() || !event.getGroup().equals(TicTac7xTitheConfig.group) || !event.getKey().equals(TicTac7xTitheConfig.points)) return;

        // Correct config changed.
        this.checkWidget();
    }

    private Optional<Widget> getNativeTitheWidget() {
        return Optional.ofNullable(client.getWidget(TITHE_FARM_WIDGET_PARENT, TITHE_FARM_WIDGET_CHILD));
    }

    public void showNativePoints() {
        final Optional<Widget> nativeWidget = getNativeTitheWidget();

        if (nativeWidget.isPresent()) nativeWidget.get().setHidden(false);
    }

    public void hideNativePoints() {
        final Optional<Widget> nativeWidget = getNativeTitheWidget();
        if (nativeWidget.isPresent()) nativeWidget.get().setHidden(true);
    }

    public void startUp() {
        this.checkWidget();
    }

    public void shutDown() {
        showNativePoints();
    }

    private void checkWidget() {
        if (config.showCustomPoints()) {
            this.hideNativePoints();
        } else {
            this.showNativePoints();
        }
    }

    public void onVarbitChanged(final VarbitChanged event) {
        switch (event.getVarbitId()) {
            case TITHE_FARM_POINTS:
                this.totalPoints = event.getValue();
                return;
            case TITHE_FARM_SACK:
                this.fruitsInSack = event.getValue();
        }
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        if (!plugin.inTitheFarm() || !config.showCustomPoints()) return null;

        final int fruits = inventory.getFruits();
        final int pointsEarned = (int) (Math.floor((double) fruits / 100) *35 + Math.floor((double) (fruits % 100) / 3));

        panelComponent.getChildren().clear();

        // Points.
        panelComponent.getChildren().add(LineComponent.builder()
            .left("Points:").leftColor(new Color(200, 200, 200))
            .right(this.totalPoints + (pointsEarned > 0 ? " + " + pointsEarned : "")).rightColor(Color.green)
            .build()
        );

        return super.render(graphics);
    }
}
