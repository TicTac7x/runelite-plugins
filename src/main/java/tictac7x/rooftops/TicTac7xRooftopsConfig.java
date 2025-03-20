package tictac7x.rooftops;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

import java.awt.Color;

import static tictac7x.rooftops.TicTac7xRooftopsConfig.group;

@ConfigGroup(group)
public interface TicTac7xRooftopsConfig extends Config {
	String group = "tictac7x-rooftops";
	String version = "version";

	@ConfigItem(
		keyName = version,
		name = version,
		description = "Version of the plugin for update message",
		hidden = true
	) default String getVersion() { return ""; }

	@ConfigSection(
		name = "Obstacles",
		description = "Obstacles",
		position = 1
	) String obstacles = "obstacles";

		@Alpha
		@ConfigItem(
			keyName = "obstacle_next",
			name = "Next",
			description = "Color of the next obstacle.",
			position = 1,
			section = obstacles
		) default Color getObstacleNextColor() { return new Color(0, 255, 0, 80); }

		@Alpha
		@ConfigItem(
			keyName = "obstacle_next_unavailable",
			name = "Next unavailable",
			description = "Color of the next obstacle.",
			position = 2,
			section = obstacles
		) default Color getObstacleNextUnavailableColor() { return new Color(200, 255, 0, 80); }

		@Alpha
		@ConfigItem(
			keyName = "obstacle_unavailable",
			name = "Other",
			description = "Color of other obstacles.",
			position = 3,
			section = obstacles
		) default Color getObstacleUnavailableColor() { return new Color(255, 150, 0, 70); }

		@Alpha
		@ConfigItem(
			keyName = "obstacle_stop",
			name = "Stop",
			description = "Color of obstacle that should not be used, because Mark of grace is on the ground.",
			position = 4,
			section = obstacles
		) default Color getObstacleStopColor() { return new Color(255, 0, 0, 80); }

	@ConfigSection(
		name = "Marks of graces",
		description = "Marks of graces",
		position = 2
	) String marks_of_graces = "marks_of_graces";

		@Alpha
		@ConfigItem(
			keyName = "mark_of_grace",
			name = "Mark of grace",
			description = "Color of the Mark of grace.",
			position = 1,
			section = marks_of_graces
		) default Color getMarkOfGraceColor() { return new Color(0, 255, 0, 80); }

		@ConfigItem(
			keyName = "mark_of_grace_stop",
			name = "Show stop obstacles",
			description = "Show next obstacle after Mark of grace as stop.",
			position = 2,
			section = marks_of_graces
		) default boolean showMarkOfGraceStop() { return true; }
}
