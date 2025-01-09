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
public class TithePlugin extends Plugin {
	private String plugin_version = "v0.4.1";
	private String plugin_message = "" +
		"<colHIGHLIGHT>Tithe Farm Improved " + plugin_version + ":<br>" +
		"<colHIGHLIGHT>* Points calculation formula is now more precise.";

	private static final int SEED_TABLE = 27430;
	private boolean in_tithe_farm = false;

	@Inject
	private TitheConfig config;

	@Inject
	private Client client;

	@Inject
	private ClientThread client_thread;

	@Inject
	private OverlayManager overlays;

	@Inject
	private ItemManager items;

	@Inject
	private ConfigManager configs;

	@Inject
	private ChatMessageManager chat_messages;

	@Provides
	TitheConfig provideConfig(final ConfigManager configs) {
		return configs.getConfig(tictac7x.tithe.TitheConfig.class);
	}

	private TitheOverlayPlants overlay_plants;
	private TitheOverlayPoints overlay_points;
	private TitheOverlayPatches overlay_patches;
	private TitheOverlayInventory overlay_inventory;

	@Override
	protected void startUp() {
		overlay_points = new TitheOverlayPoints(this, config, client);
		overlay_patches = new TitheOverlayPatches(this, config, client);
		overlay_plants = new TitheOverlayPlants(this, config);
		overlay_inventory = new TitheOverlayInventory(this, config);

		overlays.add(overlay_points);
		overlays.add(overlay_patches);
		overlays.add(overlay_plants);
		overlays.add(overlay_inventory);

		overlay_points.startUp();
	}

	@Override
	protected void shutDown() {
		overlay_points.shutDown();

		overlays.remove(overlay_points);
		overlays.remove(overlay_patches);
		overlays.remove(overlay_plants);
		overlays.remove(overlay_inventory);
	}

	@Subscribe
	public void onGameObjectSpawned(final GameObjectSpawned event) {
		overlay_plants.onGameObjectSpawned(event);
		if (event.getGameObject().getId() == SEED_TABLE) this.in_tithe_farm = true;
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		overlay_inventory.onItemContainerChanged(event);
	}

	@Subscribe
	public void onVarbitChanged(final VarbitChanged event) {
		overlay_points.onVarbitChanged(event);
	}

	@Subscribe
	public void onGameTick(final GameTick event) {
		overlay_plants.onGameTick();
	}

	@Subscribe
	public void onWidgetLoaded(final WidgetLoaded event) {
		overlay_points.onWidgetLoaded(event);
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		overlay_points.onConfigChanged(event);
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		// Plugin update message.
		if (event.getGameState() == GameState.LOGGED_IN && !config.getVersion().equals(plugin_version)) {
			configs.setConfiguration(TitheConfig.group, TitheConfig.version, plugin_version);
			chat_messages.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(plugin_message)
				.build()
			);
		}

		if (event.getGameState() == GameState.LOADING) this.in_tithe_farm = false;
	}

	public boolean inTitheFarm() {
		return in_tithe_farm ;
	}

	public int fruitsInInventory() {
		return overlay_inventory.fruits_inventory;
	}
}
