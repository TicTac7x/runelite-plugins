package tictac7x.gotr.store;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.MenuEntry;
import tictac7x.gotr.Notifications;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class EntranceBarrier {
    private final int BARRIER_ACTIVE = 43700;
    private final int BARRIER_INACTIVE = 43849;
    private final int BARRIER_REENTER_GAMETICKS = 51;

    private final Client client;
    private final Notifications notifications;
    private final TicTac7xGotrImprovedConfig config;

    private int gameTickInactiveBarrierDespawned = 0;
    private Optional<Instant> barrierReenterTimeLeft = Optional.empty();
    private Optional<GameObject> barrier = Optional.empty();

    private boolean notifiedBeforePassableBarrier = false;
    private boolean notifiedPassableBarrier = false;

    public EntranceBarrier(final Client client, final Notifications notifications, final TicTac7xGotrImprovedConfig config) {
        this.client = client;
        this.notifications = notifications;
        this.config = config;
    }

    public Optional<Instant> getBarrierReenterTimeLeft() {
        return barrierReenterTimeLeft;
    }

    public void onGameObjectSpawned(final GameObject gameObject) {
        if (gameObject.getId() == BARRIER_ACTIVE && gameTickInactiveBarrierDespawned == 1) {
            barrierReenterTimeLeft = Optional.of(Instant.now().plusMillis(BARRIER_REENTER_GAMETICKS * 600));
            notifiedBeforePassableBarrier = false;
            notifiedPassableBarrier = false;
        }

        if (gameObject.getId() == BARRIER_ACTIVE) {
            barrier = Optional.of(gameObject);
        }
    }

    public void onGameObjectDespawned(final GameObject gameObject) {
        if (gameObject.getId() == BARRIER_INACTIVE) {
            gameTickInactiveBarrierDespawned = 1;
        }

        if (gameObject.getId() == BARRIER_ACTIVE) {
            barrier = Optional.empty();
        }
    }

    public void onGameStateChanged(final GameState gameState) {
        if (gameState == GameState.LOADING) {
            barrier = Optional.empty();
        }
    }

    public void onGameTick() {
        if (gameTickInactiveBarrierDespawned < 10) {
            gameTickInactiveBarrierDespawned++;
        }

        if (barrierReenterTimeLeft.isPresent()) {
            final long seconds = Duration.between(Instant.now(), barrierReenterTimeLeft.get()).getSeconds();

            if (seconds < 5 && !notifiedBeforePassableBarrier) {
                notifiedBeforePassableBarrier = true;
                notifications.notifyBeforePassableBarrier();
            }

            if (seconds < 0 && !notifiedPassableBarrier) {
                notifiedPassableBarrier = true;
                notifications.notifyAboutPassableBarrier();
            }
        }
    }

    public void onMenuEntryAdded(final MenuEntry menuEntry) {
        if (
            config.preventQuickLeave() &&
                (
                    barrier.isPresent() && client.getLocalPlayer().getLocalLocation().distanceTo(barrier.get().getLocalLocation()) < 128 ||
                    client.getLocalPlayer().getWorldLocation().getY() >= 9483
                ) &&
            menuEntry.getTarget().contains("Barrier") &&
            menuEntry.getOption().contains("Quick-pass")
        ) {
            menuEntry.setOption("Deprioritized quick-pass");
        }
    }
}
