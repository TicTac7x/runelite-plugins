package tictac7x.storage;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
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
import tictac7x.storage.overlays.BankOverlay;
import tictac7x.storage.overlays.InventoryOverlay;
import tictac7x.storage.overlays.StorageOverlay;
import tictac7x.storage.panel.PanelNavigator;
import tictac7x.storage.panel.StoragePanel;
import tictac7x.storage.storage.BankStorage;
import tictac7x.storage.storage.StorageItem;
import tictac7x.storage.storageManagers.DepositBox;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.ConfigStorage;
import tictac7x.storage.storageManagers.LunarLootChest;
import tictac7x.storage.utils.ItemContainerId;
import tictac7x.storage.utils.Provider;
import tictac7x.storage.utils.WidgetId;

import javax.inject.Inject;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@PluginDescriptor(
	name = "Storage",
	description = "Show overlays of inventory and bank",
	tags = { "storage", "bank", "inventory", "item", "poh" }
)
public class TicTac7xStoragePlugin extends Plugin {
	private String pluginVersion = "v0.6.3";
	private String pluginMessage = "" +
		"<colHIGHLIGHT>Storage " + pluginVersion + ":<br>" +
		"<colHIGHLIGHT>* Fix deposit box banking bug."
	;

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

	private Provider provider;

	private List<Storage> storages;

	private StorageOverlay[] storageOverlays;

	private StoragePanel storagePanel;

	private PanelNavigator panelNavigator;

	private LunarLootChest lunarLootChest;

	@Override
	protected void startUp() {
		configMigration();
		provider = new Provider(client, clientThread, configManager, itemManager, config);

		storages = new ArrayList<>();

		final BankStorage bankStorage = new BankStorage(provider);
		storages.add(bankStorage);

		final Storage inventoryStorage = new Storage(ItemContainerId.INVENTORY);
		storages.add(inventoryStorage);

		final ConfigStorage homeStorage = new ConfigStorage(TicTac7xStorageConfig.home, ItemContainerId.HOME, provider);
		storages.add(homeStorage);

		lunarLootChest = new LunarLootChest(ItemContainerId.LUNAR_LOOT_CHEST, bankStorage, provider);
		storages.add(lunarLootChest);

		new DepositBox(inventoryStorage, bankStorage, provider);

		storageOverlays = new StorageOverlay[]{
			new InventoryOverlay(TicTac7xStorageConfig.inventory, inventoryStorage, WidgetId.INVENTORY, provider),
			new BankOverlay(TicTac7xStorageConfig.bank, bankStorage, WidgetId.BANK, provider)
		};
		for (final StorageOverlay storageOverlay : storageOverlays) {
			overlayManager.add(storageOverlay);
		}

		// Panel
		storagePanel = new StoragePanel(Arrays.asList(bankStorage, homeStorage), provider);
		panelNavigator = new PanelNavigator(clientToolbar, config, storagePanel);

		// Load storage items from config.
		for (final Storage storage : storages) {
			if (storage instanceof ConfigStorage) {
				((ConfigStorage) storage).loadFromConfig(itemManager);
			}
		}
	}

	@Override
	protected void shutDown() {
		panelNavigator.shutDown();

		for (final StorageOverlay storageOverlay : storageOverlays) {
			overlayManager.remove(storageOverlay);
		}
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		for (final Storage storage : storages) {
			if (event.getContainerId() != storage.itemContainerId) continue;
			final List<StorageItem> items = new ArrayList<>();

			for (final Item item : event.getItemContainer().getItems()) {
				if (item.getId() == -1) continue;
				final ItemComposition itemComposition = itemManager.getItemComposition(item.getId());

				// Valid item.
				items.add(new StorageItem(
					itemComposition.getPlaceholderTemplateId() != -1 ? itemComposition.getPlaceholderId() : item.getId(),
					itemComposition.getPlaceholderTemplateId() != -1 ? 0 : item.getQuantity(),
					itemComposition.getName()
				));
			}

			storage.addItems(items);
		}
	}

	@Subscribe
	public void onWidgetLoaded(final WidgetLoaded event) {
		lunarLootChest.onWidgetLoaded(event);
	}

	@Subscribe
	public void onWidgetClosed(final WidgetClosed event) {
		lunarLootChest.onWidgetClosed(event);
	}

	@Subscribe
	public void onMenuOptionClicked(final MenuOptionClicked event) {
		lunarLootChest.onMenuOptionClicked(event);
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		if (!event.getGroup().equals(TicTac7xStorageConfig.group)) return;

		panelNavigator.onConfigChanged(event);
		lunarLootChest.onConfigChanged(event);

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

	public static Optional<Widget> getWidget(final int[] ids, final Client client) {
		return Optional.ofNullable(client.getWidget(ids[0], ids[1]));
	}

	public static Optional<Widget> getWidget(final int id1, final int id2, final int id3, final Client client) {
		final Optional<Widget> widget = Optional.ofNullable(client.getWidget(id1, id2));

		if (widget.isPresent()) {
			return Optional.ofNullable(widget.get().getChild(id3));
		} else {
			return Optional.empty();
		}
	}

	private static final Map<String, ImageIcon> iconCache = new HashMap<>();

	public static ImageIcon getCachedIcon(final int itemId, final int itemQuantity, final ItemManager itemManager) {
		final String multiKey = itemId + "_" + itemQuantity;

		if (!iconCache.containsKey(multiKey)) {
			iconCache.put(multiKey, new ImageIcon(itemManager.getImage(itemId, itemQuantity, true)));
		}

		return iconCache.get(multiKey);
	}
}
