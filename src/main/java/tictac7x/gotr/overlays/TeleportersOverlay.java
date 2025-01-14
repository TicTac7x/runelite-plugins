package tictac7x.gotr.overlays;

import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;
import tictac7x.gotr.TicTac7xGotrImprovedPlugin;
import tictac7x.gotr.store.Inventory;
import tictac7x.gotr.store.Teleporters;
import tictac7x.gotr.types.Teleporter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class TeleportersOverlay extends net.runelite.client.ui.overlay.Overlay {
    private final Client client;
    private final ItemManager items;
    private final ModelOutlineRenderer outlines;
    private final TicTac7xGotrImprovedConfig config;
    private final Teleporters teleporters;

    private final Inventory inventory;

    public TeleportersOverlay(
        final Client client,
        final ItemManager items,
        final ModelOutlineRenderer outlines,
        final TicTac7xGotrImprovedConfig config,
        final Teleporters teleporters,
        final Inventory inventory
    ) {
        this.client = client;
        this.items = items;
        this.outlines = outlines;
        this.config = config;
        this.teleporters = teleporters;
        this.inventory = inventory;

        super.setPosition(OverlayPosition.DYNAMIC);
        super.setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        // Teleporters highlighting disabled.
        if (!config.highlightActiveTeleporters()) return null;

        for (final Map.Entry<GameObject, Teleporter> entry : teleporters.getTeleporters().entrySet()) {
            final GameObject teleporterGameObject = entry.getKey();
            final Teleporter teleporter = entry.getValue();
            final boolean hasTalisman = inventory.hasTeleporterTalisman(teleporter);
            final boolean isActive = ((DynamicObject) teleporterGameObject.getRenderable()).getAnimation().getId() == Teleporter.ACTIVE_ANIMATION_ID;

            // Teleporter not usable.
            if (!isActive && !hasTalisman) continue;

            // Teleporter level requirement not met.
            if (client.getBoostedSkillLevel(Skill.RUNECRAFT) < teleporter.level) continue;

            // Outline.
            try {
                outlines.drawOutline(teleporterGameObject, 2,
                    config.indicateUnusableTeleporters() && inventory.hasGuardianStones() ? Color.red :
                    teleporter.isElemental() ? config.getElementalColor() :
                    config.getCatalyticColor(),
                2);
            } catch (final Exception ignored) {}

            // Image.
            final BufferedImage teleporterImage = items.getImage(isActive ? teleporter.runeItemId : teleporter.talismanItemId);
            try {
                OverlayUtil.renderImageLocation(client, graphics, teleporterGameObject.getLocalLocation(), teleporterImage, 500);
            } catch (final Exception ignored) {}

            // Remaining time.
            if (isActive && teleporters.getTimeLeft().isPresent()) {
                final long seconds = Duration.between(Instant.now(), teleporters.getTimeLeft().get()).getSeconds();
                final long milliseconds = Duration.between(Instant.now(), teleporters.getTimeLeft().get()).getNano() / 1_000_000 % 1000 / 100;
                final String time = seconds % 60 + "." + milliseconds;

                try {
                    graphics.setFont(FontManager.getRunescapeSmallFont());
                    final Point rune_location = Perspective.getCanvasImageLocation(client, teleporterGameObject.getLocalLocation(), teleporterImage, 500);
                    final Rectangle rectangle = new Rectangle(rune_location.getX() + 16, rune_location.getY() - 18, 0, 24);
                    TicTac7xGotrImprovedPlugin.drawCenteredString(graphics, time, rectangle,
                        inventory.hasGuardianStones() ? Color.red :
                        Color.WHITE,
                    FontManager.getRunescapeSmallFont());
                } catch (final Exception ignored) {}
            }
        }

        return null;
    }
}
