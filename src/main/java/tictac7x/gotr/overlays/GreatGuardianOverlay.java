package tictac7x.gotr.overlays;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import tictac7x.gotr.store.Inventory;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class GreatGuardianOverlay extends Overlay {
    private final Client client;
    private final ModelOutlineRenderer modelOutlineRenderer;
    private final TicTac7xGotrImprovedConfig config;
    private final Inventory inventory;

    private final BufferedImage imageElementalGuardianStones;
    private final BufferedImage imageCatalyticGuardianStones;
    private final BufferedImage imagePolyelementalGuardianStones;

    private Optional<NPC> greatGuardian = Optional.empty();

    public GreatGuardianOverlay(final Client client, final ItemManager itemManager, final ModelOutlineRenderer modelOutlineRenderer, final TicTac7xGotrImprovedConfig config, final Inventory inventory) {
        this.client = client;
        this.modelOutlineRenderer = modelOutlineRenderer;
        this.config = config;
        this.inventory = inventory;

        this.imageElementalGuardianStones = itemManager.getImage(ItemID.ELEMENTAL_GUARDIAN_STONE);
        this.imageCatalyticGuardianStones = itemManager.getImage(ItemID.CATALYTIC_GUARDIAN_STONE);
        this.imagePolyelementalGuardianStones = itemManager.getImage(ItemID.POLYELEMENTAL_GUARDIAN_STONE);

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    public void onNpcSpawned(final NpcSpawned event) {
        if (event.getNpc().getId() == NpcID.THE_GREAT_GUARDIAN) {
            greatGuardian = Optional.of(event.getNpc());
        }
    }

    public void onNpcDespawned(final NPC npc) {
        if (npc.getId() == NpcID.THE_GREAT_GUARDIAN) {
            greatGuardian = Optional.empty();
        }
    }

    public void onGameStateChanged(final GameState game_state) {
        if (game_state == GameState.LOADING) {
            greatGuardian = Optional.empty();
        }
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        // Great guardian highlighting disabled.
        if (!config.highlightGreatGuardian()) return null;

        // Great guardian NPC object not available.
        if (!greatGuardian.isPresent()) return null;

        // No essence in inventory.
        if (!inventory.hasGuardianStones()) return null;

        // Outline.
        try {
            final Color outlineColor =
                inventory.hasElementalGuardianStones() ? config.getElementalColor() :
                inventory.hasCatalyticGuardianStones() ? config.getCatalyticColor() :
                config.getPolyelementalColor();
            modelOutlineRenderer.drawOutline(greatGuardian.get(), 2, outlineColor, 2);
        } catch (final Exception ignored) {}

        // Image.
        try {
            final BufferedImage stoneImage =
                inventory.hasElementalGuardianStones() ? imageElementalGuardianStones :
                inventory.hasCatalyticGuardianStones() ? imageCatalyticGuardianStones :
                imagePolyelementalGuardianStones;
            OverlayUtil.renderImageLocation(client, graphics, greatGuardian.get().getLocalLocation(), stoneImage, 550);
        } catch (final Exception ignored) {}

        return null;
    }
}
