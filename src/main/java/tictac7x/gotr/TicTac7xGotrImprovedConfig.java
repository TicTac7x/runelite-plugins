package tictac7x.gotr;

import net.runelite.client.config.*;
import tictac7x.gotr.types.BarriersWidgetStyle;
import tictac7x.gotr.types.BeforeGameStarts;
import tictac7x.gotr.types.PointsWidgetStyle;

import java.awt.Color;

import static tictac7x.gotr.TicTac7xGotrImprovedConfig.group;

@ConfigGroup(group)
public interface TicTac7xGotrImprovedConfig extends Config {
    String group = "tictac7x-gotr";

    String version = "version";
    String energy_catalytic = "energy_catalytic";
    String energy_elemental = "energy_elemental";
    String guardians_current = "guardians_current";
    String guardians_total = "guardians_total";

    @ConfigSection(
        name = "Colors",
        description = "Change colors of various items",
        position = 1,
        closedByDefault = true
    ) String colors = "colors";

        @Alpha
        @ConfigItem(
            keyName = "color_elemental",
            name = "Elemental color",
            description = "Color to highlight elemental objects",
            position = 1,
            section = colors
        ) default Color getElementalColor() {
            return new Color(50, 210, 160);
        }

        @Alpha
        @ConfigItem(
            keyName = "color_catalytic",
            name = "Catalytic color",
            description = "Color to highlight catalytic objects",
            position = 2,
            section = colors
        ) default Color getCatalyticColor() {
            return new Color(215, 240, 60);
        }

        @ConfigItem(
            keyName = "color_widget_energies",
            name = "Colorful energy points",
            description = "If enabled shows energy points in respective colors",
            position = 3,
            section = colors
        ) default boolean showWidgetEnergyPointColors() {
            return true;
        }

        @ConfigItem(
            keyName = "color_widget_portal",
            name = "Colorful portal countdown",
            description = "If enabled shows portal countdown in different colors based on time left",
            position = 4,
            section = colors
        ) default boolean showWidgetPortalCountdownColors() {
            return true;
        }

    @ConfigSection(
        name = "Teleporters",
        description = "Teleporters",
        position = 2,
        closedByDefault = true
    ) String teleporters = "teleporters";

        @ConfigItem(
            keyName = "highlight_teleporters",
            name = "Highlight teleporters",
            description = "Highlight active teleporters",
            position = 1,
            section = teleporters
        ) default boolean highlightActiveTeleporters() {
            return true;
        }

        @ConfigItem(
            keyName = "teleporters_level_requirements",
            name = "Check level requirements",
            description = "Check teleporter level requirement for highlighting",
            position = 2,
            section = teleporters
        ) default boolean checkTeleporterLevelRequirements() {
        return true;
    }

        @ConfigItem(
            keyName = "unusable_teleporters",
            name = "Indicate unusable teleporters",
            description = "Indicate that teleporters are unusable, because player has guardian stones in their inventory",
            position = 3,
            section = teleporters
        ) default boolean indicateUnusableTeleporters() {
            return true;
        }

    @ConfigSection(
        name = "Guardians",
        description = "Guardians",
        position = 3,
        closedByDefault = true
    ) String guardians = "guardians";

        @ConfigItem(
            keyName = "highlight_guardians",
            name = "Highlight guardians",
            description = "Highlight guardians if it's possible to build them",
            position = 1,
            section = guardians
        ) default boolean highlightGuardians() {
            return true;
        }

        @ConfigItem(
            keyName = "highlight_guardians_without_cells",
            name = "Highlight guardians without cells",
            description = "Highlight guardians even if player has no cells in their inventory",
            position = 2,
            section = guardians
        ) default boolean highlightGuardiansWithoutCells() {
            return false;
        }

    @ConfigSection(
        name = "Great Guardian",
        description = "Great Guardian",
        position = 4,
        closedByDefault = true
    ) String greatGuardian = "great_guardian";

        @ConfigItem(
            keyName = "highlight_great_guardian",
            name = "Highlight great guardian",
            description = "Highlight great guardian if you have guardian stones in your inventory",
            position = 1,
            section = greatGuardian
        ) default boolean highlightGreatGuardian() {
            return true;
        }

    @ConfigSection(
        name = "Uncharged Cells",
        description = "Uncharged Cells",
        position = 5,
        closedByDefault = true
    ) String unchargedCells = "uncharged_cells";

