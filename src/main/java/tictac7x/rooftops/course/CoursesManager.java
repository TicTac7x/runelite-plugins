package tictac7x.rooftops.course;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.StatChanged;
import tictac7x.rooftops.TicTac7xRooftopsConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class CoursesManager {
    private final Client client;
    private final TicTac7xRooftopsConfig config;
    private final Course[] courses;

    private final Pattern regexLapComplete = Pattern.compile(".*lap count is:.*");

    private final List<Tile> marksOfGraces = new ArrayList<>();
    private final List<TileObject> portalObjects = new ArrayList<>();
    private final List<Integer> menuOptionsClicked = new ArrayList<>();
    private Optional<Course> course = Optional.empty();

    public CoursesManager(final Client client, final TicTac7xRooftopsConfig config, final Course[] courses) {
        this.client = client;
        this.config = config;
        this.courses = courses;
    }

    public void onTileObjectSpawned(final TileObject tileObject) {
        if (detectCourse() && course.isPresent()) {
            for (final Obstacle obstacle : course.get().obstacles) {
                obstacle.checkAndSetTileObject(tileObject);
            }
        }
        // Check portals
        for (final Portal portal : course.get().portals) {
            portal.checkAndSetTileObject(tileObject);
       }
    }

    public void onGameStateChanged(final GameStateChanged event) {
        // Clear previous obstacles objects (since they will spawn again).
        if (event.getGameState() == GameState.LOADING) {
            if (course.isPresent()) {
                course.get().clearObstaclesTileObjects();
                course.get().clearPortalTileObjects();
            }
            marksOfGraces.clear();
            portalObjects.clear();
            course = Optional.empty();
        }
    }

    public void onStatChanged(final StatChanged event) {
        if (course.isPresent() && event.getSkill() == Skill.AGILITY) {
            completeObstacle(menuOptionsClicked);
        }
    }

    public void onHitsplatApplied(final HitsplatApplied event) {
        if (course.isPresent() && event.getActor() == client.getLocalPlayer()) {
            completeCourse();
        }
    }

    public void onGameTick(final GameTick ignored) {
        checkStartObstacle();

        // Check if player completed an obstacle by position (for obstacles that don't give XP)
        if (course.isPresent() && course.get().getCurrentObstacle().isPresent() && course.get().isDoingObstacle()) {
            Obstacle currentObstacle = course.get().getCurrentObstacle().get();
            if (currentObstacle.completeAt.isPresent()) {
                int[] completeAt = currentObstacle.completeAt.get();
                WorldPoint playerLocation = client.getLocalPlayer().getWorldLocation();
                
                if (playerLocation.getX() == completeAt[0] && playerLocation.getY() == completeAt[1]) {
                    completeObstacle(menuOptionsClicked);
                }
            }
        }
    }

    public void onChatMessage(final ChatMessage event) {
        if (
            course.isPresent() &&
            event.getType() == ChatMessageType.GAMEMESSAGE &&
            regexLapComplete.matcher(event.getMessage()).find()
        ) {
            completeCourse();
        }
    }

    public void onItemSpawned(final ItemSpawned event) {
        if (event.getItem().getId() == ItemID.MARK_OF_GRACE) {
            marksOfGraces.add(event.getTile());
        }
    }

    public void onItemDespawned(final ItemDespawned event) {
        if (event.getItem().getId() == ItemID.MARK_OF_GRACE) {
            marksOfGraces.remove(event.getTile());
        }
    }

    public Optional<Course> getCourse() {
        return course;
    }

    public List<Tile> getMarksOfGraces() {
        return marksOfGraces;
    }

    public boolean isStoppingObstacle(final int obstacleId) {
        if (!course.isPresent() || !config.showMarkOfGraceStop()) return false;

        for (final Tile tile : marksOfGraces) {
            for (final MarkOfGrace mark : course.get().marksOfGraces) {
                if (
                    mark.obstacle == obstacleId &&
                    mark.x == tile.getWorldLocation().getX() &&
                    mark.y == tile.getWorldLocation().getY()
                ) {
                    return true;
                }
            }
        }

        // Check for portals near the player
        if (config.showPortalStops()) {
            Player player = client.getLocalPlayer();
            if (player != null) {
                // Find the current obstacle
                Obstacle currentObstacle = null;
                for (Obstacle obstacle : course.get().obstacles) {
                    if (obstacle.id == obstacleId) {
                        currentObstacle = obstacle;
                        break;
                    }
                }
                
                // If we found the obstacle and player is close to a portal
                if (currentObstacle != null) {
                    Optional<Portal> nearbyPortal = course.get().findNearbyPortal(player.getWorldLocation(), 6);

                    if (nearbyPortal.isPresent()&& nearbyPortal.get().getTileObject().get().getClickbox()!=null) {
                        return true;
                    }
                }
            }
        }
        

        return false;
    }

    private boolean isNearNextObstacle() {
        if (!course.isPresent()) return false;

        final Optional<List<Obstacle>> nextObstacles = course.get().getNextObstacles();
        if (!nextObstacles.isPresent()) return false;

        final Player player = client.getLocalPlayer();
        for (final Obstacle nextObstacle : nextObstacles.get()) {
            for (final WorldPoint obstacle_point : nextObstacle.locations) {
                if (player.getWorldLocation().distanceTo(obstacle_point) <= 1) {
                    return true;
                }
            }
        }

        return false;
    }

    private void startObstacle(final Obstacle obstacle) {
        if (course.isPresent()) {
            course.get().startObstacle(obstacle);
        }
    }

    private void completeObstacle(final List<Integer> menuOptionsClicked) {
        if (!course.isPresent()) return;

        final Optional<Obstacle> currentObstacle = course.get().getCurrentObstacle();

        if (
            currentObstacle.isPresent() &&
            currentObstacle.get().completeAt.isPresent() && (
                client.getLocalPlayer().getWorldLocation().getX() != currentObstacle.get().completeAt.get()[0] ||
                client.getLocalPlayer().getWorldLocation().getY() != currentObstacle.get().completeAt.get()[1]
        )) {
            return;
        }

        course.get().completeObstacle(menuOptionsClicked);
    }

    private boolean detectCourse() {
        if (client.getLocalPlayer() == null || client.getLocalPlayer().getWorldLocation() == null) return false;

        for (final Course course : courses) {
            if (course.isNearRegion(client.getLocalPlayer().getWorldLocation().getRegionID())) {
                if (this.course.isPresent() && course == this.course.get()) return true;

                // New course found, complete previous.
                completeCourse();
                this.course = Optional.of(course);
                return true;
            }
        }

        this.course = Optional.empty();
        return false;
    }

    private void checkStartObstacle() {
        if (!course.isPresent() || course.get().isDoingObstacle() || !isNearNextObstacle()) return;

        final Optional<List<Obstacle>> nextObstacles = course.get().getNextObstacles();
        if (!nextObstacles.isPresent()) return;

        // Start obstacle.
        for (final Obstacle nextObstacle : nextObstacles.get()) {
            if (menuOptionsClicked.contains(nextObstacle.id)) {
                startObstacle(nextObstacle);
            }
        }
    }

    private void completeCourse() {
        if (course.isPresent()) {
            course.get().completeCourse(menuOptionsClicked);
        }
    }

    public void onMenuOptionClicked(final MenuOptionClicked event) {
        if (course.isPresent()) {
            // Check if player clicked on a portal
            for (final Portal portal : course.get().portals) {
                if (portal.getTileObject().isPresent() && 
                    event.getId() == portal.id) {
                    // Player clicked a portal, mark it as used
                    if (portal.nextObstacles.isPresent() && !portal.nextObstacles.get().isEmpty()) {
                        int nextObstacleId = portal.nextObstacles.get().get(0);
                        // Add the target obstacle ID to menuOptionsClicked
                        menuOptionsClicked.add(nextObstacleId);
                    }
                    course.get().usePortal(portal.id);
                    return;
                }
            }
            
            // Check if player clicked on an obstacle
            for (final Obstacle obstacle : course.get().obstacles) {
                if (event.getId() == obstacle.id) {
                    menuOptionsClicked.add(event.getId());
                }
            }
        }
    }
}
