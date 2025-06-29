package tictac7x.rooftops.course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import net.runelite.api.coords.WorldPoint;  // Add this import

public abstract class Course {
    public final String id;
    public final int[] regions;
    public final Obstacle[] obstacles;
    public final MarkOfGrace[] marksOfGraces;
    public final Portal[] portals;

    private Optional<Obstacle> currentObstacle = Optional.empty();
    private boolean isDoingObstacle = false;
    private boolean justUsedPortal = false;

    
    // Constructor for courses with marks of grace
    public Course(
        final String id,
        final int[] regions,
        final Obstacle[] obstacles,
        final MarkOfGrace[] marksOfGraces
    ) {
        this(id, regions, obstacles, marksOfGraces, new Portal[0]);
    }

    // Constructor for courses with portals (like Prifddinas)
    public Course(
        final String id,
        final int[] regions,
        final Obstacle[] obstacles,
        final Portal[] portals
    ) {
        this(id, regions, obstacles, new MarkOfGrace[0], portals);
    }

    // Full constructor
    public Course(
        final String id,
        final int[] regions,
        final Obstacle[] obstacles,
        final MarkOfGrace[] marksOfGraces,
        final Portal[] portals
    ) {
        this.id = id;
        this.regions = regions;
        this.obstacles = obstacles;
        this.marksOfGraces = marksOfGraces;
        this.portals = portals;
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
        }
        
        // Check if player just used a portal
        else if (justUsedPortal) {
            // Find current obstacle index
            int currentObstacleIndex = 0;
            for (final Obstacle obstacle : obstacles) {
                if (obstacle.id == currentObstacle.get().id) {
                    break;
                }
                currentObstacleIndex++;
            }

            // Skip one obstacle (if possible)
            if (currentObstacleIndex + 2 < obstacles.length) {
                nextObstacles.add(obstacles[currentObstacleIndex + 2]);
            } else if (currentObstacleIndex + 1 < obstacles.length) {
                // If we can't skip two ahead, just use the next one
                nextObstacles.add(obstacles[currentObstacleIndex + 1]);
            }
            
            // Reset portal usage flag
            justUsedPortal = false;
        }

        // Find next obstacle index based on order.
         else {
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
    public void clearPortalTileObjects() {
        for (final Portal portal : portals) {
            portal.clearTileObject();
        }
    }

    /**
     * Call this method when a player uses a portal
     * @param portalId The ID of the portal that was used
     * @return true if a valid portal was used, false otherwise
     */
    public boolean usePortal(int portalId) {
        // Find the portal by ID
        for (Portal portal : portals) {
            if (portal.id == portalId && portal.getTileObject().isPresent()) {
                // If the portal specifies which obstacle to go to next, use that
                if (portal.nextObstacles.isPresent() && !portal.nextObstacles.get().isEmpty()) {
                    int nextObstacleId = portal.nextObstacles.get().get(0);
                    
                    // Find the obstacle with this ID and set it as current
                    for (Obstacle obstacle : obstacles) {
                        if (obstacle.id == nextObstacleId) {
                            currentObstacle = Optional.of(obstacle);
                            isDoingObstacle = false;
                            
                            // Clear the portal after use to prevent reuse
                            portal.clearTileObject();
                            obstacle.clearTileObject();
                            return true;
                        }
                    }
                }
                
                // Fallback to original behavior if no next obstacle is specified
                justUsedPortal = true;
                if (currentObstacle.isPresent()) {
                    int currentIndex = -1;
                    for (int i = 0; i < obstacles.length; i++) {
                        if (obstacles[i].id == currentObstacle.get().id) {
                            currentIndex = i;
                            break;
                        }
                    }
                    
                    if (currentIndex >= 0 && currentIndex + 2 < obstacles.length) {
                        currentObstacle = Optional.of(obstacles[currentIndex + 2]);
                    } else if (currentIndex >= 0 && currentIndex + 1 < obstacles.length) {
                        currentObstacle = Optional.of(obstacles[currentIndex + 1]);
                    }
                    isDoingObstacle = false;
                }
                
                // Clear the portal after use to prevent reuse
                portal.clearTileObject();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any portal is visible and close to the player
     * @param playerLocation The player's current location
     * @param maxDistance Maximum distance to consider a portal as close
     * @return The portal that's close to the player, or empty if none are close
     */
    public Optional<Portal> findNearbyPortal(WorldPoint playerLocation, int maxDistance) {
        if (playerLocation == null) return Optional.empty();
        
        for (Portal portal : portals) {
            if (portal.getTileObject().isPresent()) {
                for (WorldPoint portalLocation : portal.locations) {
                    if (playerLocation.distanceTo(portalLocation) <= maxDistance) {
                        return Optional.of(portal);
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Resets the portal usage state
     */
    public void clearPortalUsage() {
        justUsedPortal = false;
    }
}
