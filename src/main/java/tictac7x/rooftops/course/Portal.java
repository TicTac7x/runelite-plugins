package tictac7x.rooftops.course;
import net.runelite.api.Client;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Portal {
    public final int id;
    public final List<WorldPoint> locations;
    private Optional<TileObject> tileObject = Optional.empty();
    public Optional<List<Integer>> nextObstacles = Optional.empty();

    public Portal(final int id, final int plane, final int[][] locations) {
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
    
    public Portal nextObstacle(final int... ids) {
        nextObstacles = Optional.of(Arrays.stream(ids).boxed().collect(Collectors.toList()));
        return this;
    }
}