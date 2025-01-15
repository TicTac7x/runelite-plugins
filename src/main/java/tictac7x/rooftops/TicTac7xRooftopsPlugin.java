package tictac7x.rooftops;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.*;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import tictac7x.rooftops.course.Course;
import tictac7x.rooftops.course.CoursesManager;
import tictac7x.rooftops.courses.*;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Rooftop Agility Improved",
	description = "Improved clickboxes for rooftop agility courses",
	tags = { "roof", "rooftop", "agility", "mark", "grace", "graceful" }
)
public class TicTac7xRooftopsPlugin extends Plugin {
	private final String pluginVersion = "v0.6.2";
	private final String pluginMessage = "" +
		"<colHIGHLIGHT>Rooftop Agility Improved " + pluginVersion + ":<br>" +
		"<colHIGHLIGHT>* Varlamore course added.<br>" +
		"<colHIGHLIGHT>* Ape Atoll course added.<br>" +
		"<colHIGHLIGHT>* Multi paths support and skill level checks."
	;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlays;

	@Inject
	private ConfigManager configManager;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private TicTac7xRooftopsConfig config;

	private CoursesManager coursesManager;

	private Overlay overlayRooftops;

	@Provides
	TicTac7xRooftopsConfig provideConfig(final ConfigManager configManager) {
		return configManager.getConfig(TicTac7xRooftopsConfig.class);
	}

	@Override
	protected void startUp() {
		coursesManager = new CoursesManager(client, config, new Course[]{
			// Rooftops with marks of grace.
			new Draynor(),
			new AlKharid(),
			new Varrock(),
			new Canifis(),
			new Falador(),
			new Seers(),
			new Pollnivneach(),
			new Rellekka(),
			new Ardougne(),

			// Other.
			new Varlamore(),
			new ApeAtoll()
		});
		overlayRooftops = new Overlay(client, config, coursesManager);
		overlays.add(overlayRooftops);
	}

	@Override
	protected void shutDown() {
		overlays.remove(overlayRooftops);
	}

	@Subscribe
	public void onGameObjectSpawned(final GameObjectSpawned event) {
		coursesManager.onTileObjectSpawned(event.getGameObject());
	}

	@Subscribe
	public void onWallObjectSpawned(final WallObjectSpawned event) {
		coursesManager.onTileObjectSpawned(event.getWallObject());
	}

	@Subscribe
	public void onGroundObjectSpawned(final GroundObjectSpawned event) {
		coursesManager.onTileObjectSpawned(event.getGroundObject());
	}

	@Subscribe
	public void onDecorativeObjectSpawned(final DecorativeObjectSpawned event) {
		coursesManager.onTileObjectSpawned(event.getDecorativeObject());
	}

	@Subscribe
	public void onItemSpawned(final ItemSpawned event) {
		coursesManager.onItemSpawned(event);
	}

	@Subscribe
	public void onItemDespawned(final ItemDespawned event) {
		coursesManager.onItemDespawned(event);
	}

	@Subscribe
	public void onChatMessage(final ChatMessage event) {
		coursesManager.onChatMessage(event);
	}

	@Subscribe
	public void onStatChanged(final StatChanged event) {
		coursesManager.onStatChanged(event);
	}

	@Subscribe
	public void onHitsplatApplied(final HitsplatApplied event) {
		coursesManager.onHitsplatApplied(event);
	}

	@Subscribe
	public void onGameTick(final GameTick gametick) {
		coursesManager.onGameTick(gametick);
	}

	@Subscribe
	public void onMenuOptionClicked(final MenuOptionClicked event) {
		coursesManager.onMenuOptionClicked(event);
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		coursesManager.onGameStateChanged(event);

		// Send message about plugin updates for once.
		if (event.getGameState() == GameState.LOGGED_IN && !config.getVersion().equals(pluginVersion)) {
			configManager.setConfiguration(TicTac7xRooftopsConfig.group, TicTac7xRooftopsConfig.version, pluginVersion);
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(pluginMessage)
				.build()
			);
		}
	}
}
