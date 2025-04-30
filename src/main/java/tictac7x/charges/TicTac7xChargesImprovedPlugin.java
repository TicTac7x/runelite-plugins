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
import net.runelite.client.util.OSType;
import tictac7x.charges.customEvents.CustomChatMessage;
import tictac7x.charges.customEvents.CustomHitsplatApplied;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.overlays.ChargedItemInfobox;
import tictac7x.charges.item.overlays.ChargedItemOverlay;
import tictac7x.charges.customEvents.CustomItemContainerChanged;
import tictac7x.charges.items.barrows.*;
import tictac7x.charges.customEvents.CustomMenuOptionClicked;
import tictac7x.charges.items.boots.B_FremennikSeaBoots;
import tictac7x.charges.items.capes.*;
import tictac7x.charges.items.crystal.A_CrystalBody;
import tictac7x.charges.items.crystal.A_CrystalHelm;
import tictac7x.charges.items.crystal.A_CrystalLegs;
import tictac7x.charges.items.helms.H_CircletOfWater;
import tictac7x.charges.items.helms.H_KandarinHeadgear;
import tictac7x.charges.items.jewelry.*;
import tictac7x.charges.items.moons.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.items.potions.P_Overload;
import tictac7x.charges.items.potions.cox.*;
import tictac7x.charges.items.potions.toa.*;
import tictac7x.charges.items.shields.*;
import tictac7x.charges.items.utils.*;
import tictac7x.charges.items.weapons.*;
import tictac7x.charges.store.Store;
import tictac7x.charges.store.VarbitId;

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

public class TicTac7xChargesImprovedPlugin extends Plugin implements KeyListener, MouseListener, MouseWheelListener {
	private final String pluginVersion = "v0.6.3";
	private final String pluginMessage =
		"<colHIGHLIGHT>Item Charges Improved " + pluginVersion + ":<br>" +
		"<colHIGHLIGHT>* Potion doses support added."
	;

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
	private TicTac7xChargesImprovedConfig config;

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
	TicTac7xChargesImprovedConfig provideConfig(final ConfigManager configManager) {
		return configManager.getConfig(TicTac7xChargesImprovedConfig.class);
	}

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

