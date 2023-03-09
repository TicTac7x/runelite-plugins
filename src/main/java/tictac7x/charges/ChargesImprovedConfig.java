package tictac7x.charges;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

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

    String arclight = "arclight";
    String ash_sanctifier = "ash_sanctifier";
    String bone_crusher = "bone_crusher";
    String bottomless_compost_bucket = "bottomless_compost_bucket";
    String bottomless_compost_bucket_type = "bottomless_compost_bucket_type";
    String bracelet_of_slaughter = "bracelet_of_slaughter";
    String bryophytas_staff = "bryophytas_staff";
    String celestial_ring = "celestial_ring";
    String chronicle = "chronicle";
    String crystal_shield = "crystal_shield";
    String expeditious_bracelet = "expeditious_bracelet";
    String falador_shield = "falador_shield";
    String gricollers_can = "gricollers_can";
    String ibans_staff = "ibans_staff";
    String pharaohs_sceptre = "pharaohs_sceptre";
    String ring_of_suffering = "ring_of_suffering";
    String sanguinesti_staff = "sanguinesti_staff";
    String skull_sceptre = "skull_sceptre";
    String soul_bearer = "soul_bearer";
    String trident_of_the_seas = "trident_of_the_seas";
    String xerics_talisman = "xerics_talisman";

    @ConfigSection(
        name = "Colors",
        description = "Colors of item overlays",
        position = 1
    ) String colors = "colors";

        @ConfigItem(
            keyName = "colors_default",
            name = "Default",
            description = "Color of default charges",
            position = 1,
            section = colors
        ) default Color getColorDefault() { return Color.white; }

        @ConfigItem(
            keyName = "colors_unknown",
            name = "Unknown",
            description = "Color of unknown charges",
            position = 2,
            section = colors
        ) default Color getColorUnknown() { return Color.gray; }

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
        ) default int getArclightCharges() { return -1; }

        @ConfigItem(
            keyName = ash_sanctifier,
            name = "Ash sanctifier",
            description = "Ash sanctifier charges",
            section = debug
        ) default int getAshSanctifierCharges() { return -1; }

        @ConfigItem(
            keyName = bone_crusher,
            name = "Bone crusher",
            description = "Bone crusher charges",
            section = debug
        ) default int getBoneCrusherCharges() { return -1; }

        @ConfigItem(
            keyName = bottomless_compost_bucket,
            name = "Bottomless compost bucket",
            description = "Bottomless compost bucket charges",
            section = debug
        ) default int getBottomlessCompostBucketCharges() { return -1; }

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
        ) default int getBraceletOfSlaughterCharges() { return -1; }

        @ConfigItem(
            keyName = bryophytas_staff,
            name = "Bryophyta's Staff",
            description = "Bryophyta's Staff charges",
            section = debug
        ) default int getBryophytasStaffCharges() { return -1; }

        @ConfigItem(
            keyName = celestial_ring,
            name = "Celestial Ring",
            description = "Celestial Ring charges",
            section = debug
        ) default int getCelestialRingCharges() { return -1; }

        @ConfigItem(
            keyName = chronicle,
            name = "Chronicle",
            description = "Chronicle charges",
            section = debug
        ) default int getChronicleCharges() { return -1; }

        @ConfigItem(
            keyName = crystal_shield,
            name = "Crystal shield",
            description = "Crystal shield charges",
            section = debug
        ) default int getCrystalShieldCharges() { return -1; }

        @ConfigItem(
            keyName = expeditious_bracelet,
            name = "Expeditious bracelet",
            description = "Expeditious bracelet charges",
            section = debug
        ) default int getBraceletOfExpeditiousCharges() { return -1; }

        @ConfigItem(
            keyName = falador_shield,
            name = "Falador shield",
            description = "Falador shield charges",
            section = debug
        ) default int getFaladorShieldCharges() { return -1; }

        @ConfigItem(
            keyName = gricollers_can,
            name = "Gricoller's can",
            description = "Gricoller's can charges",
            section = debug
        ) default int getGricollersCanCharges() { return -1; }

        @ConfigItem(
            keyName = ibans_staff,
            name = "Iban's staff",
            description = "Iban's staff charges",
            section = debug
        ) default int getIbansStaffCharges() { return -1; }

        @ConfigItem(
            keyName = pharaohs_sceptre,
            name = "Pharaoh's sceptre",
            description = "Pharaoh's sceptre charges",
            section = debug
        ) default int getPharaohsSceptreCharges() { return -1; }
        @ConfigItem(
                keyName = ring_of_suffering,
                name = "Ring of Suffering",
                description = "Ring of suffering charges",
                section = debug
        ) default int getRingOfSufferingCharges() { return -1; }

        @ConfigItem(
                keyName = sanguinesti_staff,
                name = "Sanguinesti staff",
                description = "Sanguinesti staff charges",
                section = debug
        ) default int getSanguinestiStaffCharges() { return -1; }

        @ConfigItem(
            keyName = skull_sceptre,
            name = "Skull sceptre",
            description = "Skull sceptre charges",
            section = debug
        ) default int getSkullSceptreCharges() { return -1; }

        @ConfigItem(
            keyName = soul_bearer,
            name = "Soul bearer",
            description = "Soul bearer charges",
            section = debug
        ) default int getSoulBearerCharges() { return -1; }

        @ConfigItem(
            keyName = trident_of_the_seas,
            name = "Trident of the seas",
            description = "Trident of the seas charges",
            section = debug
        ) default int getTridentOfTheSeasCharges() { return -1; }

        @ConfigItem(
            keyName = xerics_talisman,
            name = "Xeric's talisman",
            description = "Xeric's talisman charges",
            section = debug
        ) default int getXericsTalismanCharges() { return -1; }
}
