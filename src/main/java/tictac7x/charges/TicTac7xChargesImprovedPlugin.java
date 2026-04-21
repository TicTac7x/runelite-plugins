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
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBox;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.OSType;
import tictac7x.charges.events.*;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.overlays.ChargedItemInfobox;
import tictac7x.charges.item.overlays.ChargedItemOverlay;
import tictac7x.charges.items.barrows.*;
import tictac7x.charges.items.boots.*;
import tictac7x.charges.items.capes.*;
import tictac7x.charges.items.crystal.*;
import tictac7x.charges.items.foods.*;
import tictac7x.charges.items.helms.*;
import tictac7x.charges.items.jewelry.*;
import tictac7x.charges.items.moons.*;
import tictac7x.charges.items.potions.P_Overload;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.items.potions.P_Overload;
import tictac7x.charges.items.potions.cox.*;
import tictac7x.charges.items.potions.toa.*;
import tictac7x.charges.items.shields.*;
import tictac7x.charges.items.utils.*;
import tictac7x.charges.items.weapons.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.Store;
import tictac7x.charges.store.ids.ChargeId;
import tictac7x.charges.store.ids.VarbitId;

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
		"scroll",
		"potion"
	}
)

public class TicTac7xChargesImprovedPlugin extends Plugin implements KeyListener, MouseListener, MouseWheelListener {
	private final String pluginVersion = "v0.6.12";
	private final String pluginMessage =
		"<colHIGHLIGHT>Item Charges Improved " + pluginVersion + ":<br>" +
		"<colHIGHLIGHT>* Reagent pouch fixes.<br>" +
		"<colHIGHLIGHT>* Abyssal tentacle added.<br>" +
		"<colHIGHLIGHT>* Venator bow improvements and echo venator bow support."
	;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ItemManager itemManager;

	@Inject
	private PluginManager pluginManager;

	@Inject
	private ConfigManager configManager;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private OverlayManager overlayManager;

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

	@Inject
	private TicTac7xChargesImprovedConfig config;

	@Provides
	TicTac7xChargesImprovedConfig provideConfig(final ConfigManager configManager) {
		return configManager.getConfig(TicTac7xChargesImprovedConfig.class);
	}

	private Provider provider;
	private Store store;

	private ChargedItemOverlay overlayChargedItems;

	private ChargedItemBase[] chargedItems;
	private final List<InfoBox> chargedItemsInfoboxes = new ArrayList<>();

	private final ZoneId timezone = ZoneId.of("Europe/London");
	public static final String INFINITE_SYMBOL = OSType.getOSType() == OSType.MacOS ? "inf" : "∞";

