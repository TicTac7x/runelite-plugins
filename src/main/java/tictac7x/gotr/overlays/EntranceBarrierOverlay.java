package tictac7x.gotr.overlays;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.components.ProgressPieComponent;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;
import tictac7x.gotr.store.EntranceBarrier;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class EntranceBarrierOverlay extends Overlay {
    private final int BARRIER_ACTIVE = 43700;
    private final int BARRIER_REENTER_GAMETICKS = 51;
    private final int GAMETICK_DURATION = 600;

    private final Client client;
    private final ModelOutlineRenderer modelOutlineRenderer;
    private final EntranceBarrier entranceBarrier;
    private final TicTac7xGotrImprovedConfig config;

    private Optional<GameObject> barrierGameObject = Optional.empty();

    public EntranceBarrierOverlay(final Client client, final ModelOutlineRenderer modelOutlineRenderer, final EntranceBarrier entranceBarrier, final TicTac7xGotrImprovedConfig config) {
        this.client = client;
        this.modelOutlineRenderer = modelOutlineRenderer;
        this.entranceBarrier = entranceBarrier;
        this.config = config;
    }

    public void onGameObjectSpawned(final GameObject gameObject) {
        if (gameObject.getId() == BARRIER_ACTIVE) {
            barrierGameObject = Optional.of(gameObject);
        }
    }

    public void onGameObjectDespawned(final GameObject gameObject) {
        if (gameObject.getId() == BARRIER_ACTIVE) {
            barrierGameObject = Optional.empty();
        }
    }

    public void onGameStateChanged(final GameState gameState) {
        if (gameState == GameState.LOADING) {
            barrierGameObject = Optional.empty();
        }
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        if (!barrierGameObject.isPresent()) return null;
        if (!entranceBarrier.getBarrierReenterTimeLeft().isPresent()) return null;
        if (!isBehindBarrier()) return null;

        final long seconds = Duration.between(Instant.now(), entranceBarrier.getBarrierReenterTimeLeft().get()).getSeconds();

        if (config.showBarrierRemainingTimePie()) {
            final ProgressPieComponent pie = new ProgressPieComponent();
            pie.setPosition(barrierGameObject.get().getCanvasLocation(420));
            pie.setFill(seconds > 0 ? Color.RED : Color.GREEN);
            pie.setBorder(seconds > 0 ? Color.RED : Color.GREEN, 1);
            pie.setProgress((double) Duration.between(Instant.now(), entranceBarrier.getBarrierReenterTimeLeft().get()).getSeconds() / ((double) (BARRIER_REENTER_GAMETICKS * GAMETICK_DURATION) / 1000) - 1);
            try { pie.render(graphics); } catch (final Exception ignored) {}
        }

        if (config.showBarrierRemainingTime()) {
            if (seconds >= 0) {
                final long milliseconds = Duration.between(Instant.now(), entranceBarrier.getBarrierReenterTimeLeft().get()).getNano() / 1_000_000 % 1000 / 100;
                final String time = seconds + "." + milliseconds;
                final Point location =  Perspective.getCanvasTextLocation(client, graphics, barrierGameObject.get().getLocalLocation(), time, 410);

                try { OverlayUtil.renderTextLocation(graphics, location, time, Color.WHITE); } catch (final Exception ignored) {}
            }

            if (seconds < 0) {
                try { modelOutlineRenderer.drawOutline(barrierGameObject.get(), 2, Color.green, 2); } catch (final Exception ignored) {}
            }
        }

        return null;
    }

    private boolean isBehindBarrier() {
        return client.getLocalPlayer().getWorldLocation().getY() <= 9482;
    }
}
