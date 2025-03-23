package tictac7x.storage;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(TicTac7xStorageConfig.group)
public interface TicTac7xStorageConfig extends Config {
	String group = "tictac7x-storage";
	String visible = "visible";
	String hidden = "hidden";
	String show = "show";
	String auto_hide = "auto_hide";
	String version = "version";
	String panel_priority = "panel_priority";
	String storage = "_storage";

	enum InventoryEmpty { TOP, FIRST, LAST, BOTTOM, HIDDEN }

	@ConfigItem(
		keyName = version,
		name = "Version",
		description = "Plugin version",
		hidden = true
	) default String getVersion() {
		return "0";
	}

	@ConfigSection(
		name = "Inventory",
		description = "Inventory overlay settings",
		position = 1
	) String inventory = "inventory";

		@ConfigItem(
			keyName = inventory,
			name = inventory,
			description = inventory,
			section = inventory,
			hidden = true
		) default String getInventory() { return "{}"; }

		@ConfigItem(
			keyName = inventory + "_" + show,
			name = "Show inventory overlay",
			description = "Show inventory overlay",
			section = inventory,
			position = 1
		) default boolean showInventory() { return true; }

		@ConfigItem(
			keyName = inventory + "_" + auto_hide,
			name = "Auto-hide when inventory is open",
			description = "Hide inventory overlay if inventory tab is open",
			section = inventory,
			position = 2
		) default boolean hideInventory() { return true; }

		@ConfigItem(
			keyName = inventory + "_" + visible,
			name = "Visible items",
			description = "Names of the items to show in the inventory overlay (all if empty)",
			section = inventory,
			position = 3
		) default String getInventoryVisible() { return ""; }

		@ConfigItem(
			keyName = inventory + "_" + hidden,
			name = "Hidden items",
			description = "Names of the items to hide in the inventory overlay",
			section = inventory,
			position = 4
		) default String getInventoryHidden() { return ""; }

		@ConfigItem(
			keyName = inventory + "_empty",
			name = "Empty slots",
			description = "Show the amount of free space in the inventory",
			section = inventory,
			position = 5
		) default InventoryEmpty getInventoryEmpty() { return InventoryEmpty.FIRST; }

	@ConfigSection(
		name = "Bank",
		description = "Bank overlay settings",
		position = 2
	) String bank = "bank";

		@ConfigItem(
			keyName = bank,
			name = bank,
			description = bank,
			section = bank,
			hidden = true
		) default String getBank() { return "{}"; }

		@ConfigItem(
			keyName = bank + "_" + show,
			name = "Show bank overlay",
			description = "Show bank overlay",
			section = bank,
			position = 1
		) default boolean showBank() { return true; }

		@ConfigItem(
			keyName = bank + "_" + auto_hide,
			name = "Auto-hide when bank is open",
			description = "Hide bank overlay if bank is open",
			section = bank,
			position = 2
		) default boolean hideBank() { return true; }

		@ConfigItem(
			keyName = bank + "_" + visible,
			name = "Visible items",
			description = "Names of the items to show in the bank overlay (all if empty)",
			section = bank,
			position = 3
		) default String getBankVisible() { return "Coins"; }

		@ConfigItem(
			keyName = bank + "_" + hidden,
			name = "Hidden items",
			description = "Names of the items to hide in the bank overlay",
			section = bank,
			position = 4
		) default String getBankHidden() { return ""; }

	@ConfigSection(
		name = "Panel",
		description = "Panel settings",
		position = 3
	) String panel = "panel";

		@ConfigItem(
			keyName = panel,
			name = "Show bank panel",
			description = "Show bank panel on the sidebar where you can check your bank items",
			section = panel,
			position = 1
		) default boolean showPanel() { return true; }

		@ConfigItem(
			keyName = panel_priority,
			name = "Bank panel priority",
			description = "Lower the number, higher the priority and storage icon on the sidebar",
			section = panel,
			position = 2
		) default int getPanelPriority() { return 5; }

	@ConfigSection(
		name = "Debug",
		description = "Debug",
		position = 4,
		closedByDefault = true
	) String debug = "debug";

		@ConfigItem(
			keyName = inventory + storage,
			name = inventory + storage,
			description = inventory + storage,
			section = debug,
			position = 1
		) default String getInventoryStorage() { return ""; }

		@ConfigItem(
			keyName = bank + storage,
			name = bank + storage,
			description = bank + storage,
			section = debug,
			position = 2
		) default String getBankStorage() { return ""; }
}
