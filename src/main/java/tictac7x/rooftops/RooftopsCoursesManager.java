package tictac7x.rooftops;

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
import tictac7x.rooftops.courses.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class RooftopsCoursesManager {
    private final Client client;
    private final Course[] courses;

    private final Pattern regexLapComplete = Pattern.compile(".*lap count is:.*");

    private final List<Tile> marksOfGraces = new ArrayList<>();
    private final List<Integer> menuOptionsClicked = new ArrayList<>();
    private Optional<Course> course = Optional.empty();

    public RooftopsCoursesManager(final Client client, final Course[] courses) {
        this.client = client;
        this.courses = courses;
    }

    public void onTileObjectSpawned(final TileObject tileObject) {
        if (detectCourse() && course.isPresent()) {
            for (final Obstacle obstacle : course.get().obstacles) {
                obstacle.checkAndSetTileObject(tileObject);
            }
        }
    }

    public void onGameStateChanged(final GameStateChanged event) {
        // Clear previous obstacles objects (since they will spawn again).
        if (event.getGameState() == GameState.LOADING) {
            if (course.isPresent()) course.get().clearObstaclesTileObjects();
            marksOfGraces.clear();
            course = Optional.empty();
        }
    }

    public void onStatChanged(final StatChanged event) {
        if (course.isPresent() && event.getSkill() == Skill.AGILITY) {
            completeObstacle();
            menuOptionsClicked.clear();
        }
    }

    public void onHitsplatApplied(final HitsplatApplied event) {
        if (course.isPresent() && event.getActor() == client.getLocalPlayer()) {
            completeCourse();
        }
    }

    public void onGameTick(final GameTick ignored) {
        checkStartObstacle();
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
        if (!course.isPresent()) return false;

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

    private void completeObstacle() {
        if (!course.isPresent()) return;

        final Optional<Obstacle> currentObstacle = course.get().getCurrentObstacle();
        if (
            currentObstacle.isPresent() &&
            currentObstacle.get().completeAt.isPresent() &&
            (client.getLocalPlayer().getWorldLocation().getX() != currentObstacle.get().completeAt.get()[0] || client.getLocalPlayer().getWorldLocation().getY() != currentObstacle.get().completeAt.get()[1])
        ) {
            return;
        }

        course.get().completeObstacle();
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
            course.get().completeCourse();
        }
    }

    public void onMenuOptionClicked(final MenuOptionClicked event) {
        menuOptionsClicked.add(event.getId());
    }
}
