package tictac7x.gotr;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.events.*;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import tictac7x.gotr.overlays.*;
import tictac7x.gotr.store.*;
import tictac7x.gotr.widgets.BarriersWidget;
import tictac7x.gotr.widgets.PointsWidget;
import tictac7x.gotr.widgets.InactivePortalWidget;

import javax.inject.Inject;
import java.awt.*;

@Slf4j
@PluginDescriptor(
	name = "Guardians of the Rift Improved",
	description = "Show charges of various items",
	tags = {
		"gotr",
		"guardian",
		"rift",
		"abyssal",
	}
)
public class TicTac7xGotrImprovedPlugin extends Plugin {
	private final String pluginVersion = "v0.1";
	private final String pluginMessage = "" +
		"<colHIGHLIGHT>GOTR Improved " + pluginVersion + ":<br>";

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ItemManager itemManager;

	@Inject
	private ConfigManager configManager;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private TicTac7xGotrImprovedConfig config;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private Notifier notifier;

	@Inject
	private ModelOutlineRenderer modelOutlineRenderer;

	@Inject
	private SpriteManager spriteManager;

	private Player player;
	private Teleporters teleporters;
	private GreatGuardianOverlay greatGuardianOverlay;
	private Guardians guardians;
	private Portal portal;
	private Inventory inventory;
	private Points points;
	private EntranceBarrier entranceBarrier;
	private Notifications notifications;
	private Creatures creatures;
	private Barriers barriers;

	private PortalOverlay portalOverlayOverlay;
	private GuardiansOverlay guardiansOverlay;
	private TeleportersOverlay teleportersOverlay;
	private UnchargedCellsBenchOverlay unchargedCellsBenchOverlay;
	private BarriersWidget barriersWidget;
	private RewardsGuardianOverlay rewardsGuardianOverlay;

	private InactivePortalWidget inactivePortalWidget;
	private PointsWidget pointsWidget;

