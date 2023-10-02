package tictac7x.charges;

import net.runelite.client.config.*;
import tictac7x.charges.store.ChargesItem;

import java.awt.Color;
import java.util.EnumSet;
import java.util.Set;

import static tictac7x.charges.ChargesImprovedConfig.group;

@ConfigGroup(group)
public interface ChargesImprovedConfig extends Config {
    String group = "tictac7x-charges";
    String version = "version";
    String date = "date";
    String infoboxes_show = "infoboxes_show";
    String infoboxes_hidden = "infoboxes_hidden";
    String item_overlays_show = "item_overlays_show";
    String item_overlays_hidden = "item_overlays_hidden";

    String arclight = "arclight";
    String ash_sanctifier = "ash_sanctifier";
    String bone_crusher = "bone_crusher";
    String kharedsts_memoirs = "kharedsts_memoirs";
    String bottomless_compost_bucket = "bottomless_compost_bucket";
    String bottomless_compost_bucket_type = "bottomless_compost_bucket_type";
    String bracelet_of_slaughter = "bracelet_of_slaughter";
    String bryophytas_staff = "bryophytas_staff";
    String celestial_ring = "celestial_ring";
    String escape_crystal = "escape_crystal";
    String chronicle = "chronicle";
    String crystal_shield = "crystal_shield";
    String crystal_bow = "crystal_bow";
    String expeditious_bracelet = "expeditious_bracelet";
    String falador_shield = "falador_shield";
    String fish_barrel = "fish_barrel";
    String gricollers_can = "gricollers_can";
    String ibans_staff = "ibans_staff";
    String pharaohs_sceptre = "pharaohs_sceptre";
    String ring_of_suffering = "ring_of_suffering";
    String sanguinesti_staff = "sanguinesti_staff";
    String skull_sceptre = "skull_sceptre";
    String soul_bearer = "soul_bearer";
    String trident_of_the_seas = "trident_of_the_seas";
    String xerics_talisman = "xerics_talisman";
    String dragonfire_shield = "dragonfire_shield";
    String toxic_staff_of_the_dead = "toxic_staff_of_the_dead";
    String camulet = "camulet";
    String circlet_of_water = "circlet_of_water";
    String teleport_crystal = "teleport_crystal";
    String waterskin = "waterskin";
    String bracelet_of_clay = "bracelet_of_clay";
    String coffin = "coffin";
    String bracelet_of_flamtaer = "bracelet_of_flamtaer";
    String ring_of_recoil = "ring_of_recoil";
    String log_basket = "log_basket";
    String ardougne_cloak = "ardougne_cloak";
    String magic_cape = "magic_cape";
    String crystal_helm = "crystal_helm";
    String crystal_body = "crystal_body";
    String crystal_legs = "crystal_legs";

    @ConfigSection(
        name = "Colors",
        description = "Colors of item overlays",
        position = 1
    ) String colors = "colors";

        @Alpha
        @ConfigItem(
            keyName = "colors_default",
            name = "Default",
            description = "Color of default charges",
            position = 1,
            section = colors
        ) default Color getColorDefault() { return Color.white; }

        @Alpha
        @ConfigItem(
            keyName = "colors_unknown",
            name = "Unknown",
            description = "Color of unknown charges",
            position = 2,
            section = colors
        ) default Color getColorUnknown() { return Color.gray; }

        @Alpha
        @ConfigItem(
            keyName = "colors_empty",
            name = "Empty",
            description = "Color of empty charges",
            position = 3,
            section = colors
        ) default Color getColorEmpty() { return Color.red; }

    @ConfigSection(
        name = "Infoboxes",
        description = "Select items to hide infoboxes for",
        position = 2,
        closedByDefault = true
    ) String infoboxes = "infoboxes";

        @ConfigItem(
            keyName = infoboxes_show,
            name = "Show infoboxes",
            description = "Whether to allow showing infoboxes for items",
            position = 1,
            section = infoboxes
        ) default boolean showInfoboxes() {
        return true;
    }

        @ConfigItem(
            keyName = infoboxes_hidden,
            name = "Hidden",
            description = "Select items to hide infoboxes for (use ctrl + click to select multiple items)",
            position = 2,
            section = infoboxes
        ) default Set<ChargesItem> getHiddenInfoboxes() {
            return EnumSet.noneOf(ChargesItem.class);
        }

    @ConfigSection(
        name = "Item Overlays",
        description = "Select items to hide item overlays for",
        position = 3,
        closedByDefault = true
    ) String item_overlays = "item_overlays";

        @ConfigItem(
            keyName = item_overlays_show,
            name = "Show item overlays",
            description = "Whether to allow showing overlays for items",
            position = 1,
            section = item_overlays
        ) default boolean showItemOverlays() {
            return true;
        }

        @ConfigItem(
            keyName = item_overlays_hidden,
            name = "Hidden",
            description = "Select items to hide item overlays for (use ctrl + click to select multiple items)",
            position = 2,
            section = item_overlays
        ) default Set<ChargesItem> getHiddenItemOverlays() {
        return EnumSet.noneOf(ChargesItem.class);
    }

    @ConfigSection(
        name = "Debug",
        description = "Values of charges for all items under the hood",
        position = 99,
        closedByDefault = true
    ) String debug = "debug";

        @ConfigItem(
            keyName = version,
            name = "Version",
            description = "Version of the plugin for update message",
            section = debug,
            position = 1
        ) default String getVersion() { return ""; }

        @ConfigItem(
            keyName = date,
            name = "Date",
            description = "Date to check for charges reset when logging in",
            section = debug,
            position = 2
        ) default String getResetDate() { return ""; }

