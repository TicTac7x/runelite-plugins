package tictac7x.motherlode.oreveins;

import net.runelite.api.Actor;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.Player;
import net.runelite.api.WallObject;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ProgressPieComponent;
import tictac7x.motherlode.Motherlode;
import tictac7x.motherlode.Character;
import tictac7x.motherlode.Provider;
import tictac7x.motherlode.TicTac7xMotherlodeConfig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.*;

import static tictac7x.motherlode.TicTac7xMotherlodePlugin.getWorldObjectKey;

public class OreVeins extends Overlay {
    private final TicTac7xMotherlodeConfig config;
    private final Character character;
    private final Motherlode motherlode;
    private final Provider provider;

    public final Map<String, OreVein> oreVeins = new HashMap<>();
    public final Set<WallObject> oreVeinsWallObjects = new HashSet<>();

    public OreVeins(final TicTac7xMotherlodeConfig config, final Character character, final Motherlode motherlode, final Provider provider) {
        this.config = config;
        this.character = character;
        this.motherlode = motherlode;
        this.provider = provider;

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

    public void onGameTick() {
        for (final OreVein oreVein : oreVeins.values()) {
            oreVein.onGameTick();
        }
    }

    public void onMenuEntryAdded(final MenuEntryAdded event) {
        if (!config.removeMineWhenFull() || !isOreVeinMineEntry(event.getMenuEntry()) || !motherlode.shouldStopMining()) return;

        final List<MenuEntry> menuEntries = new ArrayList<>();

        for (final MenuEntry menuEntry : provider.client.getMenu().getMenuEntries()) {
            if (!isOreVeinMineEntry(menuEntry)) {
                menuEntries.add(menuEntry);
            }
        }

        provider.client.getMenu().setMenuEntries(menuEntries.toArray(new MenuEntry[0]));
    }

    private boolean isOreVeinMineEntry(final MenuEntry menuEntry) {
        return menuEntry.getType() == MenuAction.GAME_OBJECT_FIRST_OPTION && OreVein.isOreVein(menuEntry.getIdentifier());
    }

    private void updateOreVein(final WallObject wallObject, final boolean isDepleted) {
        final String key = getWorldObjectKey(wallObject);

        if (oreVeins.containsKey(key)) {
            oreVeins.get(key).setDepleted(isDepleted);
        } else {
            oreVeins.put(key, new OreVein(
                wallObject.getWorldLocation().getX(),
                wallObject.getWorldLocation().getY(),
                isDepleted,
                provider
            ));
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
}
