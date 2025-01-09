package tictac7x.tithe;

import net.runelite.api.GameObject;
import net.runelite.api.TileObject;

import java.awt.Color;

public class Plant {
    // Tithe empty patch.
    protected static final int TITHE_EMPTY_PATCH = 27383;

    // Golovanova plants.
    protected static final int GOLOVANOVA_SEEDLING = 27384;
    protected static final int GOLOVANOVA_SEEDLING_WATERED = 27385;
    protected static final int GOLOVANOVA_SEEDLING_BLIGHTED = 27386;
    protected static final int GOLOVANOVA_PLANT_1 = 27387;
    protected static final int GOLOVANOVA_PLANT_1_WATERED = 27388;
    protected static final int GOLOVANOVA_PLANT_1_BLIGHTED = 27389;
    protected static final int GOLOVANOVA_PLANT_2 = 27390;
    protected static final int GOLOVANOVA_PLANT_2_WATERED = 27391;
    protected static final int GOLOVANOVA_PLANT_2_BLIGHTED = 27392;
    protected static final int GOLOVANOVA_GROWN = 27393;
    protected static final int GOLOVANOVA_GROWN_BLIGHTED = 27394;

    // Bologano plants.
    protected static final int BOLOGANO_SEEDLING = 27395;
    protected static final int BOLOGANO_SEEDLING_WATERED = 27396;
    protected static final int BOLOGANO_SEEDLING_BLIGHTED = 27397;
    protected static final int BOLOGANO_PLANT_1 = 27398;
    protected static final int BOLOGANO_PLANT_1_WATERED = 27399;
    protected static final int BOLOGANO_PLANT_1_BLIGHTED = 27400;
    protected static final int BOLOGANO_PLANT_2 = 27401;
    protected static final int BOLOGANO_PLANT_2_WATERED = 27402;
    protected static final int BOLOGANO_PLANT_2_BLIGHTED = 27403;
    protected static final int BOLOGANO_GROWN = 27404;
    protected static final int BOLOGANO_GROWN_BLIGHTED = 27405;

    // Logavano plants.
    protected static final int LOGAVANO_SEEDLING = 27406;
    protected static final int LOGAVANO_SEEDLING_WATERED = 27407;
    protected static final int LOGAVANO_SEEDLING_BLIGHTED = 27408;
    protected static final int LOGAVANO_PLANT_1 = 27409;
    protected static final int LOGAVANO_PLANT_1_WATERED = 27410;
    protected static final int LOGAVANO_PLANT_1_BLIGHTED = 27411;
    protected static final int LOGAVANO_PLANT_2 = 27412;
    protected static final int LOGAVANO_PLANT_2_WATERED = 27413;
    protected static final int LOGAVANO_PLANT_2_BLIGHTED = 27414;
    protected static final int LOGAVANO_GROWN = 27415;
    protected static final int LOGAVANO_GROWN_BLIGHTED = 27416;

    // One plant cycle duration in game ticks.
    private final double DURATION_CYCLE_GAME_TICKS = 100;

    public enum State {
        SEEDLING_DRY,
        SEEDLING_WATERED,
        PLANT_1_DRY,
        PLANT_1_WATERED,
        PLANT_2_DRY,
        PLANT_2_WATERED,
        GROWN,
        BLIGHTED
    }

    private final TicTac7xTitheConfig config;
    private GameObject game_object;

    // First state can't be anything else than dry seedling.
    public State state = State.SEEDLING_DRY;

    // Number of ticks to render the progress pie.
    private int ticks = 0;

    public Plant(final TicTac7xTitheConfig config, final GameObject seedling) {
        this.config = config;
        this.game_object = seedling;
    }

    public GameObject getGameObject() {
        return this.game_object;
    }

    public void setGameObject(final GameObject game_object) {
        // Not seedling or plant.
        if (!isPatch(game_object)) return;

        // Update game object reference.
        this.game_object = game_object;

        switch (game_object.getId()) {
            case GOLOVANOVA_SEEDLING_WATERED:
            case BOLOGANO_SEEDLING_WATERED:
            case LOGAVANO_SEEDLING_WATERED:
                this.state = State.SEEDLING_WATERED;
                return;

            case GOLOVANOVA_PLANT_1:
            case BOLOGANO_PLANT_1:
            case LOGAVANO_PLANT_1:
                if (this.state != State.PLANT_1_DRY) this.ticks = 0;
                this.state = State.PLANT_1_DRY;
                return;

            case GOLOVANOVA_PLANT_1_WATERED:
            case BOLOGANO_PLANT_1_WATERED:
            case LOGAVANO_PLANT_1_WATERED:
                this.state = State.PLANT_1_WATERED;
                return;

            case GOLOVANOVA_PLANT_2:
            case BOLOGANO_PLANT_2:
            case LOGAVANO_PLANT_2:
                if (this.state != State.PLANT_2_DRY) this.ticks = 0;
                this.state = State.PLANT_2_DRY;
                return;

            case GOLOVANOVA_PLANT_2_WATERED:
            case BOLOGANO_PLANT_2_WATERED:
            case LOGAVANO_PLANT_2_WATERED:
                this.state = State.PLANT_2_WATERED;
                return;

            case GOLOVANOVA_GROWN:
            case BOLOGANO_GROWN:
            case LOGAVANO_GROWN:
                if (this.state != State.GROWN) this.ticks = 0;
                this.state = State.GROWN;
                return;

            case GOLOVANOVA_SEEDLING_BLIGHTED:
            case GOLOVANOVA_PLANT_1_BLIGHTED:
            case GOLOVANOVA_PLANT_2_BLIGHTED:
            case BOLOGANO_SEEDLING_BLIGHTED:
            case BOLOGANO_PLANT_1_BLIGHTED:
            case BOLOGANO_PLANT_2_BLIGHTED:
            case LOGAVANO_SEEDLING_BLIGHTED:
            case LOGAVANO_PLANT_1_BLIGHTED:
            case LOGAVANO_PLANT_2_BLIGHTED:
                if (this.state != State.BLIGHTED) this.ticks = 0;
                this.state = State.BLIGHTED;
                return;
        }
    }

