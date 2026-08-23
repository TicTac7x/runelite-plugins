package tictac7x.motherlode.oreveins;

import net.runelite.api.Player;
import net.runelite.api.WallObject;
import tictac7x.motherlode.Motherlode;
import tictac7x.motherlode.Provider;
import tictac7x.motherlode.ids.AnimationId;
import tictac7x.motherlode.ids.WallObjectId;
import tictac7x.motherlode.sectors.Sectors;
import tictac7x.motherlode.Character;
import tictac7x.motherlode.sectors.Sector;
import tictac7x.motherlode.TicTac7xMotherlodeConfig;
import java.awt.Color;

public class OreVein {
    public final int x;
    public final int y;
    public final Sector sector;

    private boolean isDepleted;
    private boolean isDepleting;
    private int regenerationTicks = 0;
    private float health;
    private final int RESPAWN_TIME_GAMETICKS = 100;
    private final int DESPAWN_TIME_DOWNSTAIRS_GAMETICKS = 45;
    private final int DESPAWN_TIME_UPPERFLOOR_GAMETICKS = 67;
    private final Provider provider;

    private static final int[] ORE_VEINS_IDS = new int[]{WallObjectId.ORE_VEIN_ONE, WallObjectId.ORE_VEIN_TWO, WallObjectId.ORE_VEIN_THREE, WallObjectId.ORE_VEIN_FOUR};
    private static final int[] DEPLETED_ORE_VEINS_IDS = new int[]{ WallObjectId.ORE_VEIN_ONE_DEPLETED, WallObjectId.ORE_VEIN_TWO_DEPLETED, WallObjectId.ORE_VEIN_THREE_DEPLETED, WallObjectId.ORE_VEIN_FOUR_DEPLETED };

    public OreVein(final int x, final int y, final boolean isDepleted, final Provider provider) {
        this.x = x;
        this.y = y;
        this.isDepleted = isDepleted;
        this.sector = Sectors.getSectors(x, y, false).get(0);
        this.health = getMaxHealth();
        this.provider = provider;
    }

    public void setDepleted(final boolean isDepleted) {
        if (this.isDepleted == isDepleted) return;

        this.isDepleted = isDepleted;
        this.health = isDepleted ? 0 : getMaxHealth();
        this.isDepleting = false;
        regenerationTicks = 0;
    }

    public void startDepleting() {
        isDepleting = true;
        regenerationTicks = 0;
    }

    public void startRegenerating() {
        regenerationTicks++;

        if (regenerationTicks > 2) {
            isDepleting = false;
        }
    }

    public void onGameTick() {
        final boolean isBeingMined = isBeingMined();

        if (isDepleted) {
            health += (getMaxHealth()) / RESPAWN_TIME_GAMETICKS;
        } else if (isDepleting) {
            health = Math.max(health - 1, 0);
        } else if (regenerationTicks > 2) {
            health = Math.min(health + 1, getMaxHealth());
        }

        if (!isDepleted && isBeingMined) {
            startDepleting();
        }

        if (!isDepleted && !isBeingMined) {
            startRegenerating();
        }
    }

    public float getPieProgress() {
        return health / getMaxHealth() * (isDepleted ? -1 : 1);
    }

    public Color getPieColor(final TicTac7xMotherlodeConfig config, final Motherlode motherlode) {
        return
            motherlode.shouldStopMining() ? config.getOreVeinsStoppingColor() :
            isDepleted ? config.getOreVeinsDepletedColor() :
            config.getOreVeinsColor();
    }

    public boolean isRendering(final TicTac7xMotherlodeConfig config, final Character character) {
        if (config.upstairsOnly() && sector == Sector.DOWNSTAIRS) {
            return false;
        }

        if (!character.getSectors().contains(sector)) {
            return false;
        }

        return true;
    }

    private float getMaxHealth() {
        return sector == Sector.DOWNSTAIRS
            ? DESPAWN_TIME_DOWNSTAIRS_GAMETICKS
            : DESPAWN_TIME_UPPERFLOOR_GAMETICKS;
    }

    public boolean isBeingMined() {
        for (final Player player : provider.client.getTopLevelWorldView().players()) {
            if (!isMiningAnimation(player.getAnimation())) continue;

            final int playerX = player.getWorldLocation().getX();
            final int playerY = player.getWorldLocation().getY();
            final int playerOrientation = player.getOrientation();

            if (
                // Facing south.
                playerOrientation == 0 && playerX == x && playerY == y + 1 ||
                // Facing west.
                playerOrientation == 512 && playerX == x + 1 && playerY == y ||
                // Facing north.
                playerOrientation == 1024 && playerX == x && playerY == y - 1 ||
                // Facing east.
                playerOrientation == 1536 && playerX == x - 1 && playerY == y
            ) {
                return true;
            }
        }

        return false;
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

    public static boolean isOreVein(final WallObject wallObject) {
        return isOreVein(wallObject.getId());
    }

    public static boolean isOreVein(final int wallObjectId) {
        for (final int oreVeinId : ORE_VEINS_IDS) {
            if (wallObjectId == oreVeinId) return true;
        }

        return false;
    }

    public static boolean isDepletedOreVein(final WallObject wallObject) {
        for (final int depletedOreVeinId : DEPLETED_ORE_VEINS_IDS) {
            if (wallObject.getId() == depletedOreVeinId) return true;
        }

        return false;
    }
}
