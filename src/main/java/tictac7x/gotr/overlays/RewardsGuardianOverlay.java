package tictac7x.gotr.overlays;

import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;
import tictac7x.gotr.TicTac7xGotrImprovedPlugin;

import java.awt.*;
import java.util.Optional;

public class RewardsGuardianOverlay extends Overlay {
    private final Client client;
    private final TicTac7xGotrImprovedConfig config;

    private Optional<GameObject> gameObject = Optional.empty();

    public RewardsGuardianOverlay(final Client client, final TicTac7xGotrImprovedConfig config) {
        this.client = client;
        this.config = config;

        super.setPosition(OverlayPosition.DYNAMIC);
        super.setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    public void onGameObjectSpawned(final GameObjectSpawned event) {
        if (event.getGameObject().getId() == 43695) {
            gameObject = Optional.of(event.getGameObject());
        }
    }

    public void onGameStateChanged(final GameStateChanged event) {
        if (event.getGameState() == GameState.LOADING) {
            gameObject = Optional.empty();
        }
    }

    public void onGameObjectDespawned(final GameObject gameObject) {
        if (gameObject.getId() == 43695) {
            this.gameObject = Optional.empty();
        }
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        if (!config.showRewardsGuardianOverlay()) return null;

        if (gameObject.isPresent()) {
            final String text = "Rewards: " + getMaxSearches();

            final FontMetrics metrics = graphics.getFontMetrics();
            final int textWidth = metrics.stringWidth(text);
            final Point textLocation = Perspective.getCanvasTextLocation(client, graphics, gameObject.get().getLocalLocation(), text, 270);
            final Rectangle rectangle = new Rectangle(textLocation.getX(), textLocation.getY(), textWidth, 0);
            TicTac7xGotrImprovedPlugin.drawCenteredString(graphics, text, rectangle, new Color(220, 220, 220), FontManager.getRunescapeFont());
        }

        return null;
    }

    private int getMaxSearches() {
        return Math.min(config.getCatalyticEnergy(), config.getElementalEnergy());
    }
}