    /**
     * Update how many ticks the cycle has lasted.
     * We need to update the state based on game ticks, because based on game object spawns doesn't work (too far away).
     */
    public void onGameTick() {
        this.ticks++;
        if (this.ticks != DURATION_CYCLE_GAME_TICKS) return;

        // Reset progress pie.
        this.ticks = 0;

        // Cycle complete, update the state.
        switch (this.state) {
            case SEEDLING_DRY:
            case PLANT_1_DRY:
            case PLANT_2_DRY:
            case GROWN:
                this.state = State.BLIGHTED;
                return;

            case SEEDLING_WATERED:
                this.state = State.PLANT_1_DRY;
                return;

            case PLANT_1_WATERED:
                this.state = State.PLANT_2_DRY;
                return;

            case PLANT_2_WATERED:
                this.state = State.GROWN;
                return;
        }
    }

    public Color getCycleColor() {
        switch (state) {
            case SEEDLING_DRY:
            case PLANT_1_DRY:
            case PLANT_2_DRY:
                return config.getPlantsDryColor();

            case SEEDLING_WATERED:
            case PLANT_1_WATERED:
            case PLANT_2_WATERED:
                return config.getPlantsWateredColor();

            case GROWN:
                return config.getPlantsGrownColor();

            case BLIGHTED:
                return config.getPlantsBlightedColor();
        }

        return null;
    }

    public float getCycleProgress() {
        return -1 + (float) (ticks / (DURATION_CYCLE_GAME_TICKS));
    }

    public static boolean isSeedling(final TileObject patch) {
        final int id = patch.getId();
        return id == GOLOVANOVA_SEEDLING || id == BOLOGANO_SEEDLING || id == LOGAVANO_SEEDLING;
    }

    public static boolean isDry(final TileObject patch) {
        final int id = patch.getId();
        return (
               id == GOLOVANOVA_SEEDLING
            || id == GOLOVANOVA_PLANT_1
            || id == GOLOVANOVA_PLANT_2
            || id == BOLOGANO_SEEDLING
            || id == BOLOGANO_PLANT_1
            || id == BOLOGANO_PLANT_2
            || id == LOGAVANO_SEEDLING
            || id == LOGAVANO_PLANT_1
            || id == LOGAVANO_PLANT_2
        );
    }

    public static boolean isWatered(final TileObject patch) {
        final int id = patch.getId();
        return (
               id == GOLOVANOVA_SEEDLING_WATERED
            || id == GOLOVANOVA_PLANT_1_WATERED
            || id == GOLOVANOVA_PLANT_2_WATERED
            || id == BOLOGANO_SEEDLING_WATERED
            || id == BOLOGANO_PLANT_1_WATERED
            || id == BOLOGANO_PLANT_2_WATERED
            || id == LOGAVANO_SEEDLING_WATERED
            || id == LOGAVANO_PLANT_1_WATERED
            || id == LOGAVANO_PLANT_2_WATERED
        );
    }

    public static boolean isGrown(final TileObject patch) {
        final int id = patch.getId();
        return (
               id == GOLOVANOVA_GROWN
            || id == BOLOGANO_GROWN
            || id == LOGAVANO_GROWN
        );
    }

    public static boolean isBlighted(final TileObject patch) {
        final int id = patch.getId();
        return (
               id == GOLOVANOVA_SEEDLING_BLIGHTED
            || id == GOLOVANOVA_PLANT_1_BLIGHTED
            || id == GOLOVANOVA_PLANT_2_BLIGHTED
            || id == GOLOVANOVA_GROWN_BLIGHTED
            || id == BOLOGANO_SEEDLING_BLIGHTED
            || id == BOLOGANO_PLANT_1_BLIGHTED
            || id == BOLOGANO_PLANT_2_BLIGHTED
            || id == BOLOGANO_GROWN_BLIGHTED
            || id == LOGAVANO_SEEDLING_BLIGHTED
            || id == LOGAVANO_PLANT_1_BLIGHTED
            || id == LOGAVANO_PLANT_2_BLIGHTED
            || id == LOGAVANO_GROWN_BLIGHTED
        );
    }

    public static boolean isEmptyPatch(final TileObject patch) {
        return patch.getId() == TITHE_EMPTY_PATCH;
    }

    public static boolean isPatch(final TileObject patch) {
        return isDry(patch) || isWatered(patch) || isGrown(patch) || isBlighted(patch) || isEmptyPatch(patch);
    }
}