        @ConfigItem(
            keyName = "highlight_uncharged_cells_bench",
            name = "Highlight Uncharged Cells Bench",
            description = "Highlight uncharged cells bench if player has no uncharged cells in their inventory",
            position = 1,
            section = unchargedCells
        ) default boolean highlightUnchargedCellsBench() {
        return true;
    }

    @ConfigSection(
        name = "Entrance",
        description = "Entrance",
        position = 6,
        closedByDefault = true
    ) String entrance = "entrance";

        @ConfigItem(
            keyName = "entrance_prevent_quickpass",
            name = "Prevent quick leave",
            description = "Prevent quick-pass of the entrance during game",
            position = 1,
            section = entrance
        ) default boolean preventEntranceQuickLeave() {
        return true;
    }

    @ConfigSection(
        name = "Points Widget",
        description = "Points Widget",
        position = 7,
        closedByDefault = true
    ) String points_widget = "points_widget";

        @ConfigItem(
            keyName = "points_widget_style",
            name = "Points widget style",
            description = "Style of points",
            position = 1,
            section = points_widget
        ) default PointsWidgetStyle getPointsWidgetStyle() {
        return PointsWidgetStyle.SEPARATE;
    }

    @ConfigSection(
        name = "Barriers Widget",
        description = "Barriers Widget",
        position = 8,
        closedByDefault = true
    ) String barriers_widget = "barriers_widget";

        @ConfigItem(
            keyName = "barriers_widget_style",
            name = "Widget style",
            description = "Show bars of barriers levels and health",
            position = 1,
            section = barriers_widget
        ) default BarriersWidgetStyle getBarriersWidgetStyle() {
        return BarriersWidgetStyle.ALL;
    }

    @ConfigSection(
        name = "Rewards guardian",
        description = "Rewards guardian",
        position = 9,
        closedByDefault = true
    ) String rewardsGuardian = "rewards_guardian";

        @ConfigItem(
            keyName = "rewards_guardian_overlay",
            name = "Show remaining searches",
            description = "Show number of remaining searches above rewards guardian",
            position = 1,
            section = rewardsGuardian
        ) default boolean showRewardsGuardianOverlay() {
        return true;
    }

    @ConfigSection(
        name = "Notifications",
        description = "Manage notifications",
        position = 10,
        closedByDefault = true
    ) String notifications = "notifications";

        @ConfigItem(
            keyName = "notification_before_game_starts",
            name = "Before game starts",
            description = "Notify seconds before the game starts ",
            position = 1,
            section = notifications
        ) default BeforeGameStarts notifyBeforeGameStarts() {
        return BeforeGameStarts.NEVER;
    }

        @ConfigItem(
            keyName = "notification_game_started",
            name = "Game started",
            description = "Notify that the game has started",
            position = 2,
            section = notifications
        ) default boolean notifyGameStarted() {
        return true;
    }

        @Range(min = -1, max = 110)
        @ConfigItem(
            keyName = "notification_before_first_rift",
            name = "Time until first rift",
            description = "Notify before the first rift is opened. -1 to disable.",
            position = 3,
            section = notifications
        ) default int notifyBeforeFirstRift() {
        return 35;
    }

        @ConfigItem(
            keyName = "notification_portal_opened",
            name = "Portal opened",
            description = "Notify about opened portal to huge guardian remains",
            position = 4,
            section = notifications
        ) default boolean notifyPortalOpened() {
        return true;
    }

        @ConfigItem(
            keyName = "notification_eye_robes",
            name = "Unused eye robes",
            description = "Notify about unused eye robes when you are near altar with essence in inventory",
            position = 5,
            section = notifications
        ) default boolean notifyUnusedEyeRobes() {
        return true;
    }

    @ConfigSection(
        name = "Debug",
        description = "Values of charges for all items under the hood",
        position = 99,
        closedByDefault = true
    ) String debug = "debug";

        @ConfigItem(
            keyName = version,
            name = version,
            description = version,
            section = debug,
            position = 1
        ) default String getVersion() { return "v0.1"; }

        @ConfigItem(
            keyName = energy_catalytic,
            name = energy_catalytic,
            description = energy_catalytic,
            section = debug,
            position = 2
        ) default int getCatalyticEnergy() { return 0; }

        @ConfigItem(
            keyName = energy_elemental,
            name = energy_elemental,
            description = energy_elemental,
            section = debug,
            position = 3
        ) default int getElementalEnergy() { return 0; }
}