		chargedItems = new ChargedItemBase[]{
			// Crystal armor set
			new A_CrystalBody(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new A_CrystalHelm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new A_CrystalLegs(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Boots
			new B_FremennikSeaBoots(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Capes
			new C_ArdougneCloak(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new C_Coffin(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new C_ForestryBasket(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new C_ForestryKit(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new C_MagicCape(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Helms
			new H_CircletOfWater(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new H_KandarinHeadgear(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Jewellery
			new J_AlchemistsAmulet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_AmuletOfChemistry(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_AmuletOfBloodFury(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_BindingNecklace(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_BraceletOfClay(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_BraceletOfSlaughter(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_BurningAmulet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_Camulet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_CastleWarsBracelet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_DesertAmulet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_DigsitePendant(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_EfaritaysAid(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_EscapeCrystal(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_ExpeditiousBracelet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_FlamtaerBracelet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_GamesNecklace(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_GiantsoulAmulet(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_NecklaceOfPassage(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_PhoenixNecklace(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_DodgyNecklace(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_PendantOfAtes(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_CelestialRing(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfDueling(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfForging(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfTheElements(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfEndurance(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_ExplorersRing(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfPursuit(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfRecoil(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfShadows(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_SlayerRing(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_RingOfSuffering(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_SkillsNecklace(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new J_XericsTalisman(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Potions
			new P_Absorption(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Agility(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_AgilityMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_AncientBrew(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_AncientBrewMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Antidote(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_AntidoteMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_AntidotePlusPlus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Antifire(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_AntifireMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Antipoison(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_AntipoisonMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Antivenom(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_AntivenomPlus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Attack(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_AttackMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Bastion(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Battlemage(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_BlackWarlockMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_BlightedSuperRestore(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Combat(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_CombatMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Defence(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_DefenceMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_DivineBastion(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_DivineBattlemage(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_DivineMagic(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_DivineRanging(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_DivineSuperAttack(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_DivineSuperCombat(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_DivineSuperDefence(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_DivineSuperStrength(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Egniol(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Energy(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_EnergyMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ExtendedAntifire(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ExtendedAntifireMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ExtendedAntivenom(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ExtendedSuperAntifire(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ExtendedSuperAntifireMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Fishing(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_FishingMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ForgottenBrew(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Goading(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_GuthixBalance(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Hunter(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_HuntingMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Magic(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_MagicEssence(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_MagicEssenceMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_MagicMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_MenaphiteRemedy(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Moonlight(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_MoonlightMothMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Overload(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Prayer(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_PrayerMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_PrayerRegeneration(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Ranging(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_RangingMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_RelycimsBalm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_RelycimsMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Restore(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_RestoreMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_RubyHarvestMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SanfewSerum(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SapphireGlacialisMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SaradominBrew(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Serum_207(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Serum_208(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SnowyKnightMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Stamina(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_StaminaMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Strength(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_StrengthMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SunlightMothMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperAntifire(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperAntifireMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperAntipoison(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperAntipoisonMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperAttack(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperAttackMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperCombat(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperCompost(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperDefence(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperDefenceMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperEnergy(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperEnergyMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperMagic(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperRanging(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperRestore(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperRestoreMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperStrength(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SuperStrengthMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ZamorakBrew(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ZamorakMix(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			// COX potions
			new P_Elder(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ElderMinus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_ElderPlus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Kodai(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_KodaiMinus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_KodaiPlus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new tictac7x.charges.items.potions.cox.P_Overload(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_OverloadMinus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_OverloadPlus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_PrayerEnhance(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_PrayerEnhanceMinus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_PrayerEnhancePlus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Revitalisation(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_RevitalisationMinus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_RevitalisationPlus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Twisted(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_TwistedMinus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_TwistedPlus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_XericsAid(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_XericsAidMinus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_XericsAidPlus(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			// TOA potions
			new P_Ambrosia(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_BlessedCrystalScarab(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_LiquidAdrenaline(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_Nectar(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SilkDressing(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_SmellingSalts(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new P_TearsOfElidinis(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Shields
			new S_Chronicle(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_CrystalShield(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_DragonfireShield(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_FaladorShield(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_KharedstMemoirs(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_TomeOfEarth(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_TomeOfFire(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new S_TomeOfWater(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

			// Utilities
			new U_AshSanctifier(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_BoneCrusher(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_BottomlessCompostBucket(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_ChuggingBarrel(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_CoalBag(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_CrystalSaw(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_ColossalPouch(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_FishBarrel(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_FlamtaerBag(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_FungicideSpray(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_FurPouch(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_GemBag(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_GricollersCan(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_HerbSack(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_HuntsmansKit(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_ImpInABox(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_JarGenerator(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_LogBasket(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_MasterScrollBook(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_MeatPouch(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_OgreBellows(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_QuetzalWhistle(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_PlankSack(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_ReagentPouch(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_SeedBox(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_SoulBearer(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_StrangeOldLockpick(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_TackleBox(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_TeleportCrystal(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_EternalTeleportCrystal(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new U_Waterskin(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

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
			new W_ScytheOfVitur(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_SkullSceptre(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_SlayerStaffE(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_TridentOfTheSeas(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_TridentOfTheSeasE(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_TridentOfTheSwamp(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_TridentOfTheSwampE(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_TumekensShadow(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_VenatorBow(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_WarpedSceptre(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new W_WesternBanner(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),

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

			// Moons armor set.
			new BloodMoonHelm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new BloodMoonChestplate(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new BloodMoonTassets(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new BlueMoonHelm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new BlueMoonChestplate(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new BlueMoonTassets(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new EclipseMoonHelm(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new EclipseMoonChestplate(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
			new EclipseMoonTassets(client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson),
		};

		store.setChargedItems(chargedItems);

		// Items overlays.
		overlayChargedItems = new ChargedItemOverlay(client, tooltipManager, itemManager, configManager, config, chargedItems);
		overlayManager.add(overlayChargedItems);

		// Items infoboxes.
		for (final ChargedItemBase chargedItem : chargedItems) {
			final ChargedItemInfobox chargedItemInfobox = new ChargedItemInfobox(chargedItem, itemManager, infoBoxManager, configManager, config, this);
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
		final CustomMenuOptionClicked customMenuOptionClicked = new CustomMenuOptionClicked(event, client);

		if (
			// Menu option not found.
			customMenuOptionClicked.option.isEmpty() ||
			// Not menu.
			customMenuOptionClicked.target.isEmpty() && (
				!customMenuOptionClicked.option.contains("Buy-") &&
				!customMenuOptionClicked.option.equals("Continue")
			) ||
			// Start use by clicking on item.
			customMenuOptionClicked.option.equals("Use") && customMenuOptionClicked.action.equals("WIDGET_TARGET") ||
			// Cancel option.
			customMenuOptionClicked.action.equals("CANCEL") ||
			// RuneLite specific action.
			customMenuOptionClicked.action.equals("RUNELITE")
		) return;

		store.onMenuOptionClicked(customMenuOptionClicked);
		store.addConsumerToNextTickQueue(() -> {
			for (final ChargedItemBase chargedItem : chargedItems) {
				chargedItem.onMenuOptionClicked(customMenuOptionClicked);
			}
		});
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
		Arrays.stream(chargedItems).forEach(ChargedItemBase::onUserAction);
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
		// Migrate old hidden infoboxes multi-select to checkboxes.
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

	public static String getCleanText(final String text) {
		return text.replaceAll("</?col.*?>", "").replaceAll("<br>", " ").replaceAll("\u00A0"," ");
	}

	public static String getCleanChatMessage(final ChatMessage event) {
		return getCleanText(event.getMessage());
	}

	public static String menuOptionEmptyToBank = "Empty to bank";
	public static String menuOptionFillFromBank = "Fill from bank";
	public static String menuOptionEmptyToInventory = "Empty to inventory";
	public static String menuOptionFillFromInventory = "Fill from inventory";

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
}

