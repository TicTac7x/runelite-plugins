package tictac7x.storage;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import tictac7x.storage.panel.PanelNavigationButton;
import tictac7x.storage.panel.StoragePanel;

import javax.annotation.Nullable;
import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Storage",
	description = "Show overlays of inventory and bank",
	tags = { "storage", "bank", "inventory", "item" }
)
public class TicTac7xStoragePlugin extends Plugin {
	private String plugin_version = "v0.6";
	private String plugin_message = "" +
		"<colHIGHLIGHT>Storage " + plugin_version + ":<br>" +
		"<colHIGHLIGHT>* Overlays without any items no longer rendering";

	@Inject
	private Client client;

	@Inject
	private ClientToolbar client_toolbar;

	@Inject
	private TicTac7xStorageConfig config;

	@Inject
	private ClientThread client_thread;

	@Inject
	private ConfigManager configs;

	@Inject
	private ItemManager items;

	@Inject
	private OverlayManager overlays;

	@Inject
	private ChatMessageManager chat_messages;

	@Provides
	TicTac7xStorageConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TicTac7xStorageConfig.class);
	}

	private Storage[] storages;

	private StoragePanel storage_panel;

	@Nullable
	private NavigationButton navigation_button;

	private PanelNavigationButton panelNavigationButton;

	@Override
	protected void startUp() {
		storages = new Storage[]{
			new StorageInventory(TicTac7xStorageConfig.inventory, InventoryID.INVENTORY, WidgetInfo.INVENTORY, client, client_thread, configs, config, items),
			new Storage(TicTac7xStorageConfig.bank, InventoryID.BANK, WidgetInfo.BANK_CONTAINER, client, client_thread, configs, config, items)
		};

		for (final Storage storage : storages) {
			overlays.add(storage);
		}

		// Panel
		storage_panel = new StoragePanel(client_thread, items, config);
		panelNavigationButton = new PanelNavigationButton(client_toolbar, config, storage_panel);
	}

	@Override
	protected void shutDown() {
		panelNavigationButton.shutDown();

		for (final Storage storage : storages) {
			overlays.remove(storage);
		}
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		for (final Storage storage : storages) {
			storage.onItemContainerChanged(event);
		}
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		if (!event.getGroup().equals(TicTac7xStorageConfig.group)) return;

		panelNavigationButton.onConfigChanged(event);

		// Update list of items in the panel.
		storage_panel.onConfigChanged(event);

		for (final Storage storage : storages) {
			storage.onConfigChanged(event);
		}
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		// Plugin update message.
		if (event.getGameState() == GameState.LOGGED_IN && !config.getVersion().equals(plugin_version)) {
			configs.setConfiguration(TicTac7xStorageConfig.group, TicTac7xStorageConfig.version, plugin_version);
			chat_messages.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(plugin_message)
				.build()
			);
		}
	}
}
