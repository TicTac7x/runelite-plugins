package tictac7x.balloon;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Balloon Transport System",
	description = "Show amount of logs stored in the balloon transport system storages.",
	tags = { "balloon", "transport", "logs", "storage" }
)
public class TicTac7xBalloonPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ConfigManager configManager;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ItemManager itemManager;

	@Inject
	private TicTac7xBalloonConfig config;

	private Balloon balloon;
	private BalloonStorage balloonStorage;
	private BalloonInfoBox[] balloonInfoBoxes;

	@Provides
	TicTac7xBalloonConfig provideConfig(final ConfigManager configManager) {
		return configManager.getConfig(TicTac7xBalloonConfig.class);
	}

	@Override
	protected void startUp() {
		balloon = new Balloon();
		balloonStorage = new BalloonStorage(configManager);
		balloonInfoBoxes = new BalloonInfoBox[]{
			new BalloonInfoBox(ItemID.LOGS, TicTac7xBalloonConfig.logs_regular, "Regular logs - Entrana / Taverley", configManager, config, itemManager, balloon, this),
			new BalloonInfoBox(ItemID.OAK_LOGS, TicTac7xBalloonConfig.logs_oak, "Oak logs - Crafting Guild", configManager, config, itemManager, balloon, this),
			new BalloonInfoBox(ItemID.WILLOW_LOGS, TicTac7xBalloonConfig.logs_willow, "Willow logs - Varrock", configManager, config, itemManager, balloon, this),
			new BalloonInfoBox(ItemID.YEW_LOGS, TicTac7xBalloonConfig.logs_yew, "Yew logs - Castle Wars", configManager, config, itemManager, balloon, this),
			new BalloonInfoBox(ItemID.MAGIC_LOGS, TicTac7xBalloonConfig.logs_magic, "Magic logs - Grand Tree", configManager, config, itemManager, balloon, this),
		};

		for (final BalloonInfoBox infobox : balloonInfoBoxes) {
			infoBoxManager.addInfoBox(infobox);
		}
	}

	@Override
	protected void shutDown() {
		for (final BalloonInfoBox infobox : balloonInfoBoxes) {
			infoBoxManager.removeInfoBox(infobox);
		}
	}

	@Subscribe
	public void onChatMessage(final ChatMessage event) {
		balloonStorage.onChatMessage(event);
	}

	@Subscribe
	public void onGameObjectSpawned(final GameObjectSpawned event) {
		balloon.onGameObjectSpawned(event);
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		balloon.onGameStateChanged(event);
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		for (final BalloonInfoBox infobox : balloonInfoBoxes) {
			infobox.onConfigChanged(event);
		}
	}
}
