package tictac7x.tithe;

import net.runelite.api.GameObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GameObjectSpawned;

import java.util.HashMap;
import java.util.Map;

public class PlantsManager {
    private final TicTac7xTithePlugin plugin;
    private final TicTac7xTitheConfig config;

    private final Map<LocalPoint, Plant> plants = new HashMap<>();

    public PlantsManager(final TicTac7xTithePlugin plugin, final TicTac7xTitheConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    public Map<LocalPoint, Plant> getPlants() {
        return plants;
    }

    public void onGameTick() {
        for (final Plant plant : this.plants.values()) {
            plant.onGameTick();
        }
    }

    /**
     * Update plant state to watered based on the game object.
     * @param event - Tithe plant spawned event.
     */
    public void onGameObjectSpawned(final GameObjectSpawned event) {
        if (!plugin.inTitheFarm()) return;

        final GameObject gameObject = event.getGameObject();
        if (!Plant.isPatch(gameObject)) return;

        final LocalPoint patchLocation = gameObject.getLocalLocation();

        // Empty patch, plant completed.
        if (gameObject.getId() == Plant.TITHE_EMPTY_PATCH) {
            this.plants.remove(patchLocation);

        // Update plant state.
        } else if (this.plants.containsKey(patchLocation)) {
            this.plants.get(patchLocation).setGameObject(gameObject);
        }

        // GameObject is seedling.
        if (Plant.isSeedling(gameObject)) {
            this.plants.put(patchLocation, new Plant(config, gameObject));
        }
    }
}
