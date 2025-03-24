package tictac7x.storage;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemContainerChanged;
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
import net.runelite.client.ui.overlay.OverlayManager;
import tictac7x.storage.overlays.InventoryOverlay;
import tictac7x.storage.overlays.StorageOverlay;
import tictac7x.storage.panel.PanelNavigationButton;
import tictac7x.storage.panel.StoragePanel;
import tictac7x.storage.storage.DepositBox;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.utils.ItemContainerId;
import tictac7x.storage.utils.WidgetId;

import javax.inject.Inject;
import java.util.Optional;

@Slf4j
@PluginDescriptor(
	name = "Storage",
	description = "Show overlays of inventory and bank",
	tags = { "storage", "bank", "inventory", "item" }
)
public class TicTac7xStoragePlugin extends Plugin {
	private String pluginVersion = "v0.6";
	private String pluginMessage = "" +
		"<colHIGHLIGHT>Storage " + pluginVersion + ":<br>" +
		"<colHIGHLIGHT>* Overlays without any items no longer rendering";

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private TicTac7xStorageConfig config;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Provides
	TicTac7xStorageConfig provideConfig(final ConfigManager configManager) {
		return configManager.getConfig(TicTac7xStorageConfig.class);
	}

	private Storage[] storages;

	private StorageOverlay[] storageOverlays;

	private StoragePanel storagePanel;

	private PanelNavigationButton panelNavigationButton;

	@Override
	protected void startUp() {
		configMigration();

		final Storage bankStorage = new Storage(TicTac7xStorageConfig.bank, ItemContainerId.BANK, clientThread, itemManager, configManager);
		final Storage inventoryStorage = new Storage(TicTac7xStorageConfig.inventory, ItemContainerId.INVENTORY, clientThread, itemManager, configManager);

		final DepositBox depositBox = new DepositBox(client, inventoryStorage, bankStorage);

		storages = new Storage[] { bankStorage, inventoryStorage };

		storageOverlays = new StorageOverlay[]{
			new InventoryOverlay(TicTac7xStorageConfig.inventory, ItemContainerId.INVENTORY, WidgetId.INVENTORY, client, clientThread, overlayManager, configManager, itemManager, config),
			new StorageOverlay(TicTac7xStorageConfig.bank, ItemContainerId.BANK, WidgetId.BANK, client, clientThread, overlayManager, configManager, itemManager, config)
		};

		for (final StorageOverlay storageOverlay : storageOverlays) {
			overlayManager.add(storageOverlay);
		}

		// Panel
		storagePanel = new StoragePanel(clientThread, itemManager, configManager, config);
		panelNavigationButton = new PanelNavigationButton(clientToolbar, config, storagePanel);
	}

	@Override
	protected void shutDown() {
		panelNavigationButton.shutDown();

		for (final StorageOverlay storageOverlay : storageOverlays) {
			storageOverlay.shutDown();
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
		storagePanel.onConfigChanged(event);

		for (final StorageOverlay storageOverlay : storageOverlays) {
			storageOverlay.onConfigChanged(event);
		}
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		// Plugin update message.
		if (event.getGameState() == GameState.LOGGED_IN && !config.getVersion().equals(pluginVersion)) {
			configManager.setConfiguration(TicTac7xStorageConfig.group, TicTac7xStorageConfig.version, pluginVersion);
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(pluginMessage)
				.build()
			);
		}
	}

	private void configMigration() {
		// v0.5.1 -> v0.6
		final Optional<String> inventory = Optional.ofNullable(configManager.getConfiguration(TicTac7xStorageConfig.group, "inventory"));
		if (inventory.isPresent()) {
			configManager.setConfiguration(TicTac7xStorageConfig.group, TicTac7xStorageConfig.inventory + TicTac7xStorageConfig.storage, inventory.get());
			configManager.unsetConfiguration(TicTac7xStorageConfig.group, "inventory");
		}

		final Optional<String> bank = Optional.ofNullable(configManager.getConfiguration(TicTac7xStorageConfig.group, "bank"));
		if (bank.isPresent()) {
			configManager.setConfiguration(TicTac7xStorageConfig.group, TicTac7xStorageConfig.bank + TicTac7xStorageConfig.storage, bank.get());
			configManager.unsetConfiguration(TicTac7xStorageConfig.group, "bank");
		}
	}
}
