package tictac7x.motherlode.oreveins;

import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.WallObject;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.api.gameval.AnimationID;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ProgressPieComponent;
import tictac7x.motherlode.Motherlode;
import tictac7x.motherlode.Character;
import tictac7x.motherlode.TicTac7xMotherlodeConfig;

import javax.annotation.Nullable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static tictac7x.motherlode.TicTac7xMotherlodePlugin.getWorldObjectKey;

public class OreVeins extends Overlay {
    private final TicTac7xMotherlodeConfig config;
    private final Character character;
    private final Motherlode motherlode;

    public OreVeins(final TicTac7xMotherlodeConfig config, final Character character, final Motherlode motherlode) {
        this.config = config;
        this.character = character;
        this.motherlode = motherlode;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    public Map<String, OreVein> oreVeins = new HashMap<>();
    public Set<WallObject> oreVeinsWallObjects = new HashSet<>();

    public void onWallObjectSpawned(final WallObjectSpawned event) {
        final WallObject wallObject = event.getWallObject();
        final boolean isOreVein = OreVein.isOreVein(wallObject);
        final boolean isDepletedOreVein = OreVein.isDepletedOreVein(wallObject);
        if (!isOreVein && !isDepletedOreVein) return;

        updateOreVein(wallObject, isDepletedOreVein);
        oreVeinsWallObjects.add(event.getWallObject());
    }

    public void onWallObjectDespawned(final WallObjectDespawned event) {
        final WallObject wallObject = event.getWallObject();
        final boolean isOreVein = OreVein.isOreVein(wallObject);
        final boolean isDepletedOreVein = OreVein.isDepletedOreVein(wallObject);
        if (!isOreVein && !isDepletedOreVein) return;

        updateOreVein(wallObject, isDepletedOreVein);
        oreVeinsWallObjects.remove(event.getWallObject());
    }

    public void onGameStateChanged(final GameStateChanged event) {
        if (event.getGameState() == GameState.LOADING) {
            oreVeinsWallObjects.clear();
        }
    }

    public void onAnimationChanged(final AnimationChanged event) {
        setOreVeinMinedFromAnimation(event);
    }

    public void onGameTick() {
        for (final OreVein oreVein : oreVeins.values()) {
            oreVein.onGameTick();
        }
    }

    private void updateOreVein(final WallObject wallObject, final boolean isDepleted) {
        final String key = getWorldObjectKey(wallObject);

        if (oreVeins.containsKey(key)) {
            oreVeins.get(key).setDepleted(isDepleted);
        } else {
            oreVeins.put(key, new OreVein(
                wallObject.getWorldLocation().getX(),
                wallObject.getWorldLocation().getY(),
                isDepleted
            ));
        }
    }

    private void setOreVeinMinedFromAnimation(final AnimationChanged event) {
        final Actor player = event.getActor();
        if (!isMiningAnimation(event.getActor().getAnimation())) return;

        final int playerX = player.getWorldLocation().getX();
        final int playerY = player.getWorldLocation().getY();
        final int playerOrientation = player.getOrientation();

        // Find correct ore vein based on actor orientation when mining.
        for (final OreVein oreVein : oreVeins.values()) {
            if (
                // Facing south.
                playerOrientation == 0 && playerX == oreVein.x && playerY == oreVein.y + 1 ||
                // Facing west.
                playerOrientation == 512 && playerX == oreVein.x + 1 && playerY == oreVein.y ||
                // Facing north.
                playerOrientation == 1024 && playerX == oreVein.x && playerY == oreVein.y - 1 ||
                // Facing east.
                playerOrientation == 1536 && playerX == oreVein.x - 1 && playerY == oreVein.y
            ) {
                oreVein.startDepleting();
            }
        }
    }

    @Nullable
    private OreVein getOreVeinFromWallObject(final WallObject wallObject) {
        return oreVeins.getOrDefault(getWorldObjectKey(wallObject), null);
    }

    @Override
    public Dimension render(final Graphics2D graphics2D) {
        for (final WallObject wallObject : oreVeinsWallObjects) {
            final OreVein oreVein = getOreVeinFromWallObject(wallObject);
            if (oreVein == null || !oreVein.isRendering(config, character)) continue;

            renderPie(graphics2D, wallObject, oreVein.getPieColor(config, motherlode), oreVein.getPieProgress());
        }

        return null;
    }

    private void renderPie(final Graphics2D graphics, final WallObject object, final Color color, final float progress) {
        try {
            final ProgressPieComponent progressPieComponentBackground = new ProgressPieComponent();
            progressPieComponentBackground.setPosition(object.getCanvasLocation(160));
            progressPieComponentBackground.setProgress(1);
            progressPieComponentBackground.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(color.getAlpha() - 100, 0)));
            progressPieComponentBackground.render(graphics);

            final ProgressPieComponent progressPieComponentTimer = new ProgressPieComponent();
            progressPieComponentTimer.setPosition(object.getCanvasLocation(160));
            progressPieComponentTimer.setProgress(progress);
            progressPieComponentTimer.setBorderColor(color);
            progressPieComponentTimer.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(color.getAlpha() - 20, 0)));
            progressPieComponentTimer.render(graphics);
        } catch (final Exception ignored) {}
    }

    private boolean isMiningAnimation(final int animationId) {
        switch (animationId) {
            case AnimationID.HUMAN_MINING_BRONZE_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_IRON_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_STEEL_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_BLACK_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_MITHRIL_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_ADAMANT_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_RUNE_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_DRAGON_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_ZALCANO_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_DRAGON_PICKAXE_PRETTY_WALL:
            case AnimationID.HUMAN_MINING_TRAILBLAZER_PICKAXE_NO_INFERNAL_WALL:
            case AnimationID.HUMAN_MINING_3A_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_CRYSTAL_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_INFERNAL_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_GILDED_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_LEAGUE_TRAILBLAZER_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_TRAILBLAZER_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_TRAILBLAZER_RELOADED_PICKAXE_WALL:
            case AnimationID.HUMAN_MINING_TRAILBLAZER_RELOADED_PICKAXE_NO_INFERNAL_WALL:
                return true;
            default:
                return false;
        }
    }
}