	@Override
	protected void startUp() {
		configMigration();
		keyManager.registerKeyListener(this);
		mouseManager.registerMouseListener(this);
		mouseManager.registerMouseWheelListener(this);

		store = new Store(client, itemManager, configManager);
		provider = new Provider(client, clientThread, pluginManager, configManager, itemManager, infoBoxManager, chatMessageManager, tooltipManager, notifier, this, config, store, gson);

		chargedItems = new ChargedItemBase[]{
			// Crystal armor set
			new A_CrystalBody(provider),
			new A_CrystalHelm(provider),
			new A_CrystalLegs(provider),

			// Boots
			new B_FremennikSeaBoots(provider),

			// Capes
			new C_ArdougneCloak(provider),
			new C_Coffin(provider),
			new C_ForestryBasket(provider),
			new C_ForestryKit(provider),
			new C_LogBasket(provider),
			new C_MagicCape(provider),

			// Foods
			new F_Apples(provider),
			new F_Bananas(provider),
			new F_Cabbages(provider),
			new F_Onions(provider),
			new F_Oranges(provider),
			new F_Potatoes(provider),
			new F_Strawberries(provider),
			new F_Tomatoes(provider),

			// Helms
			new H_CircletOfWater(provider),
			new H_KandarinHeadgear(provider),
            new H_SerpentineHelm(provider),
            new H_MagmaHelm(provider),
            new H_TanzaniteHelm(provider),

			// Jewelery
			new J_AlchemistsAmulet(provider),
			new J_AmuletOfBloodFury(provider),
			new J_AmuletOfBounty(provider),
			new J_AmuletOfChemistry(provider),
			new J_AmuletOfGlory(provider),
			new J_BindingNecklace(provider),
			new J_BraceletOfClay(provider),
			new J_BraceletOfSlaughter(provider),
			new J_BurningAmulet(provider),
			new J_Camulet(provider),
			new J_CastleWarsBracelet(provider),
			new J_DesertAmulet(provider),
			new J_DigsitePendant(provider),
			new J_EfaritaysAid(provider),
			new J_EscapeCrystal(provider),
			new J_ExpeditiousBracelet(provider),
			new J_FlamtaerBracelet(provider),
			new J_GamesNecklace(provider),
			new J_GiantsoulAmulet(provider),
			new J_NecklaceOfPassage(provider),
			new J_PhoenixNecklace(provider),
			new J_DodgyNecklace(provider),
			new J_PendantOfAtes(provider),
			new J_CombatBracelet(provider),
			new J_CowbellAmulet(provider),
			new J_CelestialRing(provider),
			new J_RingOfDueling(provider),
			new J_RingOfForging(provider),
			new J_RingOfWealth(provider),
			new J_RingOfTheElements(provider),
			new J_RingOfEndurance(provider),
			new J_ExplorersRing(provider),
			new J_RingOfPursuit(provider),
			new J_RingOfRecoil(provider),
			new J_RingOfReturning(provider),
			new J_RingOfShadows(provider),
			new J_SlayerRing(provider),
			new J_RingOfSuffering(provider),
			new J_SkillsNecklace(provider),
			new J_XericsTalisman(provider),
			new J_SailorsAmulet(provider),
			// Potions
			new P_Absorption(provider),
			new P_Agility(provider),
			new P_AgilityMix(provider),
			new P_AncientBrew(provider),
			new P_AncientBrewMix(provider),
			new P_Antidote(provider),
			new P_AntidoteMix(provider),
			new P_AntidotePlusPlus(provider),
			new P_Antifire(provider),
			new P_AntifireMix(provider),
			new P_Antipoison(provider),
			new P_AntipoisonMix(provider),
			new P_Antivenom(provider),
			new P_AntivenomPlus(provider),
			new P_ArmadylBrew(provider),
			new P_Attack(provider),
			new P_AttackMix(provider),
			new P_Bastion(provider),
			new P_Battlemage(provider),
			new P_BlackWarlockMix(provider),
			new P_BlightedSuperRestore(provider),
			new P_Combat(provider),
			new P_CombatMix(provider),
			new P_Defence(provider),
			new P_DefenceMix(provider),
			new P_DivineBastion(provider),
			new P_DivineBattlemage(provider),
			new P_DivineMagic(provider),
			new P_DivineRanging(provider),
			new P_DivineSuperAttack(provider),
			new P_DivineSuperCombat(provider),
			new P_DivineSuperDefence(provider),
			new P_DivineSuperStrength(provider),
			new P_Egniol(provider),
			new P_Energy(provider),
			new P_EnergyMix(provider),
			new P_ExtendedAntifire(provider),
			new P_ExtendedAntifireMix(provider),
			new P_ExtendedAntivenom(provider),
			new P_ExtendedStamina(provider),
			new P_ExtendedSuperAntifire(provider),
			new P_ExtendedSuperAntifireMix(provider),
			new P_ExtremeEnergy(provider),
			new P_Fishing(provider),
			new P_FishingMix(provider),
			new P_ForgottenBrew(provider),
			new P_Goading(provider),
			new P_GuthixBalance(provider),
			new P_GuthixRest(provider),
			new P_HaemostaticDressing(provider),
			new P_Hunter(provider),
			new P_HuntingMix(provider),
			new P_Magic(provider),
			new P_MagicEssence(provider),
			new P_MagicEssenceMix(provider),
			new P_MagicMix(provider),
			new P_MenaphiteRemedy(provider),
			new P_Moonlight(provider),
			new P_MoonlightMothMix(provider),
			new P_Overload(provider),
			new P_Prayer(provider),
			new P_PrayerMix(provider),
			new P_PrayerRegeneration(provider),
			new P_Ranging(provider),
			new P_RangingMix(provider),
			new P_RelycimsBalm(provider),
			new P_RelycimsMix(provider),
			new P_Restore(provider),
			new P_RestoreMix(provider),
			new P_RubyHarvestMix(provider),
			new P_SanfewSerum(provider),
			new P_SapphireGlacialisMix(provider),
			new P_SaradominBrew(provider),
			new P_Serum_207(provider),
			new P_Serum_208(provider),
			new P_SnowyKnightMix(provider),
			new P_Stamina(provider),
			new P_StaminaMix(provider),
			new P_Strength(provider),
			new P_StrengthMix(provider),
			new P_SunlightMothMix(provider),
			new P_SuperAntifire(provider),
			new P_SuperAntifireMix(provider),
			new P_SuperAntipoison(provider),
			new P_SuperAntipoisonMix(provider),
			new P_SuperAttack(provider),
			new P_SuperAttackMix(provider),
			new P_SuperCombat(provider),
			new P_SuperCompost(provider),
			new P_SuperDefence(provider),
			new P_SuperDefenceMix(provider),
			new P_SuperEnergy(provider),
			new P_SuperEnergyMix(provider),
			new P_SuperFishing(provider),
			new P_SuperHunting(provider),
			new P_SuperMagic(provider),
			new P_SuperRanging(provider),
			new P_SuperRestore(provider),
			new P_SuperRestoreMix(provider),
			new P_SuperStrength(provider),
			new P_SuperStrengthMix(provider),
			new P_Surge(provider),
			new P_ZamorakBrew(provider),
			new P_ZamorakMix(provider),
			// COX potions
			new P_Elder(provider),
			new P_ElderMinus(provider),
			new P_ElderPlus(provider),
			new P_Kodai(provider),
			new P_KodaiMinus(provider),
			new P_KodaiPlus(provider),
			new tictac7x.charges.items.potions.cox.P_Overload(provider),
			new P_OverloadMinus(provider),
			new P_OverloadPlus(provider),
			new P_PrayerEnhance(provider),
			new P_PrayerEnhanceMinus(provider),
			new P_PrayerEnhancePlus(provider),
			new P_Revitalisation(provider),
			new P_RevitalisationMinus(provider),
			new P_RevitalisationPlus(provider),
			new P_Twisted(provider),
			new P_TwistedMinus(provider),
			new P_TwistedPlus(provider),
			new P_XericsAid(provider),
			new P_XericsAidMinus(provider),
			new P_XericsAidPlus(provider),
			// TOA potions
			new P_Ambrosia(provider),
			new P_BlessedCrystalScarab(provider),
			new P_LiquidAdrenaline(provider),
			new P_Nectar(provider),
			new P_SilkDressing(provider),
			new P_SmellingSalts(provider),
			new P_TearsOfElidinis(provider),

			// Shields
			new S_Chronicle(provider),
			new S_CrystalShield(provider),
			new S_DragonfireShield(provider),
			new S_FaladorShield(provider),
			new S_KharedstMemoirs(provider),
			new S_TomeOfEarth(provider),
			new S_TomeOfFire(provider),
			new S_TomeOfWater(provider),

			// Utilities
			new U_AshSanctifier(provider),
			new U_BloodEssence(provider),
			new U_BoneCrusher(provider),
			new U_BottomlessCompostBucket(provider),
			new U_BowStringSpool(provider),
			new U_ChuggingBarrel(provider),
			new U_CoalBag(provider),
			new U_CrystalSaw(provider),
			new U_Ectophial(provider),
			new U_ColossalPouch(provider),
			new U_FishBarrel(provider),
			new U_FlamtaerBag(provider),
			new U_FungicideSpray(provider),
			new U_FurPouch(provider),
			new U_GemBag(provider),
			new U_GricollersCan(provider),
			new U_HerbSack(provider),
			new U_HuntsmansKit(provider),
			new U_ImpInABox(provider),
			new U_JarGenerator(provider),
			new U_MasterScrollBook(provider),
			new U_MeatPouch(provider),
			new U_OgreBellows(provider),
			new U_QuetzalWhistle(provider),
			new U_PlankSack(provider),
			new U_ReagentPouch(provider),
			new U_RoyalSeedPod(provider),
			new U_SeedBox(provider),
			new U_SoulBearer(provider),
			new U_StrangeOldLockpick(provider),
			new U_TackleBox(provider),
			new U_TeleportCrystal(provider),
			new U_EternalTeleportCrystal(provider),
			new U_WateringCan(provider),
			new U_Waterskin(provider),

			// Weapons
            new W_AbyssalTentacle(provider),
			new W_Arclight(provider),
			new W_BlazingBlowpipe(provider),
			new W_BowOfFaerdhinen(provider),
			new W_BryophytasStaff(provider),
			new W_CrawsBow(provider),
			new W_CrystalBow(provider),
			new W_CrystalHalberd(provider),
            new W_EchoVenatorBow(provider),
			new W_EnchantedLyre(provider),
			new W_EyeOfAyak(provider),
			new W_InfernalAxe(provider),
			new W_IbansStaff(provider),
			new W_PharaohsSceptre(provider),
			new W_SanguinestiStaff(provider),
			new W_ScytheOfVitur(provider),
			new W_SkullSceptre(provider),
			new W_SlayerStaffE(provider),
			new W_ToxicBlowpipe(provider),
			new W_TridentOfTheSeas(provider),
			new W_TridentOfTheSeasE(provider),
			new W_TridentOfTheSwamp(provider),
			new W_TridentOfTheSwampE(provider),
			new W_TumekensShadow(provider),
			new W_VenatorBow(provider),
			new W_WarpedSceptre(provider),
			new W_WebweaverBow(provider),
			new W_WesternBanner(provider),

			// Barrows armor sets
			new AhrimsHood(provider),
			new AhrimsRobetop(provider),
			new AhrimsRobeskirt(provider),
			new AhrimsStaff(provider),

			new DharoksHelm(provider),
			new DharoksPlatebody(provider),
			new DharoksPlatelegs(provider),
			new DharoksGreataxe(provider),

			new GuthansHelm(provider),
			new GuthansPlatebody(provider),
			new GuthansChainskirt(provider),
			new GuthansWarspear(provider),

			new KarilsCoif(provider),
			new KarilsLeathertop(provider),
			new KarilsLeatherskirt(provider),
			new KarilsCrossbow(provider),

			new ToragsHelm(provider),
			new ToragsPlatebody(provider),
			new ToragsPlatelegs(provider),
			new ToragsHammers(provider),

			new VeracsHelm(provider),
			new VeracsBrassard(provider),
			new VeracsPlateskirt(provider),
			new VeracsFlail(provider),

			// Moons armor set.
			new BloodMoonHelm(provider),
			new BloodMoonChestplate(provider),
			new BloodMoonTassets(provider),
			new BlueMoonHelm(provider),
			new BlueMoonChestplate(provider),
			new BlueMoonTassets(provider),
			new EclipseMoonHelm(provider),
			new EclipseMoonChestplate(provider),
			new EclipseMoonTassets(provider),
		};

		store.setChargedItems(chargedItems);

		// Items overlays.
		overlayChargedItems = new ChargedItemOverlay(provider, chargedItems);
		overlayManager.add(overlayChargedItems);

		// Items infoboxes.
		for (final ChargedItemBase chargedItem : chargedItems) {
			final ChargedItemInfobox chargedItemInfobox = new ChargedItemInfobox(provider, chargedItem);
			chargedItemsInfoboxes.add(chargedItemInfobox);
			infoBoxManager.addInfoBox(chargedItemInfobox);
		}
	}

