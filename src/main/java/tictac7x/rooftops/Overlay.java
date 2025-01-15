package tictac7x.rooftops;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.Tile;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import tictac7x.rooftops.course.CoursesManager;
import tictac7x.rooftops.course.Obstacle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;

public class Overlay extends net.runelite.client.ui.overlay.Overlay {
    private final Client client;
    private final TicTac7xRooftopsConfig config;
    private final CoursesManager coursesManager;

    public Overlay(final Client client, final TicTac7xRooftopsConfig config, final CoursesManager coursesManager) {
        this.client = client;
        this.config = config;
        this.coursesManager = coursesManager;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (!coursesManager.getCourse().isPresent()) return null;

        // Obstacles.
        for (final Obstacle obstacle : coursesManager.getCourse().get().obstacles) {
            if (obstacle.minLevel.isPresent() && getAgilityLevel() < obstacle.minLevel.get()) continue;
            if (obstacle.maxLevel.isPresent() && getAgilityLevel() > obstacle.maxLevel.get()) continue;

            final Color color =
                coursesManager.isStoppingObstacle(obstacle.id)
                    ? config.getObstacleStopColor()
                    : coursesManager.getCourse().get().getNextObstacles().isPresent() && coursesManager.getCourse().get().getNextObstacles().get().stream().anyMatch(o -> o.id == obstacle.id)
                        ? coursesManager.getCourse().get().isDoingObstacle()
                            ? config.getObstacleNextUnavailableColor()
                            : config.getObstacleNextColor()
                        : config.getObstacleUnavailableColor();

            if (obstacle.getTileObject().isPresent()) {
                renderShape(graphics, obstacle.getTileObject().get().getClickbox(), color);
            }
        }

        // Mark of graces.
        for (final Tile mark : coursesManager.getMarksOfGraces()) {
            renderShape(graphics, mark.getItemLayer().getCanvasTilePoly(), config.getMarkOfGraceColor());
        }

        return null;
    }

    private void renderShape(final Graphics2D graphics, final Shape shape, final Color color) {
        if (shape == null || color.getAlpha() == 0) return;

        try {
            // Area border.
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.min(255, color.getAlpha() + 20)));
            graphics.setStroke(new BasicStroke(1));
            graphics.draw(shape);

            // Area fill.
            graphics.setColor(color);
            graphics.fill(shape);
        } catch (final Exception ignored) {}
    }

    private int getAgilityLevel() {
        return client.getBoostedSkillLevel(Skill.AGILITY);
    }
}
