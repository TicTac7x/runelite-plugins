package tictac7x.balloon;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.GameState;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;

import java.util.Set;

public class Balloon {
    private final Set<Integer> GAME_OBJECT_IDS = ImmutableSet.of(19133, 19135, 19137, 19139, 19141, 19143);
    private boolean visible = false;

    public void onGameObjectSpawned(final GameObjectSpawned event) {
        if (GAME_OBJECT_IDS.contains(event.getGameObject().getId())) {
            visible = true;
        }
    }

    public void onGameStateChanged(final GameStateChanged event) {
        if (event.getGameState() == GameState.LOADING) {
            visible = false;
        }
    }

    public boolean isVisible() {
        return visible;
    }
}
