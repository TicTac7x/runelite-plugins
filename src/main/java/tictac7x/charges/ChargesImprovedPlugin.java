package tictac7x.charges;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.*;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBox;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.overlays.ChargedItemInfobox;
import tictac7x.charges.item.overlays.ChargedItemOverlay;
import tictac7x.charges.items.*;
import tictac7x.charges.items.barrows.*;
import tictac7x.charges.store.AdvancedMenuEntry;
import tictac7x.charges.store.Store;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@PluginDescriptor(
	name = "Item Charges Improved",
	description = "Show charges of various items",
	tags = {
		"charges",
		"barrows",
		"crystal",
		"ardougne",
		"coffing",
		"magic",
		"cape",
		"circlet",
		"bracelet",
		"clay",
		"expeditious",
		"flamtaer",
		"slaughter",
		"camulet",
		"celestial",
		"ring",
		"escape",
		"recoil",
		"shadow",
		"suffering",
		"slayer",
		"xeric",
		"talisman",
		"chronicle",
		"dragonfire",
		"falador",
		"kharedst",
		"memoirs",
		"ash",
		"sanctifier",
		"bone",
		"crusher",
		"bottomless",
		"compost",
		"bucket",
		"coal",
		"bag",
		"fish",
		"barrel",
		"fungicide",
		"spray",
		"gem",
		"gricoller",
		"can",
		"herb",
		"sack",
		"log",
		"basket",
		"ogre",
		"bellows",
		"seed",
		"box",
		"soul",
		"bearer",
		"teleport",
		"waterskin",
		"arclight",
		"bryophyta",
		"staff",
		"bow",
		"halberd",
		"iban",
		"pharaoh",
		"sceptre",
		"sanguinesti",
		"skull",
		"trident",
		"sea",
		"toxic",
		"jar",
		"tome",
		"fur",
		"meat",
		"pouch",
		"pursuit",
		"book",
		"scroll"
	}
)

public class ChargesImprovedPlugin extends Plugin implements KeyListener, MouseListener, MouseWheelListener {
	private final String pluginVersion = "v0.5.12";
	private final String pluginMessage = "" +
		"<colHIGHLIGHT>Item Charges Improved " + pluginVersion + ":<br>" +
		"<colHIGHLIGHT>* Plank sack added.<br>" +
		"<colHIGHLIGHT>* Colossal pouch added.<br>" +
		"<colHIGHLIGHT>* Ring of duelling added.<br>" +
		"<colHIGHLIGHT>* Log basket and fish barrel fixes.<br>" +
		"<colHIGHLIGHT>* Forestry kit shop support.<br>" +
		"<colHIGHLIGHT>* Master scroll book now has Colossal Wyrm teleport scrolls support."
	;

	private final int VARBIT_MINUTES = 8354;

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
	private ChargesImprovedConfig config;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Inject
	private TooltipManager tooltipManager;

	@Inject
	private KeyManager keyManager;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private Notifier notifier;
	
	@Inject
	private Gson gson;

