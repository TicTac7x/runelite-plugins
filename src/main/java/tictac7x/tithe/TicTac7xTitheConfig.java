package tictac7x.tithe;

import net.runelite.client.config.Config;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import java.awt.Color;

@ConfigGroup(TicTac7xTitheConfig.group)
public interface TicTac7xTitheConfig extends Config {
	String group = "tictac7x-tithe";
	String version = "version";

	@ConfigItem(
		position = 1,
		keyName = version,
		name = "Version",
		description = "Plugin version",
		hidden = true
	) default String getVersion() {
		return "0";
	}

	@ConfigSection(
		position = 1,
		name = "Farming patches",
		description = "Highlight farming patches and show progress of plants"
	) String section_patches = "farming_patches";

		@Alpha
		@ConfigItem(
			position = 1,
			keyName = "plants_dry",
			name = "Dry plants",
			description = "Highlight dry plants that need to be watered.",
			section = section_patches
		) default Color getPlantsDryColor() {
			return new Color(255, 180, 0, 100);
		}

		@Alpha
		@ConfigItem(
			position = 2,
			keyName = "plants_watered",
			name = "Watered plants",
			description = "Highlight watered plants",
			section = section_patches
		) default Color getPlantsWateredColor() {
			return new Color(60, 240, 255, 100);
		}

		@Alpha
		@ConfigItem(
			position = 3,
			keyName = "plants_grown",
			name = "Grown plants",
			description = "Highlight grown plants",
			section = section_patches
		) default Color getPlantsGrownColor() {
			return new Color(0, 255, 0, 100);
		}

		@Alpha
		@ConfigItem(
			position = 4,
			keyName = "plants_blighted",
			name = "Blighted plants",
			description = "Highlight blighted plants",
			section = section_patches
		) default Color getPlantsBlightedColor() {
			return new Color(200, 200, 200, 100);
		}

		@Alpha
		@ConfigItem(
			position = 5,
			keyName = "farm_patches_hover",
			name = "Farm patches",
			description = "Highlight farm patches on hover",
			section = section_patches
		) default Color getPatchesHighlightOnHoverColor() {
			return new Color(200, 200, 200, 60);
		}

	@ConfigSection(
		position = 2,
		name = "Inventory",
		description = "Highlight items needed for the tithe farming in the inventory"
	) String section_inventory = "inventory";

		@Alpha
		@ConfigItem(
			position = 1,
			keyName = "seeds",
			name = "Seeds",
			description = "Highlight seeds",
			section = section_inventory
		) default Color getHighlightSeedsColor() {
			return new Color(0, 255, 0, 80);
		}

		@Alpha
		@ConfigItem(
			position = 1,
			keyName = "watering_cans",
			name = "Watering cans",
			description = "Highlight watering cans",
			section = section_inventory
		) default Color getHighlightWateringCanColor() {
			return new Color(60, 240, 255, 100);
	}

	@ConfigSection(
		position = 3,
		name = "Points",
		description = "Show custom information about tithe farm points"
	) String section_points = "points";

		String points = "points";
		@ConfigItem(
			position = 1,
			keyName = points,
			name = "Show custom points widget",
			description = "Show total, earned points and harvested fruits.",
			section = section_points
		) default boolean showCustomPoints() {
			return true;
		}
}
