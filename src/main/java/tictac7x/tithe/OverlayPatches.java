package tictac7x.tithe;

import net.runelite.api.DecorativeObject;
import net.runelite.api.GameObject;
import net.runelite.api.GroundObject;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.WallObject;
import net.runelite.client.ui.overlay.OverlayPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Optional;

import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class OverlayPatches extends OverlayPanel {
    private final Client client;
    private final TicTac7xTithePlugin plugin;
    private final TicTac7xTitheConfig config;

    public OverlayPatches(final TicTac7xTithePlugin plugin, final TicTac7xTitheConfig config, final Client client) {
        this.plugin = plugin;
        this.config = config;
        this.client = client;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        if (plugin.inTitheFarm()) {
            final MenuEntry[] menuEntries = client.getMenu().getMenuEntries();

            for (final MenuEntry menuEntry : menuEntries) {
                final MenuAction menuAction = menuEntry.getType();
                if (menuAction == MenuAction.CANCEL || menuAction == MenuAction.WALK) continue;

                final Optional<TileObject> object = findTileObject(menuEntry.getParam0(), menuEntry.getParam1(), menuEntry.getIdentifier());

                if (object.isPresent() && Plant.isPatch(object.get())) {
                    renderTile(graphics, object.get(), config.getPatchesHighlightOnHoverColor());
                    break;
                }
            }
        }

        return null;
    }

    private Optional<TileObject> findTileObject(final int x, final int y, final int id) {
        try {
            final Scene scene = client.getLocalPlayer().getWorldView().getScene();
            final Tile[][][] tiles = scene.getTiles();
            final Tile tile = tiles[client.getLocalPlayer().getWorldLocation().getPlane()][x][y];

            if (tile != null) {
                for (GameObject gameObject : tile.getGameObjects()) {
                    if (gameObject != null && gameObject.getId() == id) {
                        return Optional.of(gameObject);
                    }
                }

                final WallObject wallObject = tile.getWallObject();
                if (wallObject != null && wallObject.getId() == id) {
                    return Optional.of(wallObject);
                }

                final DecorativeObject decorativeObject = tile.getDecorativeObject();
                if (decorativeObject != null && decorativeObject.getId() == id) {
                    return Optional.of(decorativeObject);
                }

                final GroundObject groundObject = tile.getGroundObject();
                if (groundObject != null && groundObject.getId() == id) {
                    return Optional.of(groundObject);
                }
            }
        } catch (Exception ignored) {}

        return Optional.empty();
    }

    private void renderTile(final Graphics2D graphics, final TileObject object, final Color color) {
        if (color == null || color.getAlpha() == 0) return;

        try {
            final Shape shape = object.getCanvasTilePoly();

            // Area border.
            graphics.setColor(color.darker());
            graphics.setStroke(new BasicStroke(1));
            graphics.draw(shape);

            // Area fill.
            graphics.setColor(color);
            graphics.fill(shape);
        } catch (final Exception ignored) {}
    }
}