	@Provides
	TicTac7xGotrImprovedConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TicTac7xGotrImprovedConfig.class);
	}

	@Override
	protected void startUp() {
		player = new Player(client);
		inventory = new Inventory();
		notifications = new Notifications(notifier, inventory, config);
		teleporters = new Teleporters();
		guardians = new Guardians(client);
		portal = new Portal(client, notifications);
		points = new Points(client, configManager, config);
		entranceBarrier = new EntranceBarrier(config, player);
		creatures = new Creatures(client, notifications, config);
		barriers = new Barriers();

		portalOverlayOverlay = new PortalOverlay(client, portal);
		guardiansOverlay = new GuardiansOverlay(modelOutlineRenderer, config, guardians, inventory);
		greatGuardianOverlay = new GreatGuardianOverlay(client, itemManager, modelOutlineRenderer, config, inventory);
		teleportersOverlay = new TeleportersOverlay(client, itemManager, modelOutlineRenderer, config, teleporters, inventory);
		unchargedCellsBenchOverlay = new UnchargedCellsBenchOverlay(client, modelOutlineRenderer, config, inventory);
		barriersWidget = new BarriersWidget(client, config, barriers);
		rewardsGuardianOverlay = new RewardsGuardianOverlay(client, config, points);

		inactivePortalWidget = new InactivePortalWidget(client, spriteManager, portal);
		pointsWidget = new PointsWidget(client, config, points);

		overlayManager.add(portalOverlayOverlay);
		overlayManager.add(guardiansOverlay);
		overlayManager.add(greatGuardianOverlay);
		overlayManager.add(teleportersOverlay);
		overlayManager.add(unchargedCellsBenchOverlay);
		overlayManager.add(inactivePortalWidget);
		overlayManager.add(pointsWidget);
		overlayManager.add(barriersWidget);
		overlayManager.add(rewardsGuardianOverlay);
	}

	@Override
	protected void shutDown() {
		overlayManager.remove(portalOverlayOverlay);
		overlayManager.remove(guardiansOverlay);
		overlayManager.remove(greatGuardianOverlay);
		overlayManager.remove(teleportersOverlay);
		overlayManager.remove(unchargedCellsBenchOverlay);
		overlayManager.remove(inactivePortalWidget);
		overlayManager.remove(pointsWidget);
		overlayManager.remove(barriersWidget);
		overlayManager.remove(rewardsGuardianOverlay);

		pointsWidget.shutDown();
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		inventory.onItemContainerChanged(event.getItemContainer());
	}

	@Subscribe
	public void onGameObjectSpawned(final GameObjectSpawned event) {
		final GameObject gameObject = event.getGameObject();

		teleporters.onGameObjectSpawned(gameObject);
		portalOverlayOverlay.onGameObjectSpawned(gameObject);
		guardiansOverlay.onGameObjectSpawned(gameObject);
		unchargedCellsBenchOverlay.onGameObjectSpawned(gameObject);
		notifications.onGameObjectSpawned(gameObject);
		rewardsGuardianOverlay.onGameObjectSpawned(event);
		barriers.onGameObjectSpawned(event);
	}

	@Subscribe
	public void onGameObjectDespawned(final GameObjectDespawned event) {
		final GameObject gameObject = event.getGameObject();

		portalOverlayOverlay.onGameObjectDespawned(gameObject);
		guardiansOverlay.onGameObjectDespawned(gameObject);
		unchargedCellsBenchOverlay.onGameObjectDespawned(gameObject);
		rewardsGuardianOverlay.onGameObjectDespawned(gameObject);
	}

	@Subscribe
	public void onChatMessage(final ChatMessage message) {
		message.setMessage(message.getMessage().replaceAll("</?col.*?>", ""));

		barriers.onChatMessage(message);
		teleporters.onChatMessage(message);
		points.onChatMessage(message);
		notifications.onChatMessage(message);
		portal.onChatMessage(message);
		creatures.onChatMessage(message);
	}

	@Subscribe
	public void onGroundObjectSpawned(final GroundObjectSpawned event) {
		clientThread.invokeAtTickEnd(() -> {
			barriers.groundObjectSpawned(event);
		});
	}

	@Subscribe
	public void onNpcSpawned(final NpcSpawned event) {
		greatGuardianOverlay.onNpcSpawned(event);

		clientThread.invokeAtTickEnd(() -> {
			barriers.onNpcSpawned(event);
		});
	}

	@Subscribe
	public void onNpcDespawned(final NpcDespawned event) {
		greatGuardianOverlay.onNpcDespawned(event.getNpc());
	}

	@Subscribe
	public void onGameTick(final GameTick ignored) {
		teleporters.onGameTick();
		portal.onGameTick();
		guardians.onGameTick();
		creatures.onGameTick();

		points.onGameTick();
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		final GameState gameState = event.getGameState();

		teleporters.onGameStateChanged(gameState);
		greatGuardianOverlay.onGameStateChanged(gameState);
		portalOverlayOverlay.onGameStateChanged(gameState);
		guardiansOverlay.onGameStateChanged(gameState);
		unchargedCellsBenchOverlay.onGameStateChanged(gameState);
		rewardsGuardianOverlay.onGameStateChanged(event);
	}

	@Subscribe
	public void onMenuEntryAdded(final MenuEntryAdded event) {
		entranceBarrier.onMenuEntryAdded(event.getMenuEntry());
	}

	@Subscribe
	public void onWidgetLoaded(final WidgetLoaded event) {
		points.onWidgetLoaded(event);
		player.onWidgetLoaded(event);

		pointsWidget.onWidgetLoaded(event);
	}

	public static void drawCenteredString(final Graphics graphics, final String text, final Rectangle rectangle, final Color color, final Font font) {
		try {
			graphics.setFont(font);
			final FontMetrics metrics = graphics.getFontMetrics();

			final int x = rectangle.x + (rectangle.width - metrics.stringWidth(text)) / 2;
			final int y = rectangle.y + ((rectangle.height - metrics.getHeight()) / 2) + metrics.getAscent();

			graphics.setColor(Color.BLACK);
			graphics.drawString(text, x + 1, y + 1);

			graphics.setColor(color);
			graphics.drawString(text, x, y);
		} catch (final Exception ignored) {}
	}
}