	@Provides
	ChargesImprovedConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(ChargesImprovedConfig.class);
	}

	private Store store;

	private ChargedItemOverlay overlayChargedItems;

	private ChargedItemBase[] chargedItems;
	private List<InfoBox> chargedItemsInfoboxes = new ArrayList<>();

	private final ZoneId timezone = ZoneId.of("Europe/London");

	@Override
	protected void startUp() {
		keyManager.registerKeyListener(this);
		mouseManager.registerMouseListener(this);
		mouseManager.registerMouseWheelListener(this);
		configMigration();

		store = new Store(client, itemManager, configManager);

		chargedItems = new ChargedItemBase[]{
			// Weapons
			new W_Arclight(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_BowOfFaerdhinen(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_BryophytasStaff(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_CrystalBow(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_CrystalHalberd(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_EnchantedLyre(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_IbansStaff(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_PharaohsSceptre(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_SanguinestiStaff(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_SkullSceptre(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_SlayerStaffE(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_TridentOfTheSeas(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_VenatorBow(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_WarpedSceptre(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_WesternBanner(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Shields
			new S_Chronicle(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_CrystalShield(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_DragonfireShield(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_FaladorShield(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_KharedstMemoirs(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_TomeOfFire(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_TomeOfWater(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Boots
			new B_FremennikSeaBoots(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Helms
			new H_CircletOfWater(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new H_KandarinHeadgear(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Capes
			new C_ArdougneCloak(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new C_Coffin(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new C_ForestryKit(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new C_MagicCape(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Jewellery
			new J_BindingNecklace(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_BraceletOfClay(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_BraceletOfExpeditious(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_BraceletOfFlamtaer(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_BraceletOfSlaughter(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_Camulet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_DesertAmulet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_DigsitePendant(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_EscapeCrystal(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_NecklaceOfPassage(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_NecklaceOfPhoenix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_NecklaceOfDodgy(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_PendantOfAtes(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfCelestial(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfDueling(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfElements(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfExplorer(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfPursuit(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfRecoil(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfShadows(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfSlayer(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfSuffering(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_XericsTalisman(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Utilities
			new U_AshSanctifier(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_BoneCrusher(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_BottomlessCompostBucket(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_CoalBag(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_CrystalSaw(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_ColossalPouch(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_FishBarrel(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_FungicideSpray(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_FurPouch(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_GemBag(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_GricollersCan(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_HerbSack(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_HuntsmansKit(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_JarGenerator(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_LogBasket(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_MasterScrollBook(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_MeatPouch(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_OgreBellows(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_QuetzalWhistle(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_PlankSack(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_SeedBox(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_SoulBearer(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_StrangeOldLockpick(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_TackleBox(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_TeleportCrystal(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_Waterskin(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Foods
			new F_BlackWarlockMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new F_MoonlightMothMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new F_RubyHarvestMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new F_SapphireGlacialisMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new F_SnowyKnightMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new F_SunlightMothMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),


			// Crystal armor set
			new A_CrystalBody(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new A_CrystalHelm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new A_CrystalLegs(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Barrows armor sets
			new AhrimsHood(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new AhrimsRobetop(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new AhrimsRobeskirt(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new AhrimsStaff(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			new DharoksHelm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new DharoksPlatebody(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new DharoksPlatelegs(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new DharoksGreataxe(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			new GuthansHelm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new GuthansPlatebody(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new GuthansChainskirt(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new GuthansWarspear(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			new KarilsCoif(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new KarilsLeathertop(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new KarilsLeatherskirt(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new KarilsCrossbow(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			new ToragsHelm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new ToragsPlatebody(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new ToragsPlatelegs(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new ToragsHammers(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			new VeracsHelm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new VeracsBrassard(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new VeracsPlateskirt(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new VeracsFlail(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
		};

		store.setChargedItems(chargedItems);

		// Items overlays.
		overlayChargedItems = new ChargedItemOverlay(client, tooltipManager, itemManager, configManager, config, chargedItems);
		overlayManager.add(overlayChargedItems);

		// Items infoboxes.
		chargedItemsInfoboxes.clear();
		Arrays.stream(chargedItems).forEach(chargedItem -> chargedItemsInfoboxes.add(new ChargedItemInfobox(chargedItem, itemManager, infoBoxManager, configManager, config, this)));
		chargedItemsInfoboxes.forEach(chargedItemInfobox -> infoBoxManager.addInfoBox(chargedItemInfobox));
	}

	@Override
	protected void shutDown() {
		keyManager.unregisterKeyListener(this);
		mouseManager.unregisterMouseListener(this);
		mouseManager.unregisterMouseWheelListener(this);

		overlayManager.remove(overlayChargedItems);
		chargedItemsInfoboxes.forEach(chargedItemInfobox -> infoBoxManager.removeInfoBox(chargedItemInfobox));
	}

	@Subscribe
	public void onChatMessage(final ChatMessage event) {
		store.setLastChatMessage(event);
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onChatMessage(event));

//		System.out.println("MESSAGE | " +
//			"type: " + event.getType().name() +
//			", message: " + getCleanChatMessage(event) +
//			", sender: " + event.getSender()
//		);
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		store.onItemContainerChanged(event);

		for (final ChargedItemBase infobox : chargedItems) {
			infobox.onItemContainerChanged(event);
		}

//		String itemContainer = String.valueOf(event.getContainerId());
//		for (final Item item : event.getItemContainer().getItems()) {
//			itemContainer += "\r\n" +
//				item.getId() + ": " + itemManager.getItemComposition(item.getId()).getName() +
//				", quantity: " + item.getQuantity();
//		}
//		System.out.println("ITEM CONTAINER | " +
//			itemContainer
//		);
	}

	@Subscribe
	public void onGraphicChanged(final GraphicChanged event) {
		if (event.getActor() != client.getLocalPlayer()) return;

		Arrays.stream(chargedItems).forEach(infobox -> infobox.onGraphicChanged(event));

		if (config.showDebugIds()) {
			for (final ActorSpotAnim graphic : event.getActor().getSpotAnims()) {
				chatMessageManager.queue(QueuedMessage.builder()
					.type(ChatMessageType.CONSOLE)
					.runeLiteFormattedMessage("[Item Charges Improved] Graphic ID: " + graphic.getId())
					.build()
				);
			}
		}
	}

	@Subscribe
	public void onHitsplatApplied(final HitsplatApplied event) {
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onHitsplatApplied(event));

//		System.out.println("HITSPLAT | " +
//			"actor: " + (event.getActor() == client.getLocalPlayer() ? "self" : "enemy") +
//			", type: " + event.getHitsplat().getHitsplatType() +
//			", amount:" + event.getHitsplat().getAmount() +
//			", others = " + event.getHitsplat().isOthers() +
//			", mine = " + event.getHitsplat().isMine()
//		);
	}

	@Subscribe
	public void onAnimationChanged(final AnimationChanged event) {
		if (event.getActor() != client.getLocalPlayer() || event.getActor().getAnimation() == -1) return;

		Arrays.stream(chargedItems).forEach(infobox -> infobox.onAnimationChanged(event));

		if (config.showDebugIds()) {
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage("[Item Charges Improved] Animation ID: " + event.getActor().getAnimation())
				.build()
			);
		}
	}

	@Subscribe
	public void onWidgetLoaded(final WidgetLoaded event) {
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onWidgetLoaded(event));

//		System.out.println("WIDGET | " +
//			"group: " + event.getGroupId()
//		);
	}

	@Subscribe
	public void onMenuOptionClicked(final MenuOptionClicked event) {
		final AdvancedMenuEntry advancedMenuEntry = new AdvancedMenuEntry(event, client);
		if (
			advancedMenuEntry.option.equals("Use") && advancedMenuEntry.action.equals("WIDGET_TARGET") ||
			advancedMenuEntry.action.equals("CANCEL")
		) return;

		store.onMenuOptionClicked(advancedMenuEntry);

		for (final ChargedItemBase chargedItem : chargedItems) {
			chargedItem.onMenuOptionClicked(advancedMenuEntry);
		}

//		System.out.println("MENU OPTION | " +
//			"event id: " + advancedMenuEntry.eventId +
//			", option: " + advancedMenuEntry.option +
//			", target: " + advancedMenuEntry.target +
//			", action id: " + advancedMenuEntry.actionId +
//			", action name: " + advancedMenuEntry.action +
//			", item id: " + advancedMenuEntry.itemId +
//			", impostor id: " + advancedMenuEntry.impostorId
//		);
	}

	@Subscribe
	public void onScriptPreFired(final ScriptPreFired event) {
		switch (event.getScriptId()) {
			case 1004: case 2100: case 2512: case 3174: case 3350:
			case 4029: case 4517: case 4518: case 4671: case 4716:
			case 4721: case 4730: case 5933: case 5935: case 5939:
				return;
			default:
				for (final ChargedItemBase chargedItem : chargedItems) {
					chargedItem.onScriptPreFired(event);
				}
		}

	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		if (event.getGameState() == GameState.LOGGING_IN) {
			checkForChargesReset();
		}

		if (event.getGameState() != GameState.LOGGED_IN) return;

		// Send message about plugin updates for once.
		if (!config.getVersion().equals(pluginVersion)) {
			configManager.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.version, pluginVersion);
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(pluginMessage)
				.build()
			);
		}
	}

	@Subscribe
	public void onStatChanged(final StatChanged event) {
//		String statChanged =
//			event.getSkill().getName() +
//			", level: " + event.getLevel() +
//			", total xp: " + event.getXp();
//
//		if (store.getSkillXp(event.getSkill()).isPresent()) {
//			statChanged += ", xp drop: " + (event.getXp() - store.getSkillXp(event.getSkill()).get());
//		}
//		System.out.println("STAT CHANGED | " +
//			statChanged
//		);

		Arrays.stream(chargedItems).forEach(infobox -> infobox.onStatChanged(event));
		store.onStatChanged(event);
	}

	@Subscribe
	public void onItemDespawned(final ItemDespawned event) {
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onItemDespawned(event));
	}

	@Subscribe
	public void onVarbitChanged(final VarbitChanged event) {
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onVarbitChanged(event));

		// If server minutes are 0, it's a new day!
		if (event.getVarbitId() == VARBIT_MINUTES && client.getGameState() == GameState.LOGGED_IN && event.getValue() == 0) {
			checkForChargesReset();
		}

//		System.out.println("VARBIT CHANGED | " +
//			"id: " + event.getVarbitId() +
//			", value: " + event.getValue()
//		);
	}

	@Subscribe
	public void onMenuEntryAdded(final MenuEntryAdded event) {
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onMenuEntryAdded(event));

//		if (event.getMenuEntry().getItemId() != -1) {
//			System.out.println("MENU ENTRY ADDED | " +
//				"item id: " + event.getMenuEntry().getItemId() +
//				", option: " + event.getOption() +
//				", target: " + event.getTarget()
//			);
//		}
	}

	@Subscribe
	public void onGameTick(final GameTick gametick) {
		store.onGameTick(gametick);
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		if (event.getGroup().equals(ChargesImprovedConfig.group) && event.getKey().equals(ChargesImprovedConfig.debug_ids)) {
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(config.showDebugIds()
					? "<colHIGHLIGHT>[Item Charges Improved] Debug information is now enabled."
					: "<colHIGHLIGHT>[Item Charges Improved] Debug information is now disabled."
				).build()
			);
		}
	}

	private void onUserAction() {
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onUserAction());
	}

	private void checkForChargesReset() {
		final String date = LocalDateTime.now(timezone).format(DateTimeFormatter.ISO_LOCAL_DATE);
		if (date.equals(config.getResetDate())) return;

		configManager.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.date, date);
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onResetDaily());

		chatMessageManager.queue(QueuedMessage.builder()
			.type(ChatMessageType.CONSOLE)
			.runeLiteFormattedMessage("<colHIGHLIGHT>Daily item charges have been reset.")
			.build()
		);
	}

	private void configMigration() {
		// Migrate old hidden infoboxes multi-select to checkboxes.
		final Optional<String> necklaceOfPassageOverlay = Optional.ofNullable(configManager.getConfiguration(ChargesImprovedConfig.group, "necklage_of_passage_overlay"));
		final Optional<String> necklaceOfPassageInfobox = Optional.ofNullable(configManager.getConfiguration(ChargesImprovedConfig.group, "necklage_of_passage_infobox"));

		if (necklaceOfPassageOverlay.isPresent()) {
			configManager.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.necklace_of_passage + ChargesImprovedConfig.overlay, necklaceOfPassageOverlay.get().equals("true"));
			configManager.unsetConfiguration(ChargesImprovedConfig.group, "necklage_of_passage_overlay");
		}

		if (necklaceOfPassageInfobox.isPresent()) {
			configManager.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.necklace_of_passage + ChargesImprovedConfig.infobox, necklaceOfPassageInfobox.get().equals("true"));
			configManager.unsetConfiguration(ChargesImprovedConfig.group, "necklage_of_passage_infobox");
		}
	}

	@Override
	public void keyPressed(final KeyEvent keyEvent) {
		onUserAction();
	}

	@Override
	public void keyTyped(final KeyEvent keyEvent) {}

	@Override
	public void keyReleased(final KeyEvent keyEvent) {}

	@Override
	public MouseEvent mousePressed(final MouseEvent mouseEvent) {
		onUserAction();
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseDragged(final MouseEvent mouseEvent) {
		onUserAction();
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseMoved(final MouseEvent mouseEvent) {
		onUserAction();
		return mouseEvent;
	}

	@Override
	public MouseWheelEvent mouseWheelMoved(final MouseWheelEvent mouseWheelEvent) {
		onUserAction();
		return mouseWheelEvent;
	}

	@Override
	public MouseEvent mouseClicked(final MouseEvent mouseEvent) {
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseReleased(final MouseEvent mouseEvent) {
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseEntered(final MouseEvent mouseEvent) {
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseExited(final MouseEvent mouseEvent) {
		return mouseEvent;
	}

	public static String getCleanChatMessage(final ChatMessage event) {
		return event.getMessage().replaceAll("</?col.*?>", "").replaceAll("<br>", " ").replaceAll("\u00A0"," ");
	}

	public static int getNumberFromCommaString(final String charges) {
		return Integer.parseInt(charges.replaceAll(",", "").replaceAll("\\.", ""));
	}

	public static Optional<Widget> getWidget(final Client client, final int parent, final int child) {
		@Nullable
		final Widget widget = client.getWidget(parent, child);
		return Optional.ofNullable(widget);
	}

	public static Optional<Widget> getWidget(final Client client, final int parent, final int child, final int subChild) {
		@Nullable
		final Widget widget = client.getWidget(parent, child);
		if (widget == null) return Optional.empty();

		@Nullable
		final Widget subWidget = widget.getChild(subChild);
		return Optional.ofNullable(subWidget);
	}
	
	private static final ImmutableMap<String, Integer> TEXT_TO_NUMBER_MAP = ImmutableMap.<String, Integer>builder()
		.put("zero", 0).put("one", 1).put("two", 2).put("three", 3).put("four", 4).put("five", 5)
		.put("six", 6).put("seven", 7).put("eight", 8).put("nine", 9).put("ten", 10)
		.put("eleven", 11).put("twelve", 12).put("thirteen", 13).put("fourteen", 14).put("fifteen", 15)
		.put("sixteen", 16).put("seventeen", 17).put("eighteen", 18).put("nineteen", 19).put("twenty", 20)
		.put("thirty", 30).put("forty", 40).put("fifty", 50).put("sixty", 60).put("seventy", 70)
		.put("eighty", 80).put("ninety", 90).put("hundred", 100).build();

	public static int getNumberFromWordRepresentation(final String charges) {
		// Support strings like "twenty two" and "twenty-two"
		final String[] words = charges.toLowerCase().split("[ -]");
		int result = 0;
		int current = 0;

		for (final String word : words) {
			if (TEXT_TO_NUMBER_MAP.containsKey(word)) {
				current += TEXT_TO_NUMBER_MAP.get(word);
			} else if (word.equals("hundred")) {
				current *= 100;
			} else if (word.equals("thousand")) {
				result += current * 1000;
				current = 0;
			}
		}

		return result + current;
	}
}

