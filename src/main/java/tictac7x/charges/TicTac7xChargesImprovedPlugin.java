package tictac7x.charges;

import com.google.common.collect.*;
import com.google.gson.*;
import com.google.inject.*;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.gameval.*;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.widgets.*;
import net.runelite.client.*;
import net.runelite.client.callback.*;
import net.runelite.client.chat.*;
import net.runelite.client.config.*;
import net.runelite.client.eventbus.*;
import net.runelite.client.events.*;
import net.runelite.client.game.*;
import net.runelite.client.input.*;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.MouseListener;
import net.runelite.client.input.MouseWheelListener;
import net.runelite.client.plugins.*;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.infobox.*;
import net.runelite.client.ui.overlay.tooltip.*;
import net.runelite.client.util.*;
import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.overlays.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.items.barrows.*;
import tictac7x.charges.items.boots.*;
import tictac7x.charges.items.capes.*;
import tictac7x.charges.items.crystal.*;
import tictac7x.charges.items.foods.*;
import tictac7x.charges.items.helms.*;
import tictac7x.charges.items.jewelry.*;
import tictac7x.charges.items.moons.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.items.potions.P_Overload;
import tictac7x.charges.items.potions.cox.*;
import tictac7x.charges.items.potions.toa.*;
import tictac7x.charges.items.shields.*;
import tictac7x.charges.items.utils.*;
import tictac7x.charges.items.weapons.*;
import tictac7x.charges.items.weapons.blowpipes.*;
import tictac7x.charges.items.weapons.tridents.*;
import tictac7x.charges.items.weapons.venator.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.*;

import javax.inject.Inject;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;

@PluginDescriptor(
	name = "Item Charges Improved",
	description = "Show charges of various items",
	tags = {" charges "}
)

