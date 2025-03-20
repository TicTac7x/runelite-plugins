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
    private boolean isDoingObstacle = false;

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
        final List<Obstacle> nextObstacles = new ArrayList<>();

        // Course not started.
        if (!currentObstacle.isPresent()) {
            nextObstacles.add(obstacles[0]);

        // Get next obstacles based on ids and not order.
        } else if (currentObstacle.get().nextObstacles.isPresent()) {
            for (final Obstacle obstacle : obstacles) {
                if (currentObstacle.get().nextObstacles.get().contains(obstacle.id)) {
                    nextObstacles.add(obstacle);
                }
            }

        // Find next obstacle index based on order.
        } else {
            int currentObstacleIndex = 0;
            for (final Obstacle obstacle : obstacles) {
                if (obstacle.id == currentObstacle.get().id) {
                    break;
                }

                currentObstacleIndex++;
            }

            // Current obstacle is last.
            if (currentObstacleIndex ==  obstacles.length - 1) {
                return Optional.empty();
            }

            // Next obstacle based on order.
            nextObstacles.add(obstacles[currentObstacleIndex + 1]);
        }

        return Optional.of(nextObstacles);
    }

    public void startObstacle(final Obstacle obstacle) {
        if (isDoingObstacle) return;

        currentObstacle = Optional.of(obstacle);
        isDoingObstacle = true;
    }

    public void completeObstacle(final List<Integer> menuOptionsClicked) {
        isDoingObstacle = false;

        // If possible, complete current obstacle.
        if (currentObstacle.isPresent() && menuOptionsClicked.contains(currentObstacle.get().id)) {
            for (int i = 0; i < obstacles.length; i++) {
                if (i != obstacles.length - 1) continue;

                final Obstacle obstacle = obstacles[i];
                if (obstacle.id == currentObstacle.get().id) {
                    completeCourse(menuOptionsClicked);
                }
            }

        // If for some reason we failed to mark obstacle as current, try to find one based on id and complete that instead.
        } else {
            for (int i = menuOptionsClicked.size() - 1; i >= 0; i--) {
                for (final Obstacle obstacle : obstacles) {
                    if (obstacle.id == menuOptionsClicked.get(i)) {
                        currentObstacle = Optional.of(obstacle);
                        completeObstacle(menuOptionsClicked);
                        return;
                    }
                }
            }
        }
    }

    public void completeCourse(final List<Integer> menuOptionsClicked) {
        menuOptionsClicked.clear();
        currentObstacle = Optional.empty();
        isDoingObstacle = false;
    }

    public boolean isDoingObstacle() {
        return isDoingObstacle;
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
