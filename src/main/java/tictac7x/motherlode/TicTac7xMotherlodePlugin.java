package tictac7x.motherlode;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.TileObject;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import tictac7x.motherlode.oreveins.OreVeins;
import tictac7x.motherlode.rockfalls.Rockfalls;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Motherlode Mine Improved",
	description = "Better indicators for ore veins",
	tags = { "motherlode", "prospector", "golden", "nugget" },
	conflicts = {"Motherlode Mine", "MLM Mining Markers"}
)
public class TicTac7xMotherlodePlugin extends Plugin {
	private final String pluginVersion = "v0.4.3";
	private final String pluginMessage = "" +
		"<colHIGHLIGHT>Motherlode Mine Improved " + pluginVersion + ":<br>" +
		"<colHIGHLIGHT>* Needed paydirt improvements."
	;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ConfigManager configManager;

	@Inject
	private TicTac7xMotherlodeConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Notifier notifier;

	@Inject
	private ChatMessageManager chatMessageManager;

	private Character character;
	private Bank bank;
	private Inventory inventory;
	private Hopper hopper;
	private OreVeins oreVeins;
	private Rockfalls rockfalls;
	private Sack sack;
	private Motherlode motherlode;
	private Widget widget;

	@Provides
	TicTac7xMotherlodeConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TicTac7xMotherlodeConfig.class);
	}

	@Override
	protected void startUp() {
		character = new Character(client);
		bank = new Bank(configManager, config);
		inventory = new Inventory();
		hopper = new Hopper(client, inventory);
		sack = new Sack(client);
		motherlode = new Motherlode(client, clientThread, notifier, config, bank, inventory, sack, hopper);
		widget = new Widget(client, config, motherlode, character);
		oreVeins = new OreVeins(config, character, motherlode);
		rockfalls = new Rockfalls(config, character);

		overlayManager.add(oreVeins);
		overlayManager.add(rockfalls);
		overlayManager.add(widget);
	}

	@Override
	protected void shutDown() {
		widget.shutDown();
		overlayManager.remove(oreVeins);
		overlayManager.remove(rockfalls);
		overlayManager.remove(widget);
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		oreVeins.onGameStateChanged(event);
		rockfalls.onGameStateChanged(event);
		sendMessageAboutPluginVersion(event);
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		if (!character.isInMotherlode()) return;
		inventory.onItemContainerChanged(event);
		bank.onItemContainerChanged(event);
		motherlode.onItemContainerChanged(event);
	}

	@Subscribe
	public void onChatMessage(final ChatMessage event) {
		if (!character.isInMotherlode()) return;
		motherlode.onChatMessage(event);
	}

	@Subscribe
	public void onWallObjectSpawned(final WallObjectSpawned event) {
		if (!character.isInMotherlode()) return;
		oreVeins.onWallObjectSpawned(event);
	}

	@Subscribe
	public void onWallObjectDespawned(final WallObjectDespawned event) {
		if (!character.isInMotherlode()) return;
		oreVeins.onWallObjectDespawned(event);
	}

	@Subscribe
	public void onAnimationChanged(final AnimationChanged event) {
		if (!character.isInMotherlode()) return;
		oreVeins.onAnimationChanged(event);
		hopper.onAnimationChanged(event);
	}

	@Subscribe
	public void onGameObjectSpawned(final GameObjectSpawned event) {
		if (!character.isInMotherlode()) return;
		rockfalls.onGameObjectSpawned(event);
	}

	@Subscribe
	public void onGameObjectDespawned(final GameObjectDespawned event) {
		if (!character.isInMotherlode()) return;
		rockfalls.onGameObjectDespawned(event);
	}

	@Subscribe
	public void onGameTick(final GameTick event) {
		character.checkIsInMotherlode();
		if (!character.isInMotherlode()) return;

		character.onGameTick();
		oreVeins.onGameTick();
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		if (!character.isInMotherlode()) return;
		widget.onConfigChanged(event);
	}

	@Subscribe
	public void onWidgetLoaded(final WidgetLoaded event) {
		if (!character.isInMotherlode()) return;
		widget.onWidgetLoaded(event);
	}

	@Subscribe
	public void onVarbitChanged(final VarbitChanged event) {
		if (!character.isInMotherlode()) return;
		hopper.onVarbitChanged(event);
		motherlode.onVarbitChanged(event);
	}

	private void sendMessageAboutPluginVersion(final GameStateChanged event) {
		if (event.getGameState() == GameState.LOGGED_IN && !config.getVersion().equals(pluginVersion)) {
			configManager.setConfiguration(TicTac7xMotherlodeConfig.group, TicTac7xMotherlodeConfig.version, pluginVersion);
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(pluginMessage)
				.build()
			);
		}
	}

	public static String getWorldObjectKey(final TileObject tileObject) {
		return tileObject.getWorldLocation().getX() + "_" + tileObject.getWorldLocation().getY();
	}
}
