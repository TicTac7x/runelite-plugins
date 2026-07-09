package tictac7x.motherlode.oreveins;

import net.runelite.api.Actor;
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
import tictac7x.motherlode.ids.AnimationId;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.*;

import static tictac7x.motherlode.TicTac7xMotherlodePlugin.getWorldObjectKey;

public class OreVeins extends Overlay {
    private final TicTac7xMotherlodeConfig config;
    private final Character character;
    private final Motherlode motherlode;

    public final Map<String, OreVein> oreVeins = new HashMap<>();
    public final Set<WallObject> oreVeinsWallObjects = new HashSet<>();

    public OreVeins(final TicTac7xMotherlodeConfig config, final Character character, final Motherlode motherlode) {
        this.config = config;
        this.character = character;
        this.motherlode = motherlode;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

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

    private Optional<OreVein> getOreVeinFromWallObject(final WallObject wallObject) {
        return Optional.ofNullable(oreVeins.get(getWorldObjectKey(wallObject)));
    }

    @Override
    public Dimension render(final Graphics2D graphics2D) {
        for (final WallObject wallObject : oreVeinsWallObjects) {
            final Optional<OreVein> oreVein = getOreVeinFromWallObject(wallObject);
            if (oreVein.isEmpty() || !oreVein.get().isRendering(config, character)) continue;

            renderPie(graphics2D, wallObject, oreVein.get().getPieColor(config, motherlode), oreVein.get().getPieProgress());
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
            // Regular
            case AnimationId.BRONZE_PICKAXE:
            case AnimationId.IRON_PICKAXE:
            case AnimationId.STEEL_PICKAXE:
            case AnimationId.BLACK_PICKAXE:
            case AnimationId.MITHRIL_PICKAXE:
            case AnimationId.ADAMANT_PICKAXE:
            case AnimationId.RUNE_PICKAXE:
            case AnimationId.GILDED_PICKAXE:
            case AnimationId.DRAGON_PICKAXE:
            case AnimationId.THIRDAGE_PICKAXE:
            case AnimationId.CRYSTAL_PICKAXE:

            // Alternative variants
            case AnimationId.DRAGON_PICKAXE_UPGRADED:
            case AnimationId.DRAGON_PICKAXE_ZALCANO:
            case AnimationId.DRAGON_PICKAXE_TRAILBLAZER:
            case AnimationId.DRAGON_PICKAXE_TRAILBLAZER_RELOADED:
            case AnimationId.DRAGON_PICKAXE_INFERNAL:
            case AnimationId.DRAGON_PICKAXE_INFERNAL_TRAILBLAZER:
            case AnimationId.DRAGON_PICKAXE_INFERNAL_TRAILBLAZER_RELOADED:

            // League
            case AnimationId.LEAGUE_TRAILBLAZER_INFERNAL_PICKAXE:
                return true;

            default:
                return false;
        }
    }
}
