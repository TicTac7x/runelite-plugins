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
    @Nullable private Course course;
    private int lastMenuOptionClickedId;

    public RooftopsCoursesManager(final Client client, final Course[] courses) {
        this.client = client;
        this.courses = courses;
    }

    public void onTileObjectSpawned(final TileObject tileObject) {
        detectCourse();
        if (course == null) return;

        for (final Obstacle obstacle : course.obstacles) {
            obstacle.checkAndSetTileObject(tileObject);
        }
    }

    public void onGameStateChanged(final GameStateChanged event) {
        // Clear previous obstacles objects (since they will spawn again).
        if (event.getGameState() == GameState.LOADING) {
            if (course != null) {
                course.clearObstaclesTileObjects();
            }
            marksOfGraces.clear();
            course = null;
        }
    }

    public void onStatChanged(final StatChanged event) {
        if (course == null || event.getSkill() != Skill.AGILITY) return;
        completeObstacle();
    }

    public void onHitsplatApplied(final HitsplatApplied event) {
        if (course == null || event.getActor() != client.getLocalPlayer()) return;
        completeCourse();
    }

    public void onGameTick(final GameTick ignored) {
        checkStartObstacle();
    }

    public void onChatMessage(final ChatMessage event) {
        if (course == null || event.getType() != ChatMessageType.GAMEMESSAGE || !regexLapComplete.matcher(event.getMessage()).find()) return;
        completeCourse();
    }

    public void onItemSpawned(final ItemSpawned event) {
        if (event.getItem().getId() != ItemID.MARK_OF_GRACE) return;
        marksOfGraces.add(event.getTile());
    }

    public void onItemDespawned(final ItemDespawned event) {
        if (event.getItem().getId() != ItemID.MARK_OF_GRACE) return;
        marksOfGraces.remove(event.getTile());
    }

    @Nullable
    public Course getCourse() {
        return course;
    }

    public List<Tile> getMarksOfGraces() {
        return marksOfGraces;
    }

    public boolean isStoppingObstacle(final int obstacle_id) {
        if (course == null) return false;

        for (final Tile tile : marksOfGraces) {
            for (final MarkOfGrace mark : course.marksOfGraces) {
                if (mark.obstacle == obstacle_id && mark.x == tile.getWorldLocation().getX() && mark.y == tile.getWorldLocation().getY()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isNearNextObstacle() {
        if (course == null) return false;

        final Optional<List<Obstacle>> nextObstacles = course.getNextObstacles();
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
        if (course == null) return;
        course.startObstacle(obstacle);
        lastMenuOptionClickedId = -1;
    }

    private void completeObstacle() {
        if (this.course == null) return;

        final Optional<Obstacle> currentObstacle = course.getCurrentObstacle();
        if (
            currentObstacle.isPresent() &&
            currentObstacle.get().completeAt.isPresent() &&
            (client.getLocalPlayer().getWorldLocation().getX() != currentObstacle.get().completeAt.get()[0] || client.getLocalPlayer().getWorldLocation().getY() != currentObstacle.get().completeAt.get()[1])
        ) {
            return;
        }

        course.completeObstacle();
    }

    private void detectCourse() {
        if (course != null || client.getLocalPlayer() == null || client.getLocalPlayer().getWorldLocation() == null) return;

        for (final Course course : courses) {
            if (course.isNearRegion(client.getLocalPlayer().getWorldLocation().getRegionID())) {
                if (course == this.course) return;

                // New course found, complete previous.
                if (this.course != null) {
                    completeCourse();
                }

                this.course = course;
                return;
            }
        }

        this.course = null;
    }

    private void checkStartObstacle() {
        if (course == null || course.isDoingObstacle() || !isNearNextObstacle()) return;

        final Optional<List<Obstacle>> nextObstacles = course.getNextObstacles();
        if (!nextObstacles.isPresent()) return;

        // Start obstacle.
        for (final Obstacle nextObstacle : nextObstacles.get()) {
            if (nextObstacle.id == lastMenuOptionClickedId) {
                startObstacle(nextObstacle);
                return;
            }
        }
    }

    private void completeCourse() {
        if (course == null) return;
        course.completeCourse();
    }

    public void onMenuOptionClicked(final MenuOptionClicked event) {
        lastMenuOptionClickedId = event.getId();
    }
}
