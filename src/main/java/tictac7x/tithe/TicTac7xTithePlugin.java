package tictac7x.tithe;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Tithe Farm Improved",
	description = "Improve overall experience for Tithe farm",
	tags = { "tithe", "farm" },
	conflicts = "Tithe Farm"
)
public class TicTac7xTithePlugin extends Plugin {
	private String pluginVersion = "v0.4.1";
	private String pluginMessage = "" +
		"<colHIGHLIGHT>Tithe Farm Improved " + pluginVersion + ":<br>" +
		"<colHIGHLIGHT>* Points calculation formula is now more precise.";

	private static final int SEED_TABLE = 27430;
	private boolean in_tithe_farm = false;

	@Inject
	private TicTac7xTitheConfig config;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Provides
	TicTac7xTitheConfig provideConfig(final ConfigManager configs) {
		return configs.getConfig(TicTac7xTitheConfig.class);
	}

	private Inventory inventory;
	private PlantsManager plantsManager;
	private OverlayPlants overlayPlants;
	private OverlayPoints overlayPoints;
	private OverlayPatches overlayPatches;
	private OverlayInventory overlayInventory;

	@Override
	protected void startUp() {
		inventory = new Inventory(this);
		plantsManager = new PlantsManager(this, config);

		overlayPoints = new OverlayPoints(this, config, client, inventory);
		overlayPatches = new OverlayPatches(this, config, client);
		overlayPlants = new OverlayPlants(this, plantsManager);
		overlayInventory = new OverlayInventory(this, config);

		overlayManager.add(overlayPoints);
		overlayManager.add(overlayPatches);
		overlayManager.add(overlayPlants);
		overlayManager.add(overlayInventory);

		overlayPoints.startUp();
	}

	@Override
	protected void shutDown() {
		overlayPoints.shutDown();

		overlayManager.remove(overlayPoints);
		overlayManager.remove(overlayPatches);
		overlayManager.remove(overlayPlants);
		overlayManager.remove(overlayInventory);
	}

	@Subscribe
	public void onGameObjectSpawned(final GameObjectSpawned event) {
		plantsManager.onGameObjectSpawned(event);
		if (event.getGameObject().getId() == SEED_TABLE) this.in_tithe_farm = true;
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		inventory.onItemContainerChanged(event);
	}

	@Subscribe
	public void onVarbitChanged(final VarbitChanged event) {
		overlayPoints.onVarbitChanged(event);
	}

	@Subscribe
	public void onGameTick(final GameTick event) {
		plantsManager.onGameTick();
	}

	@Subscribe
	public void onWidgetLoaded(final WidgetLoaded event) {
		overlayPoints.onWidgetLoaded(event);
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		overlayPoints.onConfigChanged(event);
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		// Plugin update message.
		if (event.getGameState() == GameState.LOGGED_IN && !config.getVersion().equals(pluginVersion)) {
			configManager.setConfiguration(TicTac7xTitheConfig.group, TicTac7xTitheConfig.version, pluginVersion);
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(pluginMessage)
				.build()
			);
		}

		if (event.getGameState() == GameState.LOADING) this.in_tithe_farm = false;
	}

	public boolean inTitheFarm() {
		return in_tithe_farm ;
	}
}
