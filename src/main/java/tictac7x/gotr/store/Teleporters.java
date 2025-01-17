package tictac7x.gotr.store;

import net.runelite.api.ChatMessageType;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;

import java.time.Instant;
import java.util.*;

public class Teleporters {
    private final String PORTAL_OPENED = "Creatures from the Abyss begin their attack!";
    private final String KEEP_RIFTS_OPEN = "The Portal Guardians will keep their rifts open for another 30 seconds.";
    private final int TELEPORTER_ACTIVE_IN_GAME_GAMETICKS = 33;
    private final int TELEPORTER_ACTIVE_AFTER_GAME_GAMETICKS = 50;
    private final int GAMETICK_DURATION = 600;

    private Optional<Integer> gameticks_left = Optional.empty();
    private Optional<Instant> time_left = Optional.empty();
    private final Map<GameObject, Teleporter> teleporters = new HashMap<>();
    private boolean game_starting = false;
    private boolean keep_rifts_open = false;

    public Optional<Instant> getTimeLeft() {
        return time_left;
    }

    public Map<GameObject, Teleporter> getTeleporters() {
        return teleporters;
    }

    public void onChatMessage(final ChatMessage message) {
        if (message.getType() == ChatMessageType.GAMEMESSAGE) {
            game_starting = message.getMessage().equals(PORTAL_OPENED);
            keep_rifts_open = message.getMessage().equals(KEEP_RIFTS_OPEN);
        }
    }

    public void onGameTick() {
        if (gameticks_left.isPresent()) {
            gameticks_left = Optional.of(gameticks_left.get() - 1);

            if (gameticks_left.get() == 0) {
                startTeleportersCycle();
            }
        }

        if (game_starting) {
            game_starting = false;
            startTeleportersCycle();
        }

        if (keep_rifts_open) {
            keep_rifts_open = false;
            gameticks_left = Optional.empty();
            time_left = Optional.of(Instant.now().plusMillis(TELEPORTER_ACTIVE_AFTER_GAME_GAMETICKS * GAMETICK_DURATION));
        }
    }

    public void onGameObjectSpawned(final GameObject object) {
        final Optional<Teleporter> teleporter = Arrays.stream(Teleporter.ALL).filter(t -> t.teleporterGameObjectid == object.getId()).findFirst();
        teleporter.ifPresent(value -> teleporters.put(object, value));
    }

    public void onGameStateChanged(final GameState game_state) {
        if (game_state == GameState.LOADING) {
            teleporters.clear();
        }
    }

    private void startTeleportersCycle() {
        gameticks_left = Optional.of(TELEPORTER_ACTIVE_IN_GAME_GAMETICKS);
        time_left = Optional.of(Instant.now().plusMillis((TELEPORTER_ACTIVE_IN_GAME_GAMETICKS) * GAMETICK_DURATION));
    }
}
