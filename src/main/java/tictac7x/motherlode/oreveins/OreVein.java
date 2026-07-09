package tictac7x.motherlode.oreveins;

import net.runelite.api.WallObject;
import tictac7x.motherlode.Motherlode;
import tictac7x.motherlode.ids.WallObjectId;
import tictac7x.motherlode.sectors.Sectors;
import tictac7x.motherlode.Character;
import tictac7x.motherlode.sectors.Sector;
import tictac7x.motherlode.TicTac7xMotherlodeConfig;

import java.awt.Color;

public class OreVein {
    public final int x;
    public final int y;
    private boolean isDepleted;
    private boolean isDepleting;
    private float health;
    public final Sector sector;

    private final int RESPAWN_TIME_GAMETICKS = 100;
    private final int DESPAWN_TIME_DOWNSTAIRS_GAMETICKS = 45;
    private final int DESPAWN_TIME_UPPERFLOOR_GAMETICKS = 67;
    private static final int[] ORE_VEINS_IDS = new int[]{WallObjectId.ORE_VEIN_ONE, WallObjectId.ORE_VEIN_TWO, WallObjectId.ORE_VEIN_THREE, WallObjectId.ORE_VEIN_FOUR};
    private static final int[] DEPLETED_ORE_VEINS_IDS = new int[]{ WallObjectId.ORE_VEIN_ONE_DEPLETED, WallObjectId.ORE_VEIN_TWO_DEPLETED, WallObjectId.ORE_VEIN_THREE_DEPLETED, WallObjectId.ORE_VEIN_FOUR_DEPLETED };

    public OreVein(final int x, final int y, final boolean isDepleted) {
        this.x = x;
        this.y = y;
        this.isDepleted = isDepleted;
        this.sector = Sectors.getSectors(x, y, false).get(0);
        this.health = getMaxHealth();
    }

    public void setDepleted(final boolean isDepleted) {
        if (this.isDepleted == isDepleted) return;

        this.isDepleted = isDepleted;
        this.health = isDepleted ? 0 : getMaxHealth();
        this.isDepleting = false;
    }

    public void startDepleting() {
        isDepleting = true;
    }

    public void onGameTick() {
        if (isDepleted) {
            health += (getMaxHealth()) / RESPAWN_TIME_GAMETICKS;
        } else if (isDepleting) {
            health = Math.max(health - 1, 0);
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

    public static boolean isOreVein(final WallObject wallObject) {
        for (final int oreVeinId : ORE_VEINS_IDS) {
            if (wallObject.getId() == oreVeinId) return true;
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