public class TicTac7xChargesImprovedPlugin extends Plugin implements KeyListener, MouseListener, MouseWheelListener {
	public static String pluginVersion = "v0.6.17";
	public static String pluginMessage =
		"<colHIGHLIGHT>Item Charges Improved " + pluginVersion + ":<br>" +
		"<colHIGHLIGHT>* Herb sack and gem pouches have in-game options to show individual charges.<br>" +
		"<colHIGHLIGHT>* Gem containers support golem crafting.<br>" +
		"<colHIGHLIGHT>* Option to disable updates messages."
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
	TicTac7xChargesImprovedConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(TicTac7xChargesImprovedConfig.class);
	}

	private Provider provider;
	private Store store;

	private ChargedItemOverlay overlayChargedItems;

	private ChargedItemBase[] chargedItems;
	private List<InfoBox> chargedItemsInfoboxes = new ArrayList<>();


	public static String INFINITE_SYMBOL = OSType.getOSType() == OSType.MacOS ? "inf" : "∞";

	@Override
	protected void startUp() {
		keyManager.registerKeyListener(this);
		mouseManager.registerMouseListener(this);
		mouseManager.registerMouseWheelListener(this);

		store = new Store(client, itemManager, configManager);
		provider = new Provider(client, clientThread, pluginManager, configManager, itemManager, infoBoxManager, chatMessageManager, tooltipManager, notifier, this, config, store, gson);
		store.addProvider(provider);

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
			new J_AbyssalBracelet(provider),
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
			new S_GhommalsHilt(provider),
			new S_KharedstMemoirs(provider),
			new S_TomeOfEarth(provider),
			new S_TomeOfFire(provider),
			new S_TomeOfWater(provider),

			// Utilities
			new U_AshSanctifier(provider),
			new U_BloodEssence(provider),
			new U_BoneCrusher(provider),
			new U_BottomlessCompostBucket(provider),
			new U_BottomlessMilkBucket(provider),
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
			new U_GemPouch(provider),
			new U_GemSack(provider),
			new U_GemSatchel(provider),
			new U_GemTote(provider),
			new U_GricollersCan(provider),
			new U_HerbSack(provider),
			new U_SilklinedHerbSack(provider),
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
			new W_CamphorBlowpipe(provider),
			new W_CrawsBow(provider),
			new W_CrystalBow(provider),
			new W_CrystalHalberd(provider),
            new W_EchoVenatorBow(provider),
			new W_EnchantedLyre(provider),
			new W_EyeOfAyak(provider),
			new W_InfernalAxe(provider),
			new W_IronwoodBlowpipe(provider),
			new W_IbansStaff(provider),
			new W_PharaohsSceptre(provider),
			new W_RosewoodBlowpipe(provider),
			new W_SanguinestiStaff(provider),
			new W_ScytheOfVitur(provider),
			new W_SkullSceptre(provider),
			new W_SlayerStaffE(provider),
			new W_ToxicBlowpipe(provider),
			new W_TridentOfTheSeas(provider),
			new W_TridentOfTheSeasE(provider),
			new W_TridentOfTheSeasO(provider),
			new W_TridentOfTheSeasEO(provider),
			new W_TridentOfTheSwamp(provider),
			new W_TridentOfTheSwampE(provider),
			new W_TridentOfTheSwampO(provider),
			new W_TridentOfTheSwampEO(provider),
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
		for (ChargedItemBase chargedItem : chargedItems) {
			ChargedItemInfobox chargedItemInfobox = new ChargedItemInfobox(provider, chargedItem);
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
	public void onChatMessage(ChatMessage event) {
		store.onChatMessage(new CustomChatMessage(
			event.getType(),
			getCleanText(event.getMessage())
		));
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event) {
		List<StorageItem> items = new ArrayList<>();

		for (Item item : event.getItemContainer().getItems()) {
			if (item == null || item.getId() == -1 || item.getId() == 6512) continue;

			ItemComposition itemComposition = itemManager.getItemComposition(item.getId());
			items.add(new StorageItem(
				itemComposition.getId(),
				itemComposition.getPlaceholderTemplateId() != -1 ? 0 : item.getQuantity()
			));
		}

		CustomItemContainerChanged itemContainerChanged = new CustomItemContainerChanged(event.getContainerId(), items);
		store.onItemContainerChanged(itemContainerChanged);
	}

	@Subscribe
	public void onGraphicChanged(GraphicChanged event) {
		if (event == null || event.getActor() == null || event.getActor() != client.getLocalPlayer()) return;

		List<Integer> graphicIds = new ArrayList<>();
		for (ActorSpotAnim spotAnim : event.getActor().getSpotAnims()) {
			graphicIds.add(spotAnim.getId());
		}
		store.onGraphicChanged(new CustomGraphicChanged(event.getActor().getName(), graphicIds));
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied event) {
		store.onHitSplatApplied(event);
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged event) {
		store.onAnimationChanged(event);
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event) {
		store.onWidgetLoaded(event);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		// Widget menu option
		if (event.getMenuAction() == MenuAction.WIDGET_CONTINUE) {
			Optional<Widget> selectedWidget = Optional.ofNullable(event.getWidget());
			if (selectedWidget.isEmpty()) return;

			Optional<Widget> parentWidget = Optional.ofNullable(selectedWidget.get().getParent());
			if (parentWidget.isEmpty()) return;

			List<String> options = new ArrayList<>();
			for (Widget subWidget : parentWidget.get().getDynamicChildren()) {
				if (subWidget.getText().isBlank()) continue;
				options.add(subWidget.getText());
			}

			CustomWidgetMenuOptionClicked widgetMenuOptionClicked = new CustomWidgetMenuOptionClicked(
				selectedWidget.get().getId(),
				options,
				selectedWidget.get().getText()
			);

			store.onWidgetMenuOptionClicked(widgetMenuOptionClicked);

		// Regular menu option
		} else {
			int impostorId;
			try {
				impostorId = client.getObjectDefinition(event.getMenuEntry().getIdentifier()).getImpostor().getId();
			} catch (Exception ignored) {
				impostorId = -1;
			}

			CustomMenuOptionClicked menuOptionClicked = new CustomMenuOptionClicked(
				event.getId(),
				event.getMenuTarget().replaceAll("</?col.*?>", ""),
				event.getMenuOption().replaceAll("</?col.*?>", ""),
				event.getMenuAction().getId(),
				event.getMenuAction().name(),
				event.getItemId(),
				impostorId
			);

			store.onMenuOptionClicked(menuOptionClicked);
		}
	}

	@Subscribe
	public void onScriptPreFired(ScriptPreFired event) {
		store.onScriptPreFired(event);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event) {
		store.onGameStateChanged(event);
	}

	@Subscribe
	public void onStatChanged(StatChanged event) {
		store.onStatChanged(event);
	}

	@Subscribe
	public void onItemDespawned(ItemDespawned event) {
		store.onItemDespawned(event);
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event) {
		store.onVarbitChanged(event);
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event) {
		store.onMenuEntryAdded(event);
	}

	@Subscribe
	public void onMenuOpened(MenuOpened event) {
		store.onMenuOpened(event);
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		store.onGameTick(event);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event) {
		store.onConfigChanged(event);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		store.onUserAction();
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
	}

	@Override
	public MouseEvent mousePressed(MouseEvent mouseEvent) {
		store.onUserAction();
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseDragged(MouseEvent mouseEvent) {
		store.onUserAction();
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseMoved(MouseEvent mouseEvent) {
		store.onUserAction();
		return mouseEvent;
	}

	@Override
	public MouseWheelEvent mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
		store.onUserAction();
		return mouseWheelEvent;
	}

	@Override
	public MouseEvent mouseClicked(MouseEvent mouseEvent) {
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseReleased(MouseEvent mouseEvent) {
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseEntered(MouseEvent mouseEvent) {
		return mouseEvent;
	}

	@Override
	public MouseEvent mouseExited(MouseEvent mouseEvent) {
		return mouseEvent;
	}

	public static String getCleanText(String text) {
		return text.replaceAll("</?col.*?>", "").replace("<br>", " ").replace("\u00A0"," ");
	}

	public static String menuOptionEmptyToBank = "Empty-to-bank";
	public static String menuOptionFillFromBank = "Fill-from-bank";
	public static String menuOptionEmptyToInventory = "Empty-to-inventory";
	public static String menuOptionFillFromInventory = "Fill-from-inventory";

	public static int getNumberFromCommaString(String charges) {
		try {
			return Integer.parseInt(charges.replaceAll(",", "").replaceAll("\\.", ""));
		} catch (Exception ignored) {
			return getNumberFromWordRepresentation(charges);
		}
	}

	public static Optional<Widget> getWidget(Client client, int parent, int child) {
		return Optional.ofNullable(client.getWidget(parent, child));
	}

	public static Optional<Widget> getWidget(Client client, int parent, int child, int subChild) {
		return getWidget(client, parent, child, Optional.of(subChild));
	}

	public static Optional<Widget> getWidget(Client client, int parent, int child, Optional<Integer> subChild) {
		Optional<Widget> widget = getWidget(client, parent, child);
		if (!widget.isPresent()) return Optional.empty();

		if (subChild.isPresent()) {
			return Optional.ofNullable(widget.get().getChild(subChild.get()));
		} else {
			return widget;
		}
	}
	
	private static ImmutableMap<String, Integer> TEXT_TO_NUMBER_MAP = ImmutableMap.<String, Integer>builder()
		.put("zero", 0).put("one", 1).put("single", 1).put("two", 2).put("three", 3).put("four", 4).put("five", 5)
		.put("six", 6).put("seven", 7).put("eight", 8).put("nine", 9).put("ten", 10)
		.put("eleven", 11).put("twelve", 12).put("thirteen", 13).put("fourteen", 14).put("fifteen", 15)
		.put("sixteen", 16).put("seventeen", 17).put("eighteen", 18).put("nineteen", 19).put("twenty", 20)
		.put("thirty", 30).put("forty", 40).put("fifty", 50).put("sixty", 60).put("seventy", 70)
		.put("eighty", 80).put("ninety", 90).put("hundred", 100).build();

	public static int getNumberFromWordRepresentation(String charges) {
		// Support strings like "twenty two" and "twenty-two"
		String[] words = charges.toLowerCase().split("[ -]");
		int result = 0;
		int current = 0;

		for (String word : words) {
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

	public static String getChargesMinified(int charges) {
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
			int thousands = charges / 1000;
			int hundreds = Math.min((charges % 1000 + 50) / 100, 9);
			return thousands + (hundreds > 0 ? "." + hundreds : "") + "K";
		}

		// As is.
		return String.valueOf(charges);
	}

	public static boolean guessIfRangedAmmoRetrievalWasSuccessful(Provider provider) {
		int recoveryRate;

		if (provider.store.equipmentContainsItem(ItemID.ANMA_30_REWARD)) {
			recoveryRate = 60;
		} else if (provider.store.equipmentContainsItem(ItemID.ANMA_50_REWARD)) {
			recoveryRate = 72;
		} else if (provider.store.equipmentContainsItem(
			ItemID.AVAS_ASSEMBLER,
			ItemID.AVAS_ASSEMBLER_TROUVER,
			ItemID.AVAS_ASSEMBLER_MASORI,
			ItemID.AVAS_ASSEMBLER_MASORI_TROUVER,
			ItemID.SKILLCAPE_MAX_ASSEMBLER,
			ItemID.SKILLCAPE_MAX_ASSEMBLER_TROUVER,
			ItemID.SKILLCAPE_MAX_ASSEMBLER_MASORI,
			ItemID.SKILLCAPE_MAX_ASSEMBLER_MASORI_TROUVER
		)) {
			recoveryRate = 80;
		} else {
			recoveryRate = 0;
		}

		return ThreadLocalRandom.current().nextInt(1, 101) > recoveryRate;
	}
}

