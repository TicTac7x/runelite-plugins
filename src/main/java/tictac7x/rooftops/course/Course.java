package tictac7x.rooftops.course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Course {
    public final String id;
    public final int[] regions;
    public final Obstacle[] obstacles;
    public final MarkOfGrace[] marksOfGraces;

    private Optional<Obstacle> currentObstacle = Optional.empty();
    private boolean doingObstacle;

    public Course(
        final String id,
        final int[] regions,
        final Obstacle[] obstacles,
        final MarkOfGrace[] marksOfGraces
    ) {
        this.id = id;
        this.regions = regions;
        this.obstacles = obstacles;
        this.marksOfGraces = marksOfGraces;
    }

    public Optional<Obstacle> getCurrentObstacle() {
        return currentObstacle;
    }

    public Optional<List<Obstacle>> getNextObstacles() {
        // Course not started.
        if (!currentObstacle.isPresent()) {
            return Optional.of(new ArrayList<>(Arrays.asList(obstacles[0])));
        }

        final Optional<List<Integer>> fixedNextObstacles = currentObstacle.get().nextObstacles;
        if (fixedNextObstacles.isPresent()) {
            final List<Obstacle> next = new ArrayList<>();
            for (final Obstacle obstacle : obstacles) {
                if (fixedNextObstacles.get().contains(obstacle.id)) {
                    next.add(obstacle);
                }
            }
            return Optional.of(next);
        }

        int currentObstacleIndex = 0;
        for (final Obstacle obstacle : obstacles) {
            if (obstacle.id == currentObstacle.get().id) {
                break;
            }

            currentObstacleIndex++;
        }

        // Current obstacle is last.
        if (currentObstacleIndex < obstacles.length - 1) {
            return Optional.of(new ArrayList<>(Arrays.asList(obstacles[currentObstacleIndex + 1])));
        }

        return Optional.empty();
    }

    public void startObstacle(final Obstacle obstacle) {
        if (doingObstacle) return;

        currentObstacle = Optional.of(obstacle);
        doingObstacle = true;
    }

    public void completeObstacle() {
        doingObstacle = false;
    }

    public void completeCourse() {
        currentObstacle = Optional.empty();
        doingObstacle = false;
    }

    public boolean isDoingObstacle() {
        return doingObstacle;
    }

    public boolean isNearRegion(final int region) {
        for (final int courseRegion : this.regions) {
            if (courseRegion == region) {
                return true;
            }
        }

        return false;
    }

    public void clearObstaclesTileObjects() {
        for (final Obstacle obstacle : obstacles) {
            obstacle.clearTileObject();
        }
    }
}
