package tictac7x.gotr.store;

import net.runelite.api.ItemID;
import net.runelite.api.ObjectID;
import tictac7x.gotr.types.Essence;

public class Teleporter {
    final public int teleporterGameObjectid;
    final public int runeItemId;
    final public int talismanItemId;
    final public int altarId;
    final private Essence essence;
    final public int level;

    final public static int ACTIVE_ANIMATION_ID = 9363;

    public static Teleporter AIR = new Teleporter(ObjectID.GUARDIAN_OF_AIR, ItemID.AIR_RUNE, ItemID.PORTAL_TALISMAN_AIR, 34760, Essence.ELEMENTAL, 1);
    public static Teleporter WATER = new Teleporter(ObjectID.GUARDIAN_OF_WATER, ItemID.WATER_RUNE, ItemID.PORTAL_TALISMAN_WATER, 34762, Essence.ELEMENTAL, 5);
    public static Teleporter EARTH = new Teleporter(ObjectID.GUARDIAN_OF_EARTH, ItemID.EARTH_RUNE, ItemID.PORTAL_TALISMAN_EARTH, 34763, Essence.ELEMENTAL, 9);
    public static Teleporter FIRE = new Teleporter(ObjectID.GUARDIAN_OF_FIRE, ItemID.FIRE_RUNE, ItemID.PORTAL_TALISMAN_FIRE, 34764, Essence.ELEMENTAL, 14);

    public static Teleporter MIND = new Teleporter(ObjectID.GUARDIAN_OF_MIND, ItemID.MIND_RUNE, ItemID.PORTAL_TALISMAN_MIND, 34761, Essence.CATALYST, 2);
    public static Teleporter BODY = new Teleporter(ObjectID.GUARDIAN_OF_BODY, ItemID.BODY_RUNE, ItemID.PORTAL_TALISMAN_BODY, 34765, Essence.CATALYST, 20);
    public static Teleporter COSMIC = new Teleporter(ObjectID.GUARDIAN_OF_COSMIC, ItemID.COSMIC_RUNE, ItemID.PORTAL_TALISMAN_COSMIC, 34766, Essence.CATALYST, 27);
    public static Teleporter CHAOS = new Teleporter(ObjectID.GUARDIAN_OF_CHAOS, ItemID.CHAOS_RUNE, ItemID.PORTAL_TALISMAN_CHAOS, 34769, Essence.CATALYST, 35);
    public static Teleporter NATURE = new Teleporter(ObjectID.GUARDIAN_OF_NATURE, ItemID.NATURE_RUNE, ItemID.PORTAL_TALISMAN_NATURE, 34768, Essence.CATALYST, 44);
    public static Teleporter LAW = new Teleporter(ObjectID.GUARDIAN_OF_LAW, ItemID.LAW_RUNE, ItemID.PORTAL_TALISMAN_LAW, 34767, Essence.CATALYST, 54);
    public static Teleporter DEATH = new Teleporter(ObjectID.GUARDIAN_OF_DEATH, ItemID.DEATH_RUNE, ItemID.PORTAL_TALISMAN_DEATH, 34770, Essence.CATALYST, 65);
    public static Teleporter BLOOD = new Teleporter(ObjectID.GUARDIAN_OF_BLOOD, ItemID.BLOOD_RUNE, ItemID.PORTAL_TALISMAN_BLOOD, 43479, Essence.CATALYST, 77);

    public static Teleporter[] ALL = new Teleporter[]{
        AIR, WATER, EARTH, FIRE, MIND, BODY, COSMIC, CHAOS, NATURE, LAW, DEATH, BLOOD
    };
    public Teleporter(
            final int teleporterGameObjectid,
            final int runeItemId,
            final int talismanItemId,
            final int altarId,
            final Essence essence,
            final int level
    ) {
        this.teleporterGameObjectid = teleporterGameObjectid;
        this.runeItemId = runeItemId;
        this.talismanItemId = talismanItemId;
        this.altarId = altarId;
        this.essence = essence;
        this.level = level;
    }

    public boolean isElemental() {
        return essence == Essence.ELEMENTAL;
    }

    public boolean isCatalyst() {
        return essence == Essence.CATALYST;
    }
}