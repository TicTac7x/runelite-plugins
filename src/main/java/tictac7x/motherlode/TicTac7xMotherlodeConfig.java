package tictac7x.motherlode;

import net.runelite.client.config.*;
import java.awt.Color;

@ConfigGroup(TicTac7xMotherlodeConfig.group)
public interface TicTac7xMotherlodeConfig extends Config {
	String group = "tictac7x-motherlode";
	String sack_custom = "sack_custom";
	String bank_golden_nuggets = "bank_golden_nuggets";
	String version = "version";

	@ConfigItem(
		keyName = bank_golden_nuggets,
		name = bank_golden_nuggets,
		description = bank_golden_nuggets,
		hidden = true
	) default int getBankGoldenNuggets() { return 0; }

	@ConfigItem(
		keyName = version,
		name = version,
		description = version,
		hidden = true
	) default String getVersion() { return "0"; }

	@ConfigSection(
		name = "General",
		description = "General options to improve overall experience.",
		position = 1
	) String general = "general";

		@ConfigItem(
			keyName = "upstairs_only",
			name = "Only upstairs veins and rockfalls",
			description = "Highlight only upstairs ore veins and rockfalls.",
			position = 1,
			section = general
		) default boolean upstairsOnly() { return false; }

		@ConfigItem(
			keyName = "notify_to_stop_mining",
			name = "Notify to stop mining",
			description = "Notifies user to stop mining when sack would become full with already owned paydirt.",
			position = 2,
			section = general
		) default boolean notifyToStopMining() { return true; }

		@ConfigSection(
			name = "Ore veins and rockfalls",
			description = "Highlight ore veins and rockfalls based on their states.",
			position = 2
		) String ore_veins_and_rockfalls = "ore_veins_and_rockfalls";

		@Alpha
		@ConfigItem(
			keyName = "ore_veins",
			name = "Mineable Ore Veins",
			description = "Highlight ore veins that can be mined.",
			position = 1,
			section = ore_veins_and_rockfalls
		) default Color getOreVeinsColor() { return new Color(0, 255, 0, 140); }

		@Alpha
		@ConfigItem(
			keyName = "ore_veins_depleted",
			name = "Depleted Ore Veins",
			description = "Highlight ore veins that are depleted.",
			position = 2,
			section = ore_veins_and_rockfalls
		) default Color getOreVeinsDepletedColor() { return new Color(255, 180, 0, 140); }

		@Alpha
		@ConfigItem(
			keyName = "ore_veins_stop",
			name = "Stopping Ore Veins",
			description = "Highlight ore veins when they shouldn't be mined.",
			position = 3,
			section = ore_veins_and_rockfalls
		) default Color getOreVeinsStoppingColor() { return new Color(255, 0, 0, 140); }

		@Alpha
		@ConfigItem(
			keyName = "rockfalls",
			name = "Rockfalls",
			description = "Highlight rockfalls that need to be cleared.",
			position = 4,
			section = ore_veins_and_rockfalls
		) default Color getRockfallsColor() { return new Color(255, 0, 0, 80); }

	@ConfigSection(
		name = "Custom sack widget",
		description = "Show custom sack widget with helpful information.",
		position = 3
	) String custom_sack_widget = "custom_sack_widget";

		@ConfigItem(
			keyName = sack_custom,
			name = "Use custom sack widget",
			description = "Whether to show custom sack widget or not.",
			position = 1,
			section = custom_sack_widget
		) default boolean showCustomSackWidget() { return true; }

		@ConfigItem(
			keyName = "show_golden_nuggets_total",
			name = "Total golden nuggets",
			description = "Show the total number of golden nuggets that you have.",
			position = 2,
			section = custom_sack_widget
		) default boolean showGoldenNuggetsTotal() { return true; }

		@ConfigItem(
			keyName = "show_golden_nuggets_session",
			name = "Session golden nuggets",
			description = "Show the total number of golden nuggets you have found during this session.",
			position = 3,
			section = custom_sack_widget
		) default boolean showGoldenNuggetsSession() { return false; }

		@ConfigItem(
			keyName = "sack_paydirt",
			name = "Sack pay-dirt",
			description = "Show total number of pay-dirt in sack and hopper.",
			position = 4,
			section = custom_sack_widget
		) default boolean showSackAndHopperPaydirt() { return true; }

		@ConfigItem(
			keyName = "sack_paydirt_inventory",
			name = "Pay-dirt from inventory",
			description = "Show how many pay dirt will be added to the sack.",
			position = 5,
			section = custom_sack_widget
		) default boolean showSackPaydirtFromInventory() { return false; }

		@ConfigItem(
			keyName = "sack_size",
			name = "Sack total size",
			description = "Show total size of the sack.",
			position = 6,
			section = custom_sack_widget
		) default boolean showSackSize() { return true; }

		@ConfigItem(
			keyName = "sack_deposits",
			name = "Deposits left",
			description = "Show number of inventories you can store before sack gets full.",
			position = 7,
			section = custom_sack_widget
		) default boolean showSackDeposits() { return true; }

		@ConfigItem(
			keyName = "sack_needed",
			name = "Needed pay-dirt",
			description = "Show number of pay-dirt needed to mine before you should deposit the pay-dirt.",
			position = 8,
			section = custom_sack_widget
		) default boolean showSackNeeded() { return true; }


}