	@Override
	protected void shutDown() {
		keyManager.unregisterKeyListener(this);
		mouseManager.unregisterMouseListener(this);
		mouseManager.unregisterMouseWheelListener(this);
		overlayManager.remove(overlayChargedItems);
		chargedItemsInfoboxes.forEach(chargedItemInfobox -> infoBoxManager.removeInfoBox(chargedItemInfobox));
		chargedItemsInfoboxes.clear();
	}

	@Subscribe
	public void onChatMessage(final ChatMessage event) {
		final CustomChatMessage chatMessage = new CustomChatMessage(event);
		store.onChatMessage(chatMessage);
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onChatMessage(chatMessage));
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		final CustomItemContainerChanged itemContainerChanged = new CustomItemContainerChanged(event, itemManager);
		store.onItemContainerChanged(itemContainerChanged);
	}

	@Subscribe
	public void onGraphicChanged(final GraphicChanged event) {
		if (event.getActor() != client.getLocalPlayer()) return;
		store.onGraphicChanged(event);

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
		final CustomHitsplatApplied hitsplatApplied = new CustomHitsplatApplied(event, client);
		store.onHitSplatApplied(hitsplatApplied);
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onHitsplatApplied(hitsplatApplied));
	}

	@Subscribe
	public void onAnimationChanged(final AnimationChanged event) {
		if (event.getActor().getAnimation() == -1) return;

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
		final CustomMenuOptionClicked customMenuOptionClicked = new CustomMenuOptionClicked(event, client);

		if (
			// Menu option not found.
			customMenuOptionClicked.option.isEmpty() ||
			// Not menu.
			customMenuOptionClicked.target.isEmpty() && (
				!customMenuOptionClicked.option.contains("Buy-") &&
				!customMenuOptionClicked.option.equals("Continue") &&
				!customMenuOptionClicked.option.equals("Yes") &&
				customMenuOptionClicked.eventId != 65540 && // Special event check for log basket
				customMenuOptionClicked.eventId != 65538 && // Special event check for forestry basket
				customMenuOptionClicked.eventId != 131074 && // Special event check for forestry basket
				customMenuOptionClicked.eventId != 131076 && // Special event check for forestry basket
				customMenuOptionClicked.eventId != 327684 // Sailor's amulet - Deepfin Point
			) ||
			// Start use by clicking on item.
			customMenuOptionClicked.option.equals("Use") && customMenuOptionClicked.action.equals("WIDGET_TARGET") ||
			// Cancel option.
			customMenuOptionClicked.action.equals("CANCEL") ||
			// RuneLite specific action.
			customMenuOptionClicked.action.equals("RUNELITE")
		) return;

		store.onMenuOptionClicked(customMenuOptionClicked);
		for (final ChargedItemBase chargedItem : chargedItems) {
			chargedItem.onMenuOptionClicked(customMenuOptionClicked);
		}
	}

	final List<Integer> scriptIdsToIgnore = Arrays.asList(
		44, 85, 100, 839, 900, 1004, 1005, 1045, 1445, 1972, 2100, 2101,
		2165, 2250, 2372, 2476, 2512, 2513, 3174, 3277, 3350, 3351, 4024,
		4029, 4482, 4517, 4518, 4666, 4667, 4668, 4669, 4671, 4672, 4716,
		4721, 4729, 4730, 4731, 4734, 5343, 5923, 5933, 5935, 5936, 5939,
		5943, 5944, 6015, 6016, 6063, 6152
	);

	@Subscribe
	public void onScriptPreFired(final ScriptPreFired event) {
		if (scriptIdsToIgnore.contains(event.getScriptId())) return;

//		String scriptDebug = "script id: " + event.getScriptId();
//		try {
//			final Optional<Widget> widget = Optional.ofNullable(event.getScriptEvent().getSource());
//			if (widget.isPresent()) {
//				scriptDebug += ", widget id: " + widget.get().getId();
//			}
//		} catch (final Exception ignored) {}
//		try {
//			String arguments = ", arguments: [";
//			for (final Object argument : event.getScriptEvent().getArguments()) {
//				arguments += argument + ", ";
//			}
//			arguments += "]";
//			scriptDebug += arguments.replaceAll(", ]", "]");
//		} catch (final Exception ignored) {}
//		System.out.println("SCRIPT FIRED | " + scriptDebug);

		Arrays.stream(chargedItems).forEach(infobox -> infobox.onScriptPreFired(event));
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		if (event.getGameState() == GameState.LOGGING_IN) {
			checkForChargesReset();
		}

		if (event.getGameState() != GameState.LOGGED_IN) return;

		// Send message about plugin updates for once.
		if (!config.getVersion().equals(pluginVersion)) {
			configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.version, pluginVersion);
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
		if (event.getVarbitId() == VarbitId.MINUTES && client.getGameState() == GameState.LOGGED_IN && event.getValue() == 0) {
			checkForChargesReset();
		}

//		System.out.println("VARBIT CHANGED | " +
//			"id: " + event.getVarbitId() +
//			", value: " + event.getValue()
//		);
	}

	@Subscribe
	public void onMenuEntryAdded(final MenuEntryAdded event) {
		if (event.getOption().equals("Cancel")) return;
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
	public void onGameTick(final GameTick event) {
		store.onGameTick(event);
		Arrays.stream(chargedItems).forEach(infobox -> infobox.onGameTick(event));
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		if (event.getGroup().equals(TicTac7xChargesImprovedConfig.group) && event.getKey().equals(TicTac7xChargesImprovedConfig.debug_ids)) {
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
		Arrays.stream(chargedItems).forEach(chargedItem -> {
			chargedItem.onUserAction();
		});
	}

	private void checkForChargesReset() {
		final String date = LocalDateTime.now(timezone).format(DateTimeFormatter.ISO_LOCAL_DATE);
		if (date.equals(config.getResetDate())) return;

		configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.date, date);
		Arrays.stream(chargedItems).forEach(ChargedItemBase::onResetDaily);

		if (config.showDailyReset()) {
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage("<colHIGHLIGHT>Daily item charges have been reset.")
				.build()
			);
		}
	}

	private void configMigration() {
		// v0.5.5 - Migrate old hidden infoboxes multi-select to checkboxes.
		final Optional<String> necklaceOfPassageOverlay = Optional.ofNullable(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, "necklage_of_passage_overlay"));
		final Optional<String> necklaceOfPassageInfobox = Optional.ofNullable(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, "necklage_of_passage_infobox"));
		if (necklaceOfPassageOverlay.isPresent()) {
			configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.necklace_of_passage + TicTac7xChargesImprovedConfig._overlay, necklaceOfPassageOverlay.get().equals("true"));
			configManager.unsetConfiguration(TicTac7xChargesImprovedConfig.group, "necklage_of_passage_overlay");
		}
		if (necklaceOfPassageInfobox.isPresent()) {
			configManager.setConfiguration(TicTac7xChargesImprovedConfig.group, TicTac7xChargesImprovedConfig.necklace_of_passage + TicTac7xChargesImprovedConfig._infobox, necklaceOfPassageInfobox.get().equals("true"));
			configManager.unsetConfiguration(TicTac7xChargesImprovedConfig.group, "necklage_of_passage_infobox");
		}

		// v0.6.8 - remove outdated destroy entries config
		if (Optional.ofNullable(configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, "hide_destroy")).isPresent()) {
			configManager.unsetConfiguration(TicTac7xChargesImprovedConfig.group, "hide_destroy");
		}
	}

	@Override
	public void keyPressed(final KeyEvent keyEvent) {
		onUserAction();
	}

	@Override
	public void keyTyped(final KeyEvent keyEvent) {
	}

	@Override
	public void keyReleased(final KeyEvent keyEvent) {
	}

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

	public static String getCleanText(final String text) {
		return text.replaceAll("</?col.*?>", "").replaceAll("<br>", " ").replaceAll("\u00A0"," ");
	}

	public static String getCleanChatMessage(final ChatMessage event) {
		return getCleanText(event.getMessage());
	}

	public static String menuOptionEmptyToBank = "Empty-to-bank";
	public static String menuOptionFillFromBank = "Fill-from-bank";
	public static String menuOptionEmptyToInventory = "Empty-to-inventory";
	public static String menuOptionFillFromInventory = "Fill-from-inventory";

	public static int getNumberFromCommaString(final String charges) {
		return Integer.parseInt(charges.replaceAll(",", "").replaceAll("\\.", ""));
	}

	public static Optional<Widget> getWidget(final Client client, final int parent, final int child) {
		return Optional.ofNullable(client.getWidget(parent, child));
	}

	public static Optional<Widget> getWidget(final Client client, final int parent, final int child, final int subChild) {
		return getWidget(client, parent, child, Optional.of(subChild));
	}

	public static Optional<Widget> getWidget(final Client client, final int parent, final int child, final Optional<Integer> subChild) {
		final Optional<Widget> widget = getWidget(client, parent, child);
		if (!widget.isPresent()) return Optional.empty();

		if (subChild.isPresent()) {
			return Optional.ofNullable(widget.get().getChild(subChild.get()));
		} else {
			return widget;
		}
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

	public static String getChargesMinified(final int charges) {
		// Unlimited.
		if (charges == ChargeId.UNLIMITED) return INFINITE_SYMBOL;

		// Unknown.
		if (charges == ChargeId.UNKNOWN) return "?";

		// Minify to use millions (_M).
		if (charges >= 1000000) return charges / 1000000 + "M";

		// Minify to use thousands (_K).
		if (charges >= 10000) return Math.round(((float) charges / 1000)) + "K";

		// Minify to use thousands with hundreds (_._K)
		if (charges >= 1000) {
			final int thousands = charges / 1000;
			final int hundreds = Math.min((charges % 1000 + 50) / 100, 9);
			return thousands + (hundreds > 0 ? "." + hundreds : "") + "K";
		}

		// As is.
		return String.valueOf(charges);
	}
}

