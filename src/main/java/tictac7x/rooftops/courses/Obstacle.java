package tictac7x.rooftops.courses;

import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Obstacle {
    public final int id;
    public final List<WorldPoint> locations;
    private Optional<TileObject> tileObject = Optional.empty();

    public Optional<int[]> completeAt = Optional.empty();
    public Optional<List<Integer>> nextObstacles = Optional.empty();
    public Optional<Integer> minLevel = Optional.empty();
    public Optional<Integer> maxLevel = Optional.empty();

    public Obstacle(final int id, final int plane, final int[][] locations) {
        this.id = id;
        this.locations = new ArrayList<>();
        for (final int[] location : locations) {
            this.locations.add(new WorldPoint(location[0], location[1], plane));
        }
    }

    public void checkAndSetTileObject(final TileObject tileObject) {
        if (tileObject.getId() == id) {
            this.tileObject = Optional.of(tileObject);
        }
    }

    public void clearTileObject() {
        tileObject = Optional.empty();
    }

    public Optional<TileObject> getTileObject() {
        return tileObject;
    }

    public Obstacle completeAt(final int x, final int y) {
        completeAt = Optional.of(new int[]{x, y});
        return this;
    }

    public Obstacle nextObstacle(final int... ids) {
        nextObstacles = Optional.of(Arrays.stream(ids).boxed().collect(Collectors.toList()));
        return this;
    }

    public Obstacle minLevel(final int level) {
        this.minLevel = Optional.of(level);
        return this;
    }

    public Obstacle maxLevel(final int level) {
        this.maxLevel = Optional.of(level);
        return this;
    }
}