        @ConfigItem(
            keyName = arclight,
            name = "Arclight",
            description = "Arclight charges",
            section = debug
        ) default int getArclightCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = ash_sanctifier,
            name = "Ash sanctifier",
            description = "Ash sanctifier charges",
            section = debug
        ) default int getAshSanctifierCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = bone_crusher,
            name = "Bone crusher",
            description = "Bone crusher charges",
            section = debug
        ) default int getBoneCrusherCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = kharedsts_memoirs,
            name = "Kharedst's memoirs",
            description = "Kharedst's memoirs charges",
            section = debug
        ) default int getKharedstsMemoirsCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = bottomless_compost_bucket,
            name = "Bottomless compost bucket",
            description = "Bottomless compost bucket charges",
            section = debug
        ) default int getBottomlessCompostBucketCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = bottomless_compost_bucket_type,
            name = "Bottomless compost bucket type",
            description = "Bottomless compost bucket type",
            section = debug
        ) default String getBottomlessCompostBucketType() { return ""; }

        @ConfigItem(
            keyName = bracelet_of_slaughter,
            name = "Bracelet of slaughter",
            description = "Bracelet of slaughter charges",
            section = debug
        ) default int getBraceletOfSlaughterCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = bryophytas_staff,
            name = "Bryophyta's Staff",
            description = "Bryophyta's Staff charges",
            section = debug
        ) default int getBryophytasStaffCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = celestial_ring,
            name = "Celestial Ring",
            description = "Celestial Ring charges",
            section = debug
        ) default int getCelestialRingCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = chronicle,
            name = "Chronicle",
            description = "Chronicle charges",
            section = debug
        ) default int getChronicleCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = crystal_shield,
            name = "Crystal shield",
            description = "Crystal shield charges",
            section = debug
        ) default int getCrystalShieldCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = crystal_bow,
            name = "Crystal bow",
            description = "Crystal bow charges",
            section = debug
        ) default int getCrystalBowCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = expeditious_bracelet,
            name = "Expeditious bracelet",
            description = "Expeditious bracelet charges",
            section = debug
        ) default int getBraceletOfExpeditiousCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = falador_shield,
            name = "Falador shield",
            description = "Falador shield charges",
            section = debug
        ) default int getFaladorShieldCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = fish_barrel,
            name = "Fish barrel",
            description = "Fish barrel charges",
            section = debug
        ) default int getFishBarrelCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = gricollers_can,
            name = "Gricoller's can",
            description = "Gricoller's can charges",
            section = debug
        ) default int getGricollersCanCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = ibans_staff,
            name = "Iban's staff",
            description = "Iban's staff charges",
            section = debug
        ) default int getIbansStaffCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = pharaohs_sceptre,
            name = "Pharaoh's sceptre",
            description = "Pharaoh's sceptre charges",
            section = debug
        ) default int getPharaohsSceptreCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = ring_of_suffering,
            name = "Ring of Suffering",
            description = "Ring of suffering charges",
            section = debug
        ) default int getRingOfSufferingCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = sanguinesti_staff,
            name = "Sanguinesti staff",
            description = "Sanguinesti staff charges",
            section = debug
        ) default int getSanguinestiStaffCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = skull_sceptre,
            name = "Skull sceptre",
            description = "Skull sceptre charges",
            section = debug
        ) default int getSkullSceptreCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = soul_bearer,
            name = "Soul bearer",
            description = "Soul bearer charges",
            section = debug
        ) default int getSoulBearerCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = trident_of_the_seas,
            name = "Trident of the seas",
            description = "Trident of the seas charges",
            section = debug
        ) default int getTridentOfTheSeasCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = xerics_talisman,
            name = "Xeric's talisman",
            description = "Xeric's talisman charges",
            section = debug
        ) default int getXericsTalismanCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = dragonfire_shield,
            name = "Dragonfire shield",
            description = "Dragonfire shield charges",
            section = debug
        ) default int getDragonfireShieldCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = camulet,
            name = "Camulet",
            description = "Camulet charges",
            section = debug
        ) default int getCamuletCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = circlet_of_water,
            name = "Circlet of water",
            description = "Circlet of water charges",
            section = debug
        ) default int getCircletOfWaterCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = teleport_crystal,
            name = "Teleport crystal",
            description = "Teleport crystal charges",
            section = debug
        ) default int getTeleportCrystalCharges() { return ChargesImprovedPlugin.CHARGES_UNKNOWN; }

        @ConfigItem(
            keyName = waterskin,
            name = "Waterskin",
            description = "Waterskin charges",
            section = debug
        ) default int getWaterskinCharges() { return -1; }

        @ConfigItem(
            keyName = bracelet_of_clay,
            name = "Bracelet of clay",
            description = "Bracelet of clay charges",
            section = debug
        ) default int getBraceletOfClayCharges() { return -1; }

        @ConfigItem(
            keyName = coffin,
            name = "Coffin",
            description = "Coffin charges",
            section = debug
        ) default int getCoffinCharges() { return -1; }

        @ConfigItem(
            keyName = log_basket,
            name = "Log basket",
            description = "Log basket charges",
            section = debug
        ) default int getLogBasketCharges() { return -1; }

        @ConfigItem(
            keyName = ardougne_cloak,
            name = "Ardougne cloak",
            description = "Ardougle cloak charges",
            section = debug
        ) default int getArdougneCloakCharges() { return -1; }

        @ConfigItem(
            keyName = magic_cape,
            name = "Magic cape",
            description = "Magic cape charges",
            section = debug
        ) default int getMagicCapeCharges() { return -1; }
}
