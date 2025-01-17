package tictac7x.gotr.widgets;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;
import tictac7x.gotr.TicTac7xGotrImprovedPlugin;
import tictac7x.gotr.types.PointsWidgetStyle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnergyWidget extends Overlay {
    private final int GOTR_WIDGET_GROUP = 746;
    private final int GOTR_WIDGET_CHILD = 2;
    private final int GOTR_WIDGET_ELEMENTAL_ENERGY = 21;
    private final int GOTR_WIDGET_CATALYTIC_ENERGY = 24;

    private final Pattern regexElementalEnergy = Pattern.compile("Elemental<br>Energy: (?<energy>.+)");
    private final Pattern regexCatalyticEnergy = Pattern.compile("Catalytic<br>Energy: (?<energy>.+)");

    private final Client client;
    private final TicTac7xGotrImprovedConfig config;

    public EnergyWidget(final Client client, final TicTac7xGotrImprovedConfig config) {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        final Optional<Widget> widgetGotr = Optional.ofNullable(client.getWidget(GOTR_WIDGET_GROUP, GOTR_WIDGET_CHILD));
        if (!widgetGotr.isPresent() || widgetGotr.get().isHidden()) return null;

        final Optional<Widget> widgetElementalEnergy = Optional.ofNullable(client.getWidget(GOTR_WIDGET_GROUP, GOTR_WIDGET_ELEMENTAL_ENERGY));
        final Optional<Widget> widgetCatalyticEnergy = Optional.ofNullable(client.getWidget(GOTR_WIDGET_GROUP, GOTR_WIDGET_CATALYTIC_ENERGY));
        if (!widgetElementalEnergy.isPresent() || !widgetCatalyticEnergy.isPresent()) return null;

        widgetElementalEnergy.get().setHidden(true);
        widgetCatalyticEnergy.get().setHidden(true);

        final Matcher matcherElementalEnergy = regexElementalEnergy.matcher(widgetElementalEnergy.get().getText());
        final Matcher matcherCatalyticEnergy = regexCatalyticEnergy.matcher(widgetCatalyticEnergy.get().getText());
        matcherElementalEnergy.find();
        matcherCatalyticEnergy.find();

        final double elementalEnergy = Integer.parseInt(matcherElementalEnergy.group("energy"));
        final double catalyticEnergy = Integer.parseInt(matcherCatalyticEnergy.group("energy"));

        int x = widgetGotr.get().getCanvasLocation().getX();
        int y = widgetGotr.get().getCanvasLocation().getY();

        final String elementalEnergyString = config.getPointsWidgetStyle() == PointsWidgetStyle.SEPARATE ? config.getElementalEnergy() + " + " + elementalEnergy / 100 : String.valueOf(config.getElementalEnergy() + elementalEnergy / 100);
        final String catalyticEnergyString = config.getPointsWidgetStyle() == PointsWidgetStyle.SEPARATE ? config.getCatalyticEnergy() + " + " + catalyticEnergy / 100 : String.valueOf(config.getCatalyticEnergy() + catalyticEnergy / 100);

        TicTac7xGotrImprovedPlugin.drawCenteredString(graphics, "Elemental:", new Rectangle(x + 46, y + 79, 0, 0), config.getElementalColor(), FontManager.getRunescapeFont());
        TicTac7xGotrImprovedPlugin.drawCenteredString(graphics, elementalEnergyString, new Rectangle(x + 46, y + 95, 0, 0), config.getElementalColor(), FontManager.getRunescapeFont());

        TicTac7xGotrImprovedPlugin.drawCenteredString(graphics, "Catalytic:", new Rectangle(x + 124, y + 79, 0, 0), config.getCatalyticColor(), FontManager.getRunescapeFont());
        TicTac7xGotrImprovedPlugin.drawCenteredString(graphics, catalyticEnergyString, new Rectangle(x + 124, y + 95, 0, 0), config.getCatalyticColor(), FontManager.getRunescapeFont());

        return null;
    }
}
