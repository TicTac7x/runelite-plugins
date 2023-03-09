package tictac7x.charges;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import com.google.inject.Provides;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GraphicChanged;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.infoboxes.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
@PluginDescriptor(
	name = "Item Charges Improved",
	description = "Show charges of various items",
	tags = { "charges" }
)
public class ChargesImprovedPlugin extends Plugin {
	private final String plugin_version = "v0.2.3";
	private final String plugin_message = "" +
		"<colHIGHLIGHT>Item Charges Improved " + plugin_version + ":<br>" +
		"<colHIGHLIGHT>* Celestial ring added<br>" +
		"<colHIGHLIGHT>* Infoboxes that you want to hide need to be selected in the settings<br>";

	private final int VARBIT_MINUTES = 8354;

	@Inject
	private Client client;

	@Inject
	private ClientThread client_thread;

	@Inject
	private ItemManager items;

	@Inject
	private ConfigManager configs;

	@Inject
	private InfoBoxManager infoboxes;

	@Inject
	private OverlayManager overlays;

	@Inject
	private ChargesImprovedConfig config;

	@Inject
	private ChatMessageManager chat_messages;

	@Provides
	ChargesImprovedConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(ChargesImprovedConfig.class);
	}

	private ChargedItemsOverlay overlay_charged_items;

	private ChargedItemInfoBox[] infoboxes_charged_items;

	private final ZoneId timezone = ZoneId.of("Europe/London");
	private String date = LocalDateTime.now(timezone).format(DateTimeFormatter.ISO_LOCAL_DATE);

	@Override
	protected void startUp() {
		infoboxes_charged_items = new ChargedItemInfoBox[]{
			new W_Arclight(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new W_TridentOfTheSeas(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new W_SkullSceptre(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new W_IbansStaff(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new W_PharaohsSceptre(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new W_BryophytasStaff(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new W_SanguinestiStaff(client, client_thread, configs, items, infoboxes, chat_messages, config, this),

			new S_CrystalShield(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new S_FaladorShield(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new S_Chronicle(client, client_thread, configs, items, infoboxes, chat_messages, config, this),

			new J_BraceletOfSlaughter(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new J_ExpeditiousBracelet(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new J_CelestialRing(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new J_RingOfSuffering(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new J_SlayerRing(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new J_XericsTalisman(client, client_thread, configs, items, infoboxes, chat_messages, config, this),

			new U_BottomlessCompostBucket(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new U_AshSanctifier(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new U_BoneCrusher(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new U_GricollersCan(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new U_SoulBearer(client, client_thread, configs, items, infoboxes, chat_messages, config, this),

			new BarrowsAhrimsHood(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsAhrimsRobetop(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsAhrimsRobeskirt(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsAhrimsStaff(client, client_thread, configs, items, infoboxes, chat_messages, config, this),

			new BarrowsDharoksHelm(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsDharoksPlatebody(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsDharoksPlatelegs(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsDharoksGreataxe(client, client_thread, configs, items, infoboxes, chat_messages, config, this),

			new BarrowsGuthansHelm(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsGuthansPlatebody(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsGuthansChainskirt(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsGuthansWarspear(client, client_thread, configs, items, infoboxes, chat_messages, config, this),

			new BarrowsKarilsCoif(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsKarilsLeathertop(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsKarilsLeatherskirt(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsKarilsCrossbow(client, client_thread, configs, items, infoboxes, chat_messages, config, this),

			new BarrowsToragsHelm(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsToragsPlatebody(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsToragsPlatelegs(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsToragsHammers(client, client_thread, configs, items, infoboxes, chat_messages, config, this),

			new BarrowsVeracsHelm(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsVeracsBrassard(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsVeracsPlateskirt(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
			new BarrowsVeracsFlail(client, client_thread, configs, items, infoboxes, chat_messages, config, this),
		};
		overlay_charged_items = new ChargedItemsOverlay(items, config, infoboxes_charged_items);

		overlays.add(overlay_charged_items);
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infoboxes.addInfoBox(infobox));
	}

	@Override
	protected void shutDown() {
		overlays.remove(overlay_charged_items);
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infoboxes.removeInfoBox(infobox));
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		if (
			event.getContainerId() == InventoryID.INVENTORY.getId() ||
			event.getContainerId() == InventoryID.EQUIPMENT.getId()
		) {
			final ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
			final ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);

			if (inventory != null && equipment != null) {
				Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onItemContainersChanged(inventory, equipment));

				// We need to know about items to show messages about resetting charges.
				if (!config.getResetDate().equals(date)) {
					resetCharges(date);
				}
			}
		}
	}

	@Subscribe
	public void onChatMessage(final ChatMessage event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onChatMessage(event));
//		System.out.println("MESSAGE | " +
//			"type: " + event.getType().name() +
//			", message: " + event.getMessage().replaceAll("</?col.*?>", "") +
//			", sender: " + event.getSender()
//		);
	}

	@Subscribe
	public void onAnimationChanged(final AnimationChanged event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onAnimationChanged(event));
//		if (event.getActor() == client.getLocalPlayer()) {
//			System.out.println("ANIMATION | " +
//				"id: " + event.getActor().getAnimation()
//			);
//		}
	}

	@Subscribe
	public void onGraphicChanged(final GraphicChanged event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onGraphicChanged(event));
//		if (event.getActor() == client.getLocalPlayer()) {
//			System.out.println("GRAPHIC | " +
//				"id: " + event.getActor().getGraphic()
//			);
//		}
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onConfigChanged(event));
//		if (event.getGroup().equals(ChargesImprovedConfig.group)) {
//			System.out.println("CONFIG | " +
//				"key: " + event.getKey() +
//				", old value: " + event.getOldValue() +
//				", new value: " + event.getNewValue()
//			);
//		}
	}

	@Subscribe
	public void onHitsplatApplied(final HitsplatApplied event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onHitsplatApplied(event));
//		System.out.println("HITSPLAT | " +
//			"actor: " + (event.getActor() == client.getLocalPlayer() ? "self" : "enemy") +
//			", type: " + event.getHitsplat().getHitsplatType() +
//			", amount:" + event.getHitsplat().getAmount() +
//			", others = " + event.getHitsplat().isOthers() +
//			", mine = " + event.getHitsplat().isMine()
//		);
	}

	@Subscribe
	public void onWidgetLoaded(final WidgetLoaded event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onWidgetLoaded(event));
//		System.out.println("WIDGET | " +
//			"group: " + event.getGroupId()
//		);
	}

	@Subscribe
	public void onMenuOptionClicked(final MenuOptionClicked event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onMenuOptionClicked(event));
//		System.out.println("OPTION | " +
//			"option: " + event.getMenuOption() +
//			", target: " + event.getMenuTarget() +
//			", action name: " + event.getMenuAction().name() +
//			", action id: " + event.getMenuAction().getId()
//		);
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		if (event.getGameState() != GameState.LOGGED_IN) return;

		// Send message about plugin updates for once.
		if (!config.getVersion().equals(plugin_version)) {
			configs.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.version, plugin_version);
			chat_messages.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(plugin_message)
				.build()
			);
		}
	}

	@Subscribe
	public void onVarbitChanged(final VarbitChanged event) {
		// If server minutes are 0, it's a new day!
		if (event.getVarbitId() == VARBIT_MINUTES && client.getGameState() == GameState.LOGGED_IN && event.getValue() == 0) {
			final String date = LocalDateTime.now(timezone).format(DateTimeFormatter.ISO_LOCAL_DATE);
			resetCharges(date);
		}
	}

	private void resetCharges(final String date) {
		configs.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.date, date);
		Arrays.stream(infoboxes_charged_items).forEach(ChargedItemInfoBox::resetCharges);
	}

	public static String getChargesMinified(final int charges) {
		if (charges == -1) return "?";
		if (charges > 1000000) return charges / 1000000 + "M";
		if (charges > 1000) return charges / 1000 + "K";
		return String.valueOf(charges);
	}
}

