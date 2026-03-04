package tictac7x.charges;

import net.runelite.client.config.*;
import tictac7x.charges.store.ids.ChargeId;

import java.awt.Color;

import static tictac7x.charges.TicTac7xChargesImprovedConfig.group;

@ConfigGroup(group)
public interface TicTac7xChargesImprovedConfig extends Config {
    enum EscapeCrystalTimeRemainingUnit {
        SECONDS,
        TICKS,
    }

    enum CombatTimeDegradableStyle {
        CHARGES,
        PERCENTAGE,
        TIME,
    }

    enum ItemOverlayLocation {
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP_LEFT,
        TOP_RIGHT,
    }

    enum ItemActivity {
        DEACTIVATED,
        ACTIVATED
    }

    String group = "tictac7x-charges";
    String version = "version";
    String storage_bank = "storage_bank";
    String date = "date";
    String debug_ids = "debug_ids";
    String _infobox = "_infobox";
    String _overlay = "_overlay";
    String _storage = "_storage";

    // Armor sets
    String crystal_body = "crystal_body";
    String crystal_helm = "crystal_helm";
    String crystal_legs = "crystal_legs";
    String barrows_gear = "barrows_gear";
    String moons_gear = "moons_gear";

    // Helms
    String circlet_of_water = "circlet_of_water";
    String kandarin_headgear = "kandarin_headgear";

    // Boots
    String fremennik_sea_boots = "fremennik_sea_boots";

    // Capes
    String ardougne_cloak = "ardougne_cloak";
    String coffin = "coffin";
    String forestry_basket = "forestry_basket";
    String forestry_kit = "forestry_kit";
    String magic_cape = "magic_cape";

    // Jewelery
    String alchemists_amulet = "alchemists_amulet";
    String amulet_of_blood_fury = "amulet_of_blood_fury";
    String amulet_of_bounty = "amulet_of_bounty";
    String amulet_of_chemistry = "amulet_of_chemistry";
    String amulet_of_glory = "amulet_of_glory";
    String binding_necklace = "binding_necklace";
    String bracelet_of_clay = "bracelet_of_clay";
    String bracelet_of_slaughter = "bracelet_of_slaughter";
    String expeditious_bracelet = "expeditious_bracelet";
    String burning_amulet = "burning_amulet";
    String camulet = "camulet";
    String castle_wars_bracelet = "castle_wars_bracelet";
    String celestial_ring = "celestial_ring";
    String combat_bracelet = "combat_bracelet";
    String cowbell_amulet = "cowbell_amulet";
    String desert_amulet = "desert_amulet";
    String digsite_pendant = "digsite_pendant";
    String dodgy_necklace = "dodgy_necklace";
    String efaritays_aid = "efaritays_aid";
    String escape_crystal = "escape_crystal";
    String escape_crystal_status = "escape_crystal_status";
    String escape_crystal_inactivity_period = "escape_crystal_inactivity_period";
    String escape_crystal_time_remaining_warning = "escape_crystal_time_remaining_warning";
    String escape_crystal_time_remaining_unit = "escape_crystal_time_remaining_unit";
    String explorers_ring = "explorers_ring";
    String flamtaer_bracelet = "flamtaer_bracelet";
    String games_necklace = "games_necklace";
    String giantsoul_amulet = "giantsoul_amulet";
    String necklace_of_passage = "necklace_of_passage";
    String pendant_of_ates = "pendant_of_ates";
    String phoenix_necklace = "phoenix_necklace";
    String ring_of_dueling = "ring_of_dueling";
    String ring_of_endurance = "ring_of_endurance";
    String ring_of_forging = "ring_of_forging";
    String ring_of_pursuit = "ring_of_pursuit";
    String ring_of_recoil = "ring_of_recoil";
    String ring_of_returning = "ring_of_returning";
    String ring_of_shadows = "ring_of_shadows";
    String ring_of_suffering = "ring_of_suffering";
    String ring_of_suffering_status = "ring_of_suffering_status";
    String ring_of_the_elements = "ring_of_the_elements";
    String ring_of_wealth = "ring_of_wealth";
    String skills_necklace = "skills_necklace";
    String slayer_ring = "slayer_ring";
    String xerics_talisman = "xerics_talisman";
    String sailors_amulet = "sailors_amulet";

    String baskets = "baskets";
    String sacks = "sacks";

    // Shields
    String chronicle = "chronicle";
    String crystal_shield = "crystal_shield";
    String dragonfire_shield = "dragonfire_shield";
    String falador_shield = "falador_shield";
    String kharedsts_memoirs = "kharedsts_memoirs";
    String tome_of_earth = "tome_of_earth";
    String tome_of_fire = "tome_of_fire";
    String tome_of_water = "tome_of_water";

    // Utilities
    String ash_sanctifier = "ash_sanctifier";
    String blood_essence = "blood_essence";
    String ash_sanctifier_status = "ash_sanctifier_status";
    String bonecrusher = "bonecrusher";
    String bonecrusher_status = "bonecrusher_status";
    String bottomless_compost_bucket = "bottomless_compost_bucket";
    String bow_string_spool = "bow_string_spool";
    String chugging_barrel = "chugging_barrel";
    String coal_bag = "coal_bag";
    String colossal_pouch = "colossal_pouch";
    String colossal_pouch_decay_count = "colossal_pouch_decay_count";
    String crystal_saw = "crystal_saw";
    String ectophial = "ectophial";
    String enchanted_lyre = "enchanted_lyre";
    String fish_barrel = "fish_barrel";
    String flamtaer_bag = "flamtaer_bag";
    String fungicide_spray = "fungicide_spray";
    String fur_pouch = "fur_pouch";
    String gem_bag = "gem_bag";
    String gricollers_can = "gricollers_can";
    String herb_sack = "herb_sack";
    String jar_generator = "jar_generator";
    String log_basket = "log_basket";
    String master_scroll_book = "master_scroll_book";
    String meat_pouch = "meat_pouch";
    String huntsmans_kit = "huntsmans_kit";
    String imp_in_a_box = "imp_in_a_box";
    String ogre_bellows = "ogre_bellows";
    String plank_sack = "plank_sack";
    String quetzal_whistle = "quetzal_whistle";
    String reagent_pouch = "reagent_pouch";
    String royal_seed_pod = "royal_seed_pod";
    String seed_box = "seed_box";
    String soul_bearer = "soul_bearer";
    String strange_old_lockpick = "strange_old_lockpick";
    String tackle_box = "tackle_box";
    String teleport_crystal = "teleport_crystal";
    String eternal_teleport_crystal = "teleport_crystal";
    String watering_can = "watering_can";
    String waterskin = "waterskin";

    // Weapons
    String arclight = "arclight";
    String blazing_blowpipe = "blazing_blowpipe";
    String bow_of_faerdhinen = "bow_of_faerdhinen";
    String bryophytas_staff = "bryophytas_staff";
    String craws_bow = "craws_bow";
    String crystal_bow = "crystal_bow";
    String crystal_halberd = "crystal_halberd";
    String eye_of_ayak = "eye_of_ayak";
    String ibans_staff = "ibans_staff";
    String infernal_axe = "infernal_axe";
    String pharaohs_sceptre = "pharaohs_sceptre";
    String sanguinesti_staff = "sanguinesti_staff";
    String scythe_of_vitur = "scythe_of_vitur";
    String skull_sceptre = "skull_sceptre";
    String slayer_staff_e = "slayer_staff_e";
    String toxic_blowpipe = "toxic_blowpipe";
    String toxic_staff_of_the_dead = "toxic_staff_of_the_dead";
    String trident_of_the_seas = "trident_of_the_seas";
    String trident_of_the_seas_e = "trident_of_the_seas_e";
    String trident_of_the_swamp = "trident_of_the_swamp";
    String trident_of_the_swamp_e = "trident_of_the_swamp_e";
    String tumekens_shadow = "tumekens_shadow";
    String venator_bow = "venator_bow";
    String warped_sceptre = "warped_sceptre";
    String webweaver_bow = "webweaver_bow";
    String western_banner = "western_banner";

    @ConfigSection(
        name = "General",
        description = "General settings",
        position = 1
    ) String general = "general";

        @ConfigItem(
            keyName = "show_infoboxes",
            name = "Show infoboxes",
            description = "Show or hide all charges infoboxes simultaneously.",
            section = general,
            position = 1
        ) default boolean showInfoboxes() { return true; }

        @ConfigItem(
            keyName = "show_overlays",
            name = "Show overlays",
            description = "Show or hide all charges overlays on top of items simultaneously.",
            section = general,
            position = 2
        ) default boolean showOverlays() { return true; }

        @ConfigItem(
            keyName = "bank_overlays",
            name = "Show overlays in bank",
            description = "Show charges of the items in bank",
            section = general,
            position = 3
        ) default boolean showBankOverlays() { return true; }

        @ConfigItem(
            keyName = "hide_outside_bank_overlays",
            name = "Show overlays only while in bank",
            description = "Shows item charges overlays only when in bank",
            section = general,
            position = 4
        ) default boolean showOverlaysOnlyInBank() { return false; }

        @ConfigItem(
            keyName = "item_overlay_location",
            name = "Item overlay location",
            description = "Location of the charges for item overlays",
            section = general,
            position = 5
        ) default ItemOverlayLocation itemOverlayLocation() { return ItemOverlayLocation.BOTTOM_LEFT; }

        @ConfigItem(
            keyName = "storage_tooltips",
            name = "Show storage tooltips",
            description = "Show tooltips for items with storage",
            section = general,
            position = 6
        ) default boolean showStorageTooltips() { return true; }

        @ConfigItem(
            keyName = "hide_destroy_menu_entries",
            name = "Hide destroy menu entries",
            description = "Hide destroy menu entry from items that make no sense to destroy",
            section = general,
            position = 7
        ) default boolean hideDestroyMenuEntries() { return false; }

        @ConfigItem(
            keyName = "show_unlimited_charges",
            name = "Show unlimited charges",
            description = "Show infinity symbol for items with unlimited charges",
            section = general,
            position = 8
        ) default boolean showUnlimited() { return true; }

        @ConfigItem(
            keyName = "combat_degradable_style",
            name = "Time degradable style",
            description = "How to show charges for combat time degradable gear",
            section = general,
            position = 9
        ) default CombatTimeDegradableStyle combatTimeDegradableStyle() { return CombatTimeDegradableStyle.CHARGES; }

        @ConfigItem(
            keyName = "show_daily_reset",
            name = "Show daily reset message",
            description = "Show message in chatbox when items daily charges have been reset",
            section = general,
            position = 10
        ) default boolean showDailyReset() { return false; }

        @Alpha
        @ConfigItem(
            keyName = "colors_default",
            name = "Default",
            description = "Color of default charges",
            position = 11,
            section = general
        ) default Color getColorDefault() { return Color.white; }

        @Alpha
        @ConfigItem(
            keyName = "colors_unknown",
            name = "Unknown",
            description = "Color of unknown charges",
            position = 12,
            section = general
        ) default Color getColorUnknown() { return Color.gray; }

        @Alpha
        @ConfigItem(
            keyName = "colors_empty",
            name = "Empty",
            description = "Color of empty charges",
            position = 13,
            section = general
        ) default Color getColorEmpty() { return Color.red; }

        @Alpha
        @ConfigItem(
            keyName = "colors_activated",
            name = "Activated",
            description = "Color of activated charges",
            position = 14,
            section = general
        ) default Color getColorActivated() { return Color.green; }

    @ConfigSection(
        name = "Potions",
        description = "Potions",
        position = 2,
        closedByDefault = true
    ) String potions = "potion";

        @ConfigItem(
            keyName = potions + _infobox,
            name = "Infoboxes",
            description = "Show potions infoboxes",
            section = potions
        ) default boolean potionsInfoboxes() { return false; }

        @ConfigItem(
            keyName = potions + _overlay,
            name = "Overlays",
            description = "Show potions overlays",
            section = potions
        ) default boolean potionsOverlays() { return true; }

        @Alpha
        @ConfigItem(
            keyName = "dose_4",
            name = "4 doses",
            description = "Color of 4 doses overlay",
            position = 1,
            section = potions
        ) default Color get4DoseColor() { return Color.white; }

        @Alpha
        @ConfigItem(
            keyName = "dose_3",
            name = "3 doses",
            description = "Color of 3 doses overlay",
            position = 2,
            section = potions
        ) default Color get3DoseColor() { return Color.yellow; }

        @Alpha
        @ConfigItem(
            keyName = "dose_2",
            name = "2 doses",
            description = "Color of 2 doses overlay",
            position = 3,
            section = potions
        ) default Color get2DoseColor() { return new Color(230, 120, 0); }

        @Alpha
        @ConfigItem(
            keyName = "dose_1",
            name = "1 dose",
            description = "Color of 1 dose overlay",
            position = 4,
            section = potions
        ) default Color get1DoseColor() { return Color.red; }


    @ConfigSection(
        name = "Escape Crystal",
        description = "Escape Crystal",
        position = 3,
        closedByDefault = true
    ) String escape_crystal_section = "escape_crystal_section";

        @ConfigItem(
            keyName = escape_crystal_time_remaining_warning,
            name = "Time remaining alert",
            description = "Time before you are warned about Escape crystal activating",
            position = 4,
            section = escape_crystal_section
        ) default int getEscapeCrystalTimeRemainingWarning() { return 2; }

        @ConfigItem(
            keyName = escape_crystal_time_remaining_unit,
            name = "Time remaining unit",
            description = "Unit to use for Escape crystal activation warning",
            position = 5,
            section = escape_crystal_section
        ) default EscapeCrystalTimeRemainingUnit getEscapeCrystalTimeRemainingUnit() { return EscapeCrystalTimeRemainingUnit.SECONDS; }

    @ConfigSection(
        name = "Infoboxes",
        description = "Choose for which charged items infobox is visible",
        position = 4,
        closedByDefault = true
    ) String infoboxes = "infoboxes";

        @ConfigItem(
            keyName = binding_necklace + _infobox,
            name = "Binding necklace",
            description = "",
            section = infoboxes
        ) default boolean bindingNecklaceInfobox() { return true; }

        @ConfigItem(
            keyName = pendant_of_ates + _infobox,
            name = "Pendant of ates",
            description = "",
            section = infoboxes
        ) default boolean pendantOfAtesInfobox() { return true; }

        @ConfigItem(
            keyName = digsite_pendant + _infobox,
            name = "Digsite pendant",
            description = "",
            section = infoboxes
        ) default boolean digsitePendantInfobox() { return true; }

        @ConfigItem(
            keyName = tumekens_shadow + _infobox,
            name = "Tumeken's shadow",
            description = "",
            section = infoboxes
        ) default boolean tumekensShadowInfobox() { return true; }

        @ConfigItem(
            keyName = master_scroll_book + _infobox,
            name = "Master scroll book",
            description = "",
            section = infoboxes
        ) default boolean masterScrollBookInfobox() { return true; }

        @ConfigItem(
            keyName = reagent_pouch + _infobox,
            name = "Reagent pouch",
            description = "",
            section = infoboxes
        ) default boolean reagentPouchInfobox() { return true; }

        @ConfigItem(
            keyName = royal_seed_pod + _infobox,
            name = "Royal seed pod",
            description = "",
            section = infoboxes
        ) default boolean royalSeedPodInfobox() { return false; }

        @ConfigItem(
            keyName = ring_of_dueling + _infobox,
            name = "Ring of dueling",
            description = "",
            section = infoboxes
        ) default boolean ringOfDuelingInfobox() { return true; }

        @ConfigItem(
            keyName = ring_of_forging + _infobox,
            name = "Ring of forging",
            description = "",
            section = infoboxes
        ) default boolean ringOfForgingInfobox() { return true; }

        @ConfigItem(
            keyName = ring_of_pursuit + _infobox,
            name = "Ring of pursuit",
            description = "",
            section = infoboxes
        ) default boolean ringOfPursuitInfobox() { return true; }

        @ConfigItem(
            keyName = huntsmans_kit + _infobox,
            name = "Huntsman's kit",
            description = "",
            section = infoboxes
        ) default boolean huntsmansKitInfobox() { return true; }

        @ConfigItem(
            keyName = imp_in_a_box + _infobox,
            name = "Imp in a box",
            description = "",
            section = infoboxes
        ) default boolean impInABoxInfobox() { return true; }

        @ConfigItem(
            keyName = bow_of_faerdhinen + _infobox,
            name = "Bow of faerdhinen",
            description = "",
            section = infoboxes
        ) default boolean bowOfFaerdhinenInfobox() { return true; }

        @ConfigItem(
            keyName = venator_bow + _infobox,
            name = "Venator bow",
            description = "",
            section = infoboxes
        ) default boolean venatorBowInfobox() { return true; }

        @ConfigItem(
            keyName = meat_pouch + _infobox,
            name = "Meat pouch",
            description = "",
            section = infoboxes
        ) default boolean meatPouchInfobox() { return true; }

        @ConfigItem(
            keyName = western_banner + _infobox,
            name = "Western banner",
            description = "",
            section = infoboxes
        ) default boolean westernBannerInfobox() { return true; }

        @ConfigItem(
            keyName = barrows_gear + _infobox,
            name = "Barrows armor",
            description = "",
            section = infoboxes
        ) default boolean barrowsInfobox() { return true; }

        @ConfigItem(
            keyName = moons_gear + _infobox,
            name = "Moons armor",
            description = "",
            section = infoboxes
        ) default boolean moonsSetInfobox() { return true; }

        @ConfigItem(
            keyName = crystal_body + _infobox,
            name = "Crystal body",
            description = "",
            section = infoboxes
        ) default boolean crystalBodyInfobox() { return true; }

        @ConfigItem(
            keyName = crystal_helm + _infobox,
            name = "Crystal helm",
            description = "",
            section = infoboxes
        ) default boolean crystalHelmInfobox() { return true; }

        @ConfigItem(
            keyName = crystal_legs + _infobox,
            name = "Crystal legs",
            description = "",
            section = infoboxes
        ) default boolean crystalLegsInfobox() { return true; }

        @ConfigItem(
            keyName = fremennik_sea_boots + _infobox,
            name = "Fremennik sea boots",
            description = "",
            section = infoboxes
        ) default boolean fremennikSeaBootsInfobox() { return true; }

        @ConfigItem(
            keyName = ardougne_cloak + _infobox,
            name = "Ardougne cloak",
            description = "",
            section = infoboxes
        ) default boolean ardougneCloakInfobox() { return true; }

        @ConfigItem(
            keyName = coffin + _infobox,
            name = "Coffin",
            description = "",
            section = infoboxes
        ) default boolean coffinInfobox() { return true; }

        @ConfigItem(
            keyName = forestry_basket + _infobox,
            name = "Forestry basket",
            description = "",
            section = infoboxes
        ) default boolean forestryBasketInfobox() { return true; }

        @ConfigItem(
            keyName = forestry_kit + _infobox,
            name = "Forestry kit",
            description = "",
            section = infoboxes
        ) default boolean forestryKitInfobox() { return true; }

        @ConfigItem(
            keyName = fur_pouch + _infobox,
            name = "Fur pouch",
            description = "",
            section = infoboxes
        ) default boolean furPouchInfobox() { return true; }

        @ConfigItem(
            keyName = magic_cape + _infobox,
            name = "Magic cape",
            description = "",
            section = infoboxes
        ) default boolean magicCapeInfobox() { return true; }

        @ConfigItem(
            keyName = circlet_of_water + _infobox,
            name = "Circlet of water",
            description = "",
            section = infoboxes
        ) default boolean circletOfWaterInfobox() { return true; }

        @ConfigItem(
            keyName = chugging_barrel + _infobox,
            name = "Chugging barrel",
            description = "",
            section = infoboxes
        ) default boolean chuggingBarrelInfobox() { return true; }

        @ConfigItem(
            keyName = kandarin_headgear + _infobox,
            name = "Kandarin Headgear",
            description = "",
            section = infoboxes
        ) default boolean kandarinHeadgearInfobox() { return true; }

        @ConfigItem(
            keyName = bracelet_of_clay + _infobox,
            name = "Bracelet of clay",
            description = "",
            section = infoboxes
        ) default boolean braceletOfClayInfobox() { return true; }

        @ConfigItem(
            keyName = expeditious_bracelet + _infobox,
            name = "Expeditious bracelet",
            description = "",
            section = infoboxes
        ) default boolean expeditiousBraceletInfobox() { return true; }

        @ConfigItem(
            keyName = flamtaer_bracelet + _infobox,
            name = "Flamtaer bracelet",
            description = "",
            section = infoboxes
        ) default boolean flamtaerBraceletInfobox() { return true; }

        @ConfigItem(
            keyName = games_necklace + _infobox,
            name = "Games necklace",
            description = "",
            section = infoboxes
        ) default boolean gamesNecklaceInfobox() { return true; }

        @ConfigItem(
            keyName = bracelet_of_slaughter + _infobox,
            name = "Bracelet of slaughter",
            description = "",
            section = infoboxes
        ) default boolean braceletOfSlaughterInfobox() { return true; }

        @ConfigItem(
            keyName = camulet + _infobox,
            name = "Camulet",
            description = "",
            section = infoboxes
        ) default boolean camuletInfobox() { return true; }

        @ConfigItem(
            keyName = castle_wars_bracelet + _infobox,
            name = "Castle wars bracelet",
            description = "",
            section = infoboxes
        ) default boolean castleWarsBraceletInfobox() { return true; }

        @ConfigItem(
            keyName = desert_amulet + _infobox,
            name = "Desert amulet",
            description = "",
            section = infoboxes
        ) default boolean desertAmuletInfobox() { return true; }

        @ConfigItem(
            keyName = escape_crystal + _infobox,
            name = "Escape crystal",
            description = "",
            section = infoboxes
        ) default boolean escapeCrystalInfobox() { return true; }

        @ConfigItem(
            keyName = dodgy_necklace + _infobox,
            name = "Dodgy necklace",
            description = "",
            section = infoboxes
        ) default boolean dodgyNecklaceInfobox() { return true; }

        @ConfigItem(
            keyName = necklace_of_passage + _infobox,
            name = "Necklace of passage",
            description = "",
            section = infoboxes
        ) default boolean necklaceOfPassageInfobox() { return true; }

        @ConfigItem(
            keyName = phoenix_necklace + _infobox,
            name = "Phoenix necklace",
            description = "",
            section = infoboxes
        ) default boolean phoenixNecklaceInfobox() { return true; }

        @ConfigItem(
            keyName = celestial_ring + _infobox,
            name = "Celestial ring",
            description = "",
            section = infoboxes
        ) default boolean celestialRingInfobox() { return true; }

        @ConfigItem(
            keyName = combat_bracelet + _infobox,
            name = "Combat bracelet",
            description = "",
            section = infoboxes
        ) default boolean combatBraceletInfobox() { return true; }

        @ConfigItem(
            keyName = cowbell_amulet + _infobox,
            name = "Cowbell amulet",
            description = "",
            section = infoboxes
        ) default boolean cowbellAmuletInfobox() { return true; }

        @ConfigItem(
            keyName = ring_of_the_elements + _infobox,
            name = "Ring of the elements",
            description = "",
            section = infoboxes
        ) default boolean ringOfTheElementsInfobox() { return true; }

        @ConfigItem(
            keyName = ring_of_wealth + _infobox,
            name = "Ring of wealth",
            description = "",
            section = infoboxes
        ) default boolean ringOfWealthInfobox() { return true; }

        @ConfigItem(
            keyName = ring_of_endurance + _infobox,
            name = "Ring of endurance",
            description = "",
            section = infoboxes
        ) default boolean ringOfEnduranceInfobox() { return true; }

        @ConfigItem(
            keyName = explorers_ring + _infobox,
            name = "Explorer's ring",
            description = "",
            section = infoboxes
        ) default boolean explorersRingInfobox() { return true; }

        @ConfigItem(
            keyName = ring_of_recoil + _infobox,
            name = "Ring of recoil",
            description = "",
            section = infoboxes
        ) default boolean ringOfRecoilInfobox() { return true; }

        @ConfigItem(
            keyName = ring_of_returning + _infobox,
            name = "Ring of returning",
            description = "",
            section = infoboxes
        ) default boolean ringOfReturningInfobox() { return true; }

        @ConfigItem(
            keyName = ring_of_shadows + _infobox,
            name = "Ring of shadows",
            description = "",
            section = infoboxes
        ) default boolean ringOfShadowsInfobox() { return true; }

        @ConfigItem(
            keyName = slayer_ring + _infobox,
            name = "Slayer ring",
            description = "",
            section = infoboxes
        ) default boolean slayerRingInfobox() { return true; }

        @ConfigItem(
            keyName = ring_of_suffering + _infobox,
            name = "Ring of suffering",
            description = "",
            section = infoboxes
        ) default boolean ringOfSufferingInfobox() { return true; }

        @ConfigItem(
            keyName = xerics_talisman + _infobox,
            name = "Xeric's talisman",
            description = "",
            section = infoboxes
        ) default boolean xericsTalismanInfobox() { return true; }

        @ConfigItem(
            keyName =  sailors_amulet + _infobox,
            name = "Sailors' Amulet",
            description = "",
            section = infoboxes
        ) default boolean sailorsAmuletInfobox() { return true; }

        @ConfigItem(
            keyName =  baskets + _infobox,
            name = "Baskets",
            description = "",
            section = infoboxes
        ) default boolean basketsInfobox() { return true; }

        @ConfigItem(
            keyName =  sacks + _infobox,
            name = "Sacks",
            description = "",
            section = infoboxes
        ) default boolean sacksInfobox() { return true; }

        @ConfigItem(
            keyName = chronicle + _infobox,
            name = "Chronicle",
            description = "",
            section = infoboxes
        ) default boolean chronicleInfobox() { return true; }

        @ConfigItem(
            keyName = crystal_shield + _infobox,
            name = "Crystal shield",
            description = "",
            section = infoboxes
        ) default boolean crystalShieldInfobox() { return true; }

        @ConfigItem(
            keyName = dragonfire_shield + _infobox,
            name = "Dragonfire shield",
            description = "",
            section = infoboxes
        ) default boolean dragonfireShieldInfobox() { return true; }

        @ConfigItem(
            keyName = falador_shield + _infobox,
            name = "Falador shield",
            description = "",
            section = infoboxes
        ) default boolean faladorShieldInfobox() { return true; }

        @ConfigItem(
            keyName = kharedsts_memoirs + _infobox,
            name = "Kharedst's memoirs",
            description = "",
            section = infoboxes
        ) default boolean kharedstsMemoirsInfobox() { return true; }

        @ConfigItem(
            keyName = kharedsts_memoirs + _infobox,
            name = "Book of the dead",
            description = "",
            section = infoboxes
        ) default boolean bookOfTheDeadInfobox() { return true; }

        @ConfigItem(
            keyName = tome_of_earth + _infobox,
            name = "Tome of earth",
            description = "",
            section = infoboxes
        ) default boolean tomeOfEarthInfobox() { return true; }

        @ConfigItem(
            keyName = tome_of_fire + _infobox,
            name = "Tome of fire",
            description = "",
            section = infoboxes
        ) default boolean tomeOfFireInfobox() { return true; }

        @ConfigItem(
            keyName = tome_of_water + _infobox,
            name = "Tome of water",
            description = "",
            section = infoboxes
        ) default boolean tomeOfWaterInfobox() { return true; }

        @ConfigItem(
            keyName = ash_sanctifier + _infobox,
            name = "Ash sanctifier",
            description = "",
            section = infoboxes
        ) default boolean ashSanctifierInfobox() { return true; }

        @ConfigItem(
            keyName = blood_essence + _infobox,
            name = "Blood essence",
            description = "",
            section = infoboxes
        ) default boolean bloodEssenceInfobox() { return true; }

        @ConfigItem(
            keyName = bonecrusher + _infobox,
            name = "Bonecrusher",
            description = "",
            section = infoboxes
        ) default boolean bonecrusherInfobox() { return true; }

        @ConfigItem(
            keyName = bottomless_compost_bucket + _infobox,
            name = "Bottomless compost bucket",
            description = "",
            section = infoboxes
        ) default boolean bottomlessCompostBucketInfobox() { return true; }

        @ConfigItem(
            keyName = bow_string_spool + _infobox,
            name = "Bow string spool",
            description = "",
            section = infoboxes
        ) default boolean bowStringSpoolInfobox() { return true; }

        @ConfigItem(
            keyName = coal_bag + _infobox,
            name = "Coal bag",
            description = "",
            section = infoboxes
        ) default boolean coalBagInfobox() { return true; }

        @ConfigItem(
            keyName = colossal_pouch + _infobox,
            name = "Colossal pouch",
            description = "",
            section = infoboxes
        ) default boolean colossalPouchInfobox() { return true; }

        @ConfigItem(
            keyName = crystal_saw + _infobox,
            name = "Crystal saw",
            description = "",
            section = infoboxes
        ) default boolean crystalSawInfobox() { return true; }

        @ConfigItem(
            keyName = ectophial + _infobox,
            name = "Ectophial",
            description = "",
            section = infoboxes
        ) default boolean ectophialInfobox() { return false; }

        @ConfigItem(
            keyName = fish_barrel + _infobox,
            name = "Fish barrel",
            description = "",
            section = infoboxes
        ) default boolean fishBarrelInfobox() { return true; }

        @ConfigItem(
            keyName = flamtaer_bag + _infobox,
            name = "Flamtaer bag",
            description = "",
            section = infoboxes
        ) default boolean flamtaerBagInfobox() { return true; }

        @ConfigItem(
            keyName = fungicide_spray + _infobox,
            name = "Fungicide spray",
            description = "",
            section = infoboxes
        ) default boolean fungicideSprayInfobox() { return true; }

        @ConfigItem(
            keyName = gem_bag + _infobox,
            name = "Gem bag",
            description = "",
            section = infoboxes
        ) default boolean gemBagInfobox() { return true; }

        @ConfigItem(
            keyName = giantsoul_amulet + _infobox,
            name = "Giantsoul amulet",
            description = "",
            section = infoboxes
        ) default boolean giantsoulAmuletInfobox() { return true; }

        @ConfigItem(
            keyName = gricollers_can + _infobox,
            name = "Gricoller's can",
            description = "",
            section = infoboxes
        ) default boolean gricollersCanInfobox() { return true; }

        @ConfigItem(
            keyName = herb_sack + _infobox,
            name = "Herb sack",
            description = "",
            section = infoboxes
        ) default boolean herbSackInfobox() { return true; }

        @ConfigItem(
            keyName = jar_generator + _infobox,
            name = "Jar generator",
            description = "",
            section = infoboxes
        ) default boolean jarGeneratorInfobox() { return true; }

        @ConfigItem(
            keyName = log_basket + _infobox,
            name = "Log basket",
            description = "",
            section = infoboxes
        ) default boolean logBasketInfobox() { return true; }

        @ConfigItem(
            keyName = ogre_bellows + _infobox,
            name = "Ogre bellows",
            description = "",
            section = infoboxes
        ) default boolean ogreBellowsInfobox() { return true; }

        @ConfigItem(
            keyName = plank_sack + _infobox,
            name = "Plank sack",
            description = "",
            section = infoboxes
        ) default boolean plankSackInfobox() { return true; }

        @ConfigItem(
            keyName = quetzal_whistle + _infobox,
            name = "Quetzal whistle",
            description = "",
            section = infoboxes
        ) default boolean quetzalWhistleInfobox() { return true; }

        @ConfigItem(
            keyName = seed_box + _infobox,
            name = "Seed box",
            description = "",
            section = infoboxes
        ) default boolean seedBoxInfobox() { return true; }

        @ConfigItem(
            keyName = skills_necklace + _infobox,
            name = "Skills necklace",
            description = "",
            section = infoboxes
        ) default boolean skillsNecklaceInfobox() { return true; }

        @ConfigItem(
            keyName = soul_bearer + _infobox,
            name = "Soul bearer",
            description = "",
            section = infoboxes
        ) default boolean soulBearerInfobox() { return true; }

        @ConfigItem(
            keyName = strange_old_lockpick + _infobox,
            name = "Strange old lockpick",
            description = "",
            section = infoboxes
        ) default boolean strangeOldLockpickInfobox() { return true; }

        @ConfigItem(
            keyName = tackle_box + _infobox,
            name = "Tackle box",
            description = "",
            section = infoboxes
        ) default boolean tackleBoxInfobox() { return true; }

        @ConfigItem(
            keyName = teleport_crystal + _infobox,
            name = "Teleport crystal",
            description = "",
            section = infoboxes
        ) default boolean teleportCrystalInfobox() { return true; }

        @ConfigItem(
            keyName = eternal_teleport_crystal + _infobox,
            name = "Eternal teleport crystal",
            description = "",
            section = infoboxes
        ) default boolean eternalTeleportCrystalInfobox() { return true; }

        @ConfigItem(
            keyName = watering_can + _infobox,
            name = "Watering can",
            description = "",
            section = infoboxes
        ) default boolean wateringCanInfobox() { return true; }

        @ConfigItem(
            keyName = waterskin + _infobox,
            name = "Waterskin",
            description = "",
            section = infoboxes
        ) default boolean waterskinInfobox() { return true; }

        @ConfigItem(
            keyName = arclight + _infobox,
            name = "Arclight",
            description = "",
            section = infoboxes
        ) default boolean arclightInfobox() { return true; }

        @ConfigItem(
            keyName = blazing_blowpipe + _infobox,
            name = "Blazing blowpipe",
            description = "",
            section = infoboxes
        ) default boolean blazingBlowpipeInfobox() { return true; }

        @ConfigItem(
            keyName = bryophytas_staff + _infobox,
            name = "Bryophyta's staff",
            description = "",
            section = infoboxes
        ) default boolean bryophytasStaffInfobox() { return true; }

        @ConfigItem(
            keyName = craws_bow + _infobox,
            name = "Craw's bow",
            description = "",
            section = infoboxes
        ) default boolean crawsBowInfobox() { return true; }

        @ConfigItem(
            keyName = burning_amulet + _infobox,
            name = "Burning amulet",
            description = "",
            section = infoboxes
        ) default boolean burninAmuletInfobox() { return true; }

        @ConfigItem(
            keyName = crystal_bow + _infobox,
            name = "Crystal bow",
            description = "",
            section = infoboxes
        ) default boolean crystalBowInfobox() { return true; }

        @ConfigItem(
            keyName = crystal_halberd + _infobox,
            name = "Crystal halberd",
            description = "",
            section = infoboxes
        ) default boolean crystalHalberdInfobox() { return true; }

        @ConfigItem(
            keyName = efaritays_aid + _infobox,
            name = "Efaritay's aid",
            description = "",
            section = infoboxes
        ) default boolean efaritaysAidInfobox() { return true; }

        @ConfigItem(
            keyName = enchanted_lyre + _infobox,
            name = "Enchanted Lyre",
            description = "",
            section = infoboxes
        ) default boolean enchantedLyreInfobox() { return true; }

        @ConfigItem(
            keyName = ibans_staff + _infobox,
            name = "Iban's staff",
            description = "",
            section = infoboxes
        ) default boolean ibansStaffInfobox() { return true; }

        @ConfigItem(
            keyName = infernal_axe + _infobox,
            name = "Infernal axe",
            description = "",
            section = infoboxes
        ) default boolean infernalAxeInfobox() { return true; }

        @ConfigItem(
            keyName = pharaohs_sceptre + _infobox,
            name = "Pharaoh's sceptre",
            description = "",
            section = infoboxes
        ) default boolean pharaohsSceptreInfobox() { return true; }

        @ConfigItem(
            keyName = sanguinesti_staff + _infobox,
            name = "Sanguinesti staff",
            description = "",
            section = infoboxes
        ) default boolean sanguinestiStaffInfobox() { return true; }

        @ConfigItem(
            keyName = scythe_of_vitur + _infobox,
            name = "Scythe of Vitur",
            description = "",
            section = infoboxes
        ) default boolean scytheOfViturInfobox() { return true; }

        @ConfigItem(
            keyName = skull_sceptre + _infobox,
            name = "Skull sceptre",
            description = "",
            section = infoboxes
        ) default boolean skullSceptreInfobox() { return true; }

        @ConfigItem(
            keyName = slayer_staff_e + _infobox,
            name = "Slayer staff (e)",
            description = "",
            section = infoboxes
        ) default boolean slayerStaffEInfobox() { return true; }

        @ConfigItem(
            keyName = toxic_blowpipe + _infobox,
            name = "Toxic blowpipe",
            description = "",
            section = infoboxes
        ) default boolean toxicBlowpipeInfobox() { return true; }

        @ConfigItem(
            keyName = toxic_staff_of_the_dead + _infobox,
            name = "Toxic staff of the dead",
            description = "",
            section = infoboxes
        ) default boolean toxicStaffOfTheDeadInfobox() { return true; }

        @ConfigItem(
            keyName = trident_of_the_seas + _infobox,
            name = "Trident of the seas",
            description = "",
            section = infoboxes
        ) default boolean tridentOfTheSeasInfobox() { return true; }

        @ConfigItem(
            keyName = trident_of_the_seas_e + _infobox,
            name = "Trident of the seas (e)",
            description = "",
            section = infoboxes
        ) default boolean tridentOfTheSeasEInfobox() { return true; }

        @ConfigItem(
            keyName = trident_of_the_swamp + _infobox,
            name = "Trident of the swamp",
            description = "",
            section = infoboxes
        ) default boolean tridentOfTheSwampInfobox() { return true; }

        @ConfigItem(
            keyName = trident_of_the_swamp_e + _infobox,
            name = "Trident of the swamp (e)",
            description = "",
            section = infoboxes
        ) default boolean tridentOfTheSwampEInfobox() { return true; }

        @ConfigItem(
            keyName = warped_sceptre + _infobox,
            name = "Warped sceptre",
            description = "",
            section = infoboxes
        ) default boolean warpedSceptreInfobox() { return true; }

        @ConfigItem(
            keyName = webweaver_bow + _infobox,
            name = "Webweaver bow",
            description = "",
            section = infoboxes
        ) default boolean webweaverBowInfobox() { return true; }

        @ConfigItem(
            keyName = alchemists_amulet + _infobox,
            name = "Alchemist's amulet",
            description = "",
            section = infoboxes
        ) default boolean alchemistsAmuletInfobox() { return true; }

        @ConfigItem(
            keyName = amulet_of_blood_fury + _infobox,
            name = "Amulet of blood fury",
            description = "",
            section = infoboxes
        ) default boolean amuletOfBloodFuryInfobox() { return true; }

        @ConfigItem(
            keyName = amulet_of_bounty + _infobox,
            name = "Amulet of bounty",
            description = "",
            section = infoboxes
        ) default boolean amuletOfBountyInfobox() { return true; }

        @ConfigItem(
            keyName = amulet_of_chemistry + _infobox,
            name = "Amulet of chemistry",
            description = "",
            section = infoboxes
        ) default boolean amuletOfChemistryInfobox() { return true; }

        @ConfigItem(
            keyName = amulet_of_glory + _infobox,
            name = "Amulet of glory",
            description = "",
            section = infoboxes
        ) default boolean amuletOfGloryInfobox() { return true; }

        @ConfigItem(
                keyName = eye_of_ayak + _infobox,
                name = "Eye of Ayak",
                description = "",
                section = infoboxes
        ) default boolean eyeOfAyakInfobox() { return true; }

    @ConfigSection(
        name = "Overlays",
        description = "Choose for which charged items number is shown next to it",
        position = 5,
        closedByDefault = true
    ) String overlays = "overlays";

        @ConfigItem(
            keyName = binding_necklace + _overlay,
            name = "Binding necklace",
            description = "",
            section = overlays
        ) default boolean bindingNecklaceOverlay() { return true; }

        @ConfigItem(
            keyName = pendant_of_ates + _overlay,
            name = "Pendant of ates",
            description = "",
            section = overlays
        ) default boolean pendantOfAtesOverlay() { return true; }

        @ConfigItem(
            keyName = digsite_pendant + _overlay,
            name = "Digsite pendant",
            description = "",
            section = overlays
        ) default boolean digsitePendantOverlay() { return true; }

        @ConfigItem(
            keyName = tumekens_shadow + _overlay,
            name = "Tumeken's shadow",
            description = "",
            section = overlays
        ) default boolean tumekensShadowOverlay() { return true; }

        @ConfigItem(
            keyName = master_scroll_book + _overlay,
            name = "Master scroll book",
            description = "",
            section = overlays
        ) default boolean masterScrollBookOverlay() { return true; }

        @ConfigItem(
            keyName = reagent_pouch + _overlay,
            name = "Reagent pouch",
            description = "",
            section = overlays
        ) default boolean reagentPouchOverlay() { return true; }

        @ConfigItem(
            keyName = royal_seed_pod + _overlay,
            name = "Royal seed pod",
            description = "",
            section = overlays
        ) default boolean royalSeedPodOverlay() { return false; }

        @ConfigItem(
            keyName = ring_of_dueling + _overlay,
            name = "Ring of dueling",
            description = "",
            section = overlays
        ) default boolean ringOfDuelingOverlay() { return true; }

        @ConfigItem(
            keyName = ring_of_forging + _overlay,
            name = "Ring of forging",
            description = "",
            section = overlays
        ) default boolean ringOfForgingOverlay() { return true; }

        @ConfigItem(
            keyName = ring_of_pursuit + _overlay,
            name = "Ring of pursuit",
            description = "",
            section = overlays
        ) default boolean ringOfPursuitOverlay() { return true; }

        @ConfigItem(
            keyName = huntsmans_kit + _overlay,
            name = "Huntsman's kit",
            description = "",
            section = overlays
        ) default boolean huntsmansKitOverlay() { return true; }

        @ConfigItem(
            keyName = imp_in_a_box + _overlay,
            name = "Imp in a box",
            description = "",
            section = overlays
        ) default boolean impInABoxOverlay() { return true; }

        @ConfigItem(
            keyName = arclight + _overlay,
            name = "Arclight",
            description = "",
            section = overlays
        ) default boolean arclightOverlay() { return true; }

        @ConfigItem(
            keyName = blazing_blowpipe + _overlay,
            name = "Blazing blowpipe",
            description = "",
            section = overlays
        ) default boolean blazingBlowpipeOverlay() { return true; }

        @ConfigItem(
            keyName = ardougne_cloak + _overlay,
            name = "Ardougne cloak",
            description = "",
            section = overlays
        ) default boolean ardougneCloakOverlay() { return true; }

        @ConfigItem(
            keyName = ash_sanctifier + _overlay,
            name = "Ash sanctifier",
            description = "",
            section = overlays
        ) default boolean ashSanctifierOverlay() { return true; }

        @ConfigItem(
            keyName = blood_essence + _overlay,
            name = "Blood essence",
            description = "",
            section = overlays
        ) default boolean bloodEssenceOverlay() { return true; }

        @ConfigItem(
            keyName = barrows_gear + _overlay,
            name = "Barrows armor",
            description = "",
            section = overlays
        ) default boolean barrowsOverlay() { return true; }

        @ConfigItem(
            keyName = moons_gear + _overlay,
            name = "Moons armor",
            description = "",
            section = overlays
        ) default boolean moonsGearOverlay() { return true; }

        @ConfigItem(
            keyName = bonecrusher + _overlay,
            name = "Bonecrusher",
            description = "",
            section = overlays
        ) default boolean bonecrusherOverlay() { return true; }

        @ConfigItem(
            keyName = bottomless_compost_bucket + _overlay,
            name = "Bottomless compost bucket",
            description = "",
            section = overlays
        ) default boolean bottomlessCompostBucketOverlay() { return true; }

        @ConfigItem(
            keyName = bow_string_spool + _overlay,
            name = "Bow string spool",
            description = "",
            section = overlays
        ) default boolean bowStringSpoolOverlay() { return true; }

        @ConfigItem(
            keyName = bow_of_faerdhinen + _overlay,
            name = "Bow of faerdhinen",
            description = "",
            section = overlays
        ) default boolean bowOfFaerdhinenOverlay() { return true; }

        @ConfigItem(
            keyName = bracelet_of_clay + _overlay,
            name = "Bracelet of clay",
            description = "",
            section = overlays
        ) default boolean braceletOfClayOverlay() { return true; }

        @ConfigItem(
            keyName = expeditious_bracelet + _overlay,
            name = "Expeditious bracelet",
            description = "",
            section = overlays
        ) default boolean expeditiousBraceletOverlay() { return true; }

        @ConfigItem(
            keyName = flamtaer_bracelet + _overlay,
            name = "Flamtaer bracelet",
            description = "",
            section = overlays
        ) default boolean flamtaerBraceletOverlay() { return true; }

        @ConfigItem(
            keyName = games_necklace + _overlay,
            name = "Games necklace",
            description = "",
            section = overlays
        ) default boolean gamesNecklaceOverlay() { return true; }

        @ConfigItem(
            keyName = bracelet_of_slaughter + _overlay,
            name = "Bracelet of slaughter",
            description = "",
            section = overlays
        ) default boolean braceletOfSlaughterOverlay() { return true; }

        @ConfigItem(
            keyName = camulet + _overlay,
            name = "Camulet",
            description = "",
            section = overlays
        ) default boolean camuletOverlay() { return true; }

        @ConfigItem(
            keyName = castle_wars_bracelet + _overlay,
            name = "Castle wars bracelet",
            description = "",
            section = overlays
        ) default boolean castleWarsBraceletOverlay() { return true; }

        @ConfigItem(
            keyName = celestial_ring + _overlay,
            name = "Celestial ring",
            description = "",
            section = overlays
        ) default boolean celestialRingOverlay() { return true; }

        @ConfigItem(
            keyName = combat_bracelet + _overlay,
            name = "Combat bracelet",
            description = "",
            section = overlays
        ) default boolean combatBraceletOverlay() { return true; }

        @ConfigItem(
            keyName = cowbell_amulet + _overlay,
            name = "Cowbell amulet",
            description = "",
            section = overlays
        ) default boolean cowbellAmuletOverlay() { return true; }

        @ConfigItem(
            keyName = chronicle + _overlay,
            name = "Chronicle",
            description = "",
            section = overlays
        ) default boolean chronicleOverlay() { return true; }

        @ConfigItem(
            keyName = circlet_of_water + _overlay,
            name = "Circlet of water",
            description = "",
            section = overlays
        ) default boolean circletOfWaterOverlay() { return true; }

        @ConfigItem(
            keyName = chugging_barrel + _overlay,
            name = "Chugging barrel",
            description = "",
            section = overlays
        ) default boolean chuggingBarrelOverlay() { return true; }

        @ConfigItem(
            keyName = coal_bag + _overlay,
            name = "Coal bag",
            description = "",
            section = overlays
        ) default boolean coalBagOverlay() { return true; }

        @ConfigItem(
            keyName = colossal_pouch + _overlay,
            name = "Colossal pouch",
            description = "",
            section = overlays
        ) default boolean colossalPouchOverlay() { return true; }

        @ConfigItem(
            keyName = coffin + _overlay,
            name = "Coffin",
            description = "",
            section = overlays
        ) default boolean coffinOverlay() { return true; }

        @ConfigItem(
            keyName = crystal_body + _overlay,
            name = "Crystal body",
            description = "",
            section = overlays
        ) default boolean crystalBodyOverlay() { return true; }

        @ConfigItem(
            keyName = crystal_helm + _overlay,
            name = "Crystal helm",
            description = "",
            section = overlays
        ) default boolean crystalHelmOverlay() { return true; }

        @ConfigItem(
            keyName = crystal_legs + _overlay,
            name = "Crystal legs",
            description = "",
            section = overlays
        ) default boolean crystalLegsOverlay() { return true; }

        @ConfigItem(
            keyName = crystal_saw + _overlay,
            name = "Crystal saw",
            description = "",
            section = overlays
        ) default boolean crystalSawOverlay() { return true; }

        @ConfigItem(
            keyName = ectophial + _overlay,
            name = "Ectophial",
            description = "",
            section = overlays
        ) default boolean ectophialOverlay() { return false; }

        @ConfigItem(
            keyName = crystal_shield + _overlay,
            name = "Crystal shield",
            description = "",
            section = overlays
        ) default boolean crystalShieldOverlay() { return true; }

        @ConfigItem(
            keyName = desert_amulet + _overlay,
            name = "Desert amulet",
            description = "",
            section = overlays
        ) default boolean desertAmuletOverlay() { return true; }

        @ConfigItem(
            keyName = dragonfire_shield + _overlay,
            name = "Dragonfire shield",
            description = "",
            section = overlays
        ) default boolean dragonfireShieldOverlay() { return true; }

        @ConfigItem(
            keyName = falador_shield + _overlay,
            name = "Falador shield",
            description = "",
            section = overlays
        ) default boolean faladorShieldOverlay() { return true; }

        @ConfigItem(
            keyName = fur_pouch + _overlay,
            name = "Fur pouch",
            description = "",
            section = overlays
        ) default boolean furPouchOverlay() { return true; }

        @ConfigItem(
            keyName = escape_crystal + _overlay,
            name = "Escape crystal",
            description = "",
            section = overlays
        ) default boolean escapeCrystalOverlay() { return true; }

        @ConfigItem(
            keyName = explorers_ring + _overlay,
            name = "Explorer's ring",
            description = "",
            section = overlays
        ) default boolean explorersRingOverlay() { return true; }

        @ConfigItem(
            keyName = dodgy_necklace + _overlay,
            name = "Dodgy necklace",
            description = "",
            section = overlays
        ) default boolean dodgyNecklaceOverlay() { return true; }

        @ConfigItem(
            keyName = fish_barrel + _overlay,
            name = "Fish barrel",
            description = "",
            section = overlays
        ) default boolean fishBarrelOverlay() { return true; }

        @ConfigItem(
            keyName = flamtaer_bag + _overlay,
            name = "Flamtaer bag",
            description = "",
            section = overlays
        ) default boolean flamtaerBagOverlay() { return true; }

        @ConfigItem(
            keyName = forestry_basket + _overlay,
            name = "Forestry basket",
            description = "",
            section = overlays
        ) default boolean forestryBasketOverlay() { return true; }

        @ConfigItem(
            keyName = forestry_kit + _overlay,
            name = "Forestry kit",
            description = "",
            section = overlays
        ) default boolean forestryKitOverlay() { return true; }

        @ConfigItem(
            keyName = fremennik_sea_boots + _overlay,
            name = "Fremennik sea boots",
            description = "",
            section = overlays
        ) default boolean fremennikSeaBootsOverlay() { return true; }

        @ConfigItem(
            keyName = fungicide_spray + _overlay,
            name = "Fungicide spray",
            description = "",
            section = overlays
        ) default boolean fungicideSprayOverlay() { return true; }

        @ConfigItem(
            keyName = gem_bag + _overlay,
            name = "Gem bag",
            description = "",
            section = overlays
        ) default boolean gemBagOverlay() { return true; }

        @ConfigItem(
            keyName = giantsoul_amulet + _overlay,
            name = "Giantsoul amulet",
            description = "",
            section = overlays
        ) default boolean giantsoulAmuletOverlay() { return true; }

        @ConfigItem(
            keyName = gricollers_can + _overlay,
            name = "Gricoller's can",
            description = "",
            section = overlays
        ) default boolean gricollersCanOverlay() { return true; }

        @ConfigItem(
            keyName = herb_sack + _overlay,
            name = "Herb sack",
            description = "",
            section = overlays
        ) default boolean herbSackOverlay() { return true; }

        @ConfigItem(
            keyName = jar_generator + _overlay,
            name = "Jar generator",
            description = "",
            section = overlays
        ) default boolean jarGeneratorOverlay() { return true; }

        @ConfigItem(
            keyName = kandarin_headgear + _overlay,
            name = "Kandarin Headgear",
            description = "",
            section = overlays
        ) default boolean kandarinHeadgearOverlay() { return true; }

        @ConfigItem(
            keyName = kharedsts_memoirs + _overlay,
            name = "Kharedst's memoirs",
            description = "",
            section = overlays
        ) default boolean kharedstsMemoirsOverlay() { return true; }

        @ConfigItem(
            keyName = kharedsts_memoirs + _overlay,
            name = "Book of the dead",
            description = "",
            section = overlays
        ) default boolean bookOfTheDeadOverlay() { return true; }

        @ConfigItem(
            keyName = log_basket + _overlay,
            name = "Log basket",
            description = "",
            section = overlays
        ) default boolean logBasketOverlay() { return true; }

        @ConfigItem(
            keyName = magic_cape + _overlay,
            name = "Magic cape",
            description = "",
            section = overlays
        ) default boolean magicCapeOverlay() { return true; }

        @ConfigItem(
            keyName = meat_pouch + _overlay,
            name = "Meat pouch",
            description = "",
            section = overlays
        ) default boolean meatPouchOverlay() { return true; }

        @ConfigItem(
            keyName = necklace_of_passage + _overlay,
            name = "Necklace of passage",
            description = "",
            section = overlays
        ) default boolean necklaceOfPassageOverlay() { return true; }

        @ConfigItem(
            keyName = ogre_bellows + _overlay,
            name = "Ogre bellows",
            description = "",
            section = overlays
        ) default boolean ogreBellowsOverlay() { return true; }

        @ConfigItem(
            keyName = phoenix_necklace + _overlay,
            name = "Phoenix necklace",
            description = "",
            section = overlays
        ) default boolean phoenixNecklaceOverlay() { return true; }

        @ConfigItem(
            keyName = plank_sack + _overlay,
            name = "Plank sack",
            description = "",
            section = overlays
        ) default boolean plankSackOverlay() { return true; }

        @ConfigItem(
            keyName = ring_of_recoil + _overlay,
            name = "Ring of recoil",
            description = "",
            section = overlays
        ) default boolean ringOfRecoilOverlay() { return true; }

        @ConfigItem(
            keyName = ring_of_returning + _overlay,
            name = "Ring of returning",
            description = "",
            section = overlays
        ) default boolean ringOfReturningOverlay() { return true; }

        @ConfigItem(
            keyName = ring_of_shadows + _overlay,
            name = "Ring of shadows",
            description = "",
            section = overlays
        ) default boolean ringOfShadowsOverlay() { return true; }

        @ConfigItem(
            keyName = ring_of_suffering + _overlay,
            name = "Ring of suffering",
            description = "",
            section = overlays
        ) default boolean ringOfSufferingOverlay() { return true; }

        @ConfigItem(
            keyName = ring_of_the_elements + _overlay,
            name = "Ring of the elements",
            description = "",
            section = overlays
        ) default boolean ringOfTheElementsOverlay() { return true; }

        @ConfigItem(
            keyName = ring_of_wealth + _overlay,
            name = "Ring of wealth",
            description = "",
            section = overlays
        ) default boolean ringOfWealthOverlay() { return true; }

        @ConfigItem(
            keyName = ring_of_endurance + _overlay,
            name = "Ring of endurance",
            description = "",
            section = overlays
        ) default boolean ringOfEnduranceOverlay() { return true; }

        @ConfigItem(
            keyName = seed_box + _overlay,
            name = "Seed box",
            description = "",
            section = overlays
        ) default boolean seedBoxOverlay() { return true; }

        @ConfigItem(
            keyName = skills_necklace + _overlay,
            name = "Skills necklace",
            description = "",
            section = overlays
        ) default boolean skillsNecklaceOverlay() { return true; }

        @ConfigItem(
            keyName = slayer_ring + _overlay,
            name = "Slayer ring",
            description = "",
            section = overlays
        ) default boolean slayerRingOverlay() { return true; }

        @ConfigItem(
            keyName = soul_bearer + _overlay,
            name = "Soul bearer",
            description = "",
            section = overlays
        ) default boolean soulBearerOverlay() { return true; }

        @ConfigItem(
            keyName = strange_old_lockpick + _overlay,
            name = "Strange old lockpick",
            description = "",
            section = overlays
        ) default boolean strangeOldLockpickOverlay() { return true; }

        @ConfigItem(
            keyName = tackle_box + _overlay,
            name = "Tackle box",
            description = "",
            section = overlays
        ) default boolean tackleBoxOverlay() { return true; }

        @ConfigItem(
            keyName = teleport_crystal + _overlay,
            name = "Teleport crystal",
            description = "",
            section = overlays
        ) default boolean teleportCrystalOverlay() { return true; }

        @ConfigItem(
            keyName = eternal_teleport_crystal + _overlay,
            name = "Eternal teleport crystal",
            description = "",
            section = overlays
        ) default boolean eternalTeleportCrystalOverlay() { return true; }

        @ConfigItem(
            keyName = tome_of_earth + _overlay,
            name = "Tome of earth",
            description = "",
            section = overlays
        ) default boolean tomeOfEarthOverlay() { return true; }

        @ConfigItem(
            keyName = tome_of_fire + _overlay,
            name = "Tome of fire",
            description = "",
            section = overlays
        ) default boolean tomeOfFireOverlay() { return true; }

        @ConfigItem(
            keyName = tome_of_water + _overlay,
            name = "Tome of water",
            description = "",
            section = overlays
        ) default boolean tomeOfWaterOverlay() { return true; }

        @ConfigItem(
            keyName = venator_bow + _overlay,
            name = "Venator bow",
            description = "",
            section = overlays
        ) default boolean venatorBowOverlay() { return true; }

        @ConfigItem(
            keyName = watering_can + _overlay,
            name = "Watering can",
            description = "",
            section = overlays
        ) default boolean wateringCanOverlay() { return true; }

        @ConfigItem(
            keyName = waterskin + _overlay,
            name = "Waterskin",
            description = "",
            section = overlays
        ) default boolean waterskinOverlay() { return true; }

        @ConfigItem(
            keyName = western_banner + _overlay,
            name = "Western banner",
            description = "",
            section = overlays
        ) default boolean westernBannerOverlay() { return true; }

        @ConfigItem(
            keyName = bryophytas_staff + _overlay,
            name = "Bryophyta's staff",
            description = "",
            section = overlays
        ) default boolean bryophytasStaffOverlay() { return true; }

        @ConfigItem(
            keyName = craws_bow + _overlay,
            name = "Craw's bow",
            description = "",
            section = overlays
        ) default boolean crawsBowOverlay() { return true; }

        @ConfigItem(
            keyName = burning_amulet + _overlay,
            name = "Burning amulet",
            description = "",
            section = overlays
        ) default boolean burningAmuletOverlay() { return true; }

        @ConfigItem(
            keyName = crystal_bow + _overlay,
            name = "Crystal bow",
            description = "",
            section = overlays
        ) default boolean crystalBowOverlay() { return true; }

        @ConfigItem(
            keyName = crystal_halberd + _overlay,
            name = "Crystal halberd",
            description = "",
            section = overlays
        ) default boolean crystalHalberdOverlay() { return true; }

        @ConfigItem(
            keyName = efaritays_aid + _overlay,
            name = "Efaritay's aid",
            description = "",
            section = overlays
        ) default boolean efaritaysAidOverlay() { return true; }

        @ConfigItem(
            keyName = enchanted_lyre + _overlay,
            name = "Enchanted Lyre",
            description = "",
            section = overlays
        ) default boolean enchantedLyreOverlay() { return true; }

        @ConfigItem(
            keyName = ibans_staff + _overlay,
            name = "Iban's staff",
            description = "",
            section = overlays
        ) default boolean ibansStaffOverlay() { return true; }

        @ConfigItem(
            keyName = infernal_axe + _overlay,
            name = "Infernal axe",
            description = "",
            section = overlays
        ) default boolean infernalAxeOverlay() { return true; }

        @ConfigItem(
            keyName = pharaohs_sceptre + _overlay,
            name = "Pharaoh's sceptre",
            description = "",
            section = overlays
        ) default boolean pharaohsSceptreOverlay() { return true; }

        @ConfigItem(
            keyName = quetzal_whistle + _overlay,
            name = "Quetzal whistle",
            description = "",
            section = overlays
        ) default boolean quetzalWhistleOverlay() { return true; }

        @ConfigItem(
            keyName = sanguinesti_staff + _overlay,
            name = "Sanguinesti staff",
            description = "",
            section = overlays
        ) default boolean sanguinestiStaffOverlay() { return true; }

        @ConfigItem(
            keyName = scythe_of_vitur + _overlay,
            name = "Scythe of Vitur",
            description = "",
            section = overlays
        ) default boolean scytheOfViturOverlay() { return true; }

        @ConfigItem(
            keyName = skull_sceptre + _overlay,
            name = "Skull sceptre",
            description = "",
            section = overlays
        ) default boolean skullSceptreOverlay() { return true; }

        @ConfigItem(
            keyName = slayer_staff_e + _overlay,
            name = "Slayer staff (e)",
            description = "",
            section = overlays
        ) default boolean slayerStaffEOverlay() { return true; }

        @ConfigItem(
            keyName = toxic_blowpipe + _overlay,
            name = "Toxic blowpipe",
            description = "",
            section = overlays
        ) default boolean toxicBlowpipeOverlay() { return true; }

        @ConfigItem(
            keyName = toxic_staff_of_the_dead + _overlay,
            name = "Toxic staff of the dead",
            description = "",
            section = overlays
        ) default boolean toxicStaffOfTheDeadOverlay() { return true; }

        @ConfigItem(
            keyName = trident_of_the_seas + _overlay,
            name = "Trident of the seas",
            description = "",
            section = overlays
        ) default boolean tridentOfTheSeasOverlay() { return true; }

        @ConfigItem(
            keyName = trident_of_the_seas_e + _overlay,
            name = "Trident of the seas (e)",
            description = "",
            section = overlays
        ) default boolean tridentOfTheSeasEOverlay() { return true; }

        @ConfigItem(
            keyName = trident_of_the_swamp + _overlay,
            name = "Trident of the swamp",
            description = "",
            section = overlays
        ) default boolean tridentOfTheSwampOverlay() { return true; }

        @ConfigItem(
            keyName = trident_of_the_swamp_e + _overlay,
            name = "Trident of the swamp (e)",
            description = "",
            section = overlays
        ) default boolean tridentOfTheSwampEOverlay() { return true; }

        @ConfigItem(
            keyName = warped_sceptre + _overlay,
            name = "Warped sceptre",
            description = "",
            section = overlays
        ) default boolean warpedSceptreOverlay() { return true; }

        @ConfigItem(
            keyName = webweaver_bow + _overlay,
            name = "Webweaver bow",
            description = "",
            section = overlays
        ) default boolean webweaverBowOverlay() { return true; }

        @ConfigItem(
            keyName = xerics_talisman + _overlay,
            name = "Xeric's talisman",
            description = "",
            section = overlays
        ) default boolean xericsTalismanOverlay() { return true; }

        @ConfigItem(
            keyName =  sailors_amulet + _overlay,
            name = "Sailors' Amulet",
            description = "",
            section = overlays
        ) default boolean sailorsAmuletOverlay() { return true; }

        @ConfigItem(
            keyName =  baskets + _overlay,
            name = "Baskets",
            description = "",
            section = overlays
        ) default boolean basketsOverlay() { return true; }

        @ConfigItem(
            keyName =  sacks + _overlay,
            name = "Sacks",
            description = "",
            section = overlays
        ) default boolean sacksOverlay() { return true; }

        @ConfigItem(
            keyName = alchemists_amulet + _overlay,
            name = "Alchemist's amulet",
            description = "",
            section = overlays
        ) default boolean alchemistsAmuletOverlay() { return true; }

        @ConfigItem(
            keyName = amulet_of_blood_fury + _overlay,
            name = "Amulet of blood fury",
            description = "",
            section = overlays
        ) default boolean amuletOfBloodFuryOverlay() { return true; }

        @ConfigItem(
            keyName = amulet_of_bounty + _overlay,
            name = "Amulet of bounty",
            description = "",
            section = overlays
        ) default boolean amuletOfBountyOverlay() { return true; }

        @ConfigItem(
            keyName = amulet_of_chemistry + _overlay,
            name = "Amulet of chemistry",
            description = "",
            section = overlays
        ) default boolean amuletOfChemistryOverlay() { return true; }

        @ConfigItem(
            keyName = amulet_of_glory + _overlay,
            name = "Amulet of glory",
            description = "",
            section = overlays
        ) default boolean amuletOfGloryOverlay() { return true; }

        @ConfigItem(
                keyName = eye_of_ayak + _overlay,
                name = "Eye of Ayak",
                description = "",
                section = overlays
        ) default boolean eyeOfAyakOverlay() { return true; }

    @ConfigSection(
        name = "Debug",
        description = "Values of charges for all items under the hood",
        position = 99,
        closedByDefault = true
    ) String debug = "debug";

        @ConfigItem(
            keyName = version,
            name = version,
            description = "Version of the plugin for update message",
            section = debug,
            position = -4
        ) default String getVersion() { return ""; }

        @ConfigItem(
            keyName = date,
            name = "Date",
            description = "Date to check for charges reset when logging in",
            section = debug,
            position = -3
        ) default String getResetDate() { return ""; }

        @ConfigItem(
            keyName = debug_ids,
            name = "Debug IDs",
            description = "Shows animation and graphics ids within ingame messages to add support for new items",
            section = debug,
            position = -2
        ) default boolean showDebugIds() { return false; }

        @ConfigItem(
            keyName = storage_bank,
            name = storage_bank,
            description = "All player bank items to check for daily resets",
            section = debug,
            position = 1
        ) default String getStorageBank() { return ""; }

        @ConfigItem(
            keyName = barrows_gear + "_ahrims_hood",
            name = barrows_gear + "_ahrims_hood",
            description = barrows_gear + "_ahrims_hood",
            section = debug
        ) default int ahrimsHoodCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = ring_of_pursuit,
            name = ring_of_pursuit,
            description = ring_of_pursuit,
            section = debug
        ) default int ringOfPursuitCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = arclight,
            name = arclight,
            description = arclight,
            section = debug
        ) default int getArclightCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = ash_sanctifier,
            name = ash_sanctifier,
            description = ash_sanctifier,
            section = debug
        ) default int getAshSanctifierCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = ash_sanctifier_status,
            name = ash_sanctifier_status,
            description = ash_sanctifier_status,
            section = debug
        ) default ItemActivity getAshSanctifierStatus() { return ItemActivity.ACTIVATED; }

        @ConfigItem(
            keyName = binding_necklace,
            name = binding_necklace,
            description = binding_necklace,
            section = debug
        ) default int getBindingNecklaceCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = bonecrusher,
            name = bonecrusher,
            description = bonecrusher,
            section = debug
        ) default int getBoneCrusherCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = efaritays_aid,
            name = efaritays_aid,
            description = efaritays_aid,
            section = debug
        ) default int getEfaritaysAidCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = bonecrusher_status,
            name = bonecrusher_status,
            description = bonecrusher_status,
            section = debug
        ) default ItemActivity getBoneCrusherStatus() { return ItemActivity.ACTIVATED; }

        @ConfigItem(
            keyName = kharedsts_memoirs,
            name = kharedsts_memoirs,
            description = kharedsts_memoirs,
            section = debug
        ) default int getKharedstsMemoirsCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = bottomless_compost_bucket + _storage,
            name = bottomless_compost_bucket + _storage,
            description = bottomless_compost_bucket + _storage,
            section = debug
        ) default String getBottomlessCompostBucketStorage() { return ""; }

        @ConfigItem(
            keyName = master_scroll_book + _storage,
            name = master_scroll_book + _storage,
            description = master_scroll_book + _storage,
            section = debug
        ) default String masterScrollBookStorage() { return ""; }

        @ConfigItem(
            keyName = bracelet_of_slaughter,
            name = bracelet_of_slaughter,
            description = bracelet_of_slaughter,
            section = debug
        ) default int getBraceletOfSlaughterCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = bryophytas_staff,
            name = bryophytas_staff,
            description = bryophytas_staff,
            section = debug
        ) default int getBryophytasStaffCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = celestial_ring,
            name = celestial_ring,
            description = celestial_ring,
            section = debug
        ) default int getCelestialRingCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = chronicle,
            name = chronicle,
            description = chronicle,
            section = debug
        ) default int getChronicleCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = crystal_shield,
            name = crystal_shield,
            description = crystal_shield,
            section = debug
        ) default int getCrystalShieldCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = crystal_bow,
            name = crystal_bow,
            description = crystal_bow,
            section = debug
        ) default int getCrystalBowCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = expeditious_bracelet,
            name = expeditious_bracelet,
            description = expeditious_bracelet,
            section = debug
        ) default int getBraceletOfExpeditiousCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = falador_shield,
            name = falador_shield,
            description = falador_shield,
            section = debug
        ) default int getFaladorShieldCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = fish_barrel + _storage,
            name = fish_barrel + _storage,
            description = fish_barrel + _storage,
            section = debug
        ) default String getFishBarrelStorage() { return ""; }

        @ConfigItem(
            keyName = flamtaer_bag + _storage,
            name = flamtaer_bag + _storage,
            description = flamtaer_bag + _storage,
            section = debug
        ) default String getFlamtaerBagStorage() { return ""; }

        @ConfigItem(
            keyName = gricollers_can,
            name = gricollers_can,
            description = gricollers_can,
            section = debug
        ) default int getGricollersCanCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = ibans_staff,
            name = ibans_staff,
            description = ibans_staff,
            section = debug
        ) default int getIbansStaffCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = moons_gear + "_eclipse_chestplate",
            name = moons_gear + "_eclipse_chestplate",
            description = moons_gear + "_eclipse_chestplate",
            section = debug
        ) default int getEclipseMoonChestplateCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = pharaohs_sceptre,
            name = pharaohs_sceptre,
            description = pharaohs_sceptre,
            section = debug
        ) default int getPharaohsSceptreCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = reagent_pouch + _storage,
            name = reagent_pouch + _storage,
            description = reagent_pouch + _storage,
            section = debug
        ) default String getReagentPouchStorage() { return ""; }

        @ConfigItem(
            keyName = ring_of_forging,
            name = ring_of_forging,
            description = ring_of_forging,
            section = debug
        ) default int getRingOfForgingCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = ring_of_suffering,
            name = ring_of_suffering,
            description = ring_of_suffering,
            section = debug
        ) default int getRingOfSufferingCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = ring_of_suffering_status,
            name = ring_of_suffering_status,
            description = ring_of_suffering_status,
            section = debug
        ) default ItemActivity getRingOfSufferingStatus() { return ItemActivity.ACTIVATED; }

        @ConfigItem(
            keyName = sanguinesti_staff,
            name = sanguinesti_staff,
            description = sanguinesti_staff,
            section = debug
        ) default int getSanguinestiStaffCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = scythe_of_vitur,
            name = scythe_of_vitur,
            description = scythe_of_vitur,
            section = debug
        ) default int getScytheOfViturCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = skull_sceptre,
            name = skull_sceptre,
            description = skull_sceptre,
            section = debug
        ) default int getSkullSceptreCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = soul_bearer,
            name = soul_bearer,
            description = soul_bearer,
            section = debug
        ) default int getSoulBearerCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = trident_of_the_seas,
            name = trident_of_the_seas,
            description = trident_of_the_seas,
            section = debug
        ) default int getTridentOfTheSeasCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = xerics_talisman,
            name = xerics_talisman,
            description = xerics_talisman,
            section = debug
        ) default int getXericsTalismanCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = sailors_amulet,
            name = sailors_amulet,
            description = sailors_amulet,
            section = debug
        ) default int getSailorsAmuletCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = dragonfire_shield,
            name = dragonfire_shield,
            description = dragonfire_shield,
            section = debug
        ) default int getDragonfireShieldCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = camulet,
            name = camulet,
            description = camulet,
            section = debug
        ) default int getCamuletCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = circlet_of_water,
            name = circlet_of_water,
            description = circlet_of_water,
            section = debug
        ) default int getCircletOfWaterCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = chugging_barrel + _storage,
            name = chugging_barrel + _storage,
            description = chugging_barrel + _storage,
            section = debug
        ) default String getChuggingBarrelStorage() { return ""; }

        @ConfigItem(
            keyName = teleport_crystal,
            name = teleport_crystal,
            description = teleport_crystal,
            section = debug
        ) default int getTeleportCrystalCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = bracelet_of_clay,
            name = bracelet_of_clay,
            description = bracelet_of_clay,
            section = debug
        ) default int getBraceletOfClayCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = coffin,
            name = coffin,
            description = coffin,
            section = debug
        ) default int getCoffinCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = huntsmans_kit + _storage,
            name = huntsmans_kit + _storage,
            description = huntsmans_kit + _storage,
            section = debug
        ) default String getHuntsmansKitStorage() { return ""; }

        @ConfigItem(
            keyName = log_basket + _storage,
            name = log_basket + _storage,
            description = log_basket + _storage,
            section = debug
        ) default String getLogBasketStorage() { return ""; }

        @ConfigItem(
            keyName = forestry_basket + _storage,
            name = forestry_basket + _storage,
            description = forestry_basket + _storage,
            section = debug
        ) default String getForestryBasketStorage() { return ""; }

        @ConfigItem(
            keyName = forestry_kit + _storage,
            name = forestry_kit + _storage,
            description = forestry_kit + _storage,
            section = debug
        ) default String getForestryKitStorage() { return ""; }

        @ConfigItem(
            keyName = ardougne_cloak,
            name = ardougne_cloak,
            description = ardougne_cloak,
            section = debug
        ) default int getArdougneCloakCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = magic_cape,
            name = magic_cape,
            description = magic_cape,
            section = debug
        ) default int getMagicCapeCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = meat_pouch + _storage,
            name = meat_pouch + _storage,
            description = meat_pouch + _storage,
            section = debug
        ) default String getMeatPouchStorageCharges() { return ""; }

        @ConfigItem(
            keyName = gem_bag + _storage,
            name = gem_bag + _storage,
            description = gem_bag + _storage,
            section = debug
        ) default String getGemBagStorageCharges() { return ""; }

        @ConfigItem(
            keyName = seed_box,
            name = seed_box,
            description = seed_box,
            section = debug
        ) default int getSeedBoxCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = seed_box + _storage,
            name = seed_box + _storage,
            description = seed_box + _storage,
            section = debug
        ) default String getSeedBoxStorage() { return ""; }

        @ConfigItem(
            keyName = crystal_helm,
            name = crystal_helm,
            description = crystal_helm,
            section = debug
        ) default int getCrystalHelmCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = crystal_body,
            name = crystal_body,
            description = crystal_body,
            section = debug
        ) default int getCrystalBodyCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = crystal_legs,
            name = crystal_legs,
            description = crystal_legs,
            section = debug
        ) default int getCrystalLegsCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = crystal_halberd,
            name = crystal_halberd,
            description = crystal_halberd,
            section = debug
        ) default int getCrystalHalberdCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = ring_of_shadows,
            name = ring_of_shadows,
            description = ring_of_shadows,
            section = debug
        ) default int getRingOfShadowsCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = coal_bag,
            name = coal_bag,
            description = coal_bag,
            section = debug
        ) default int getCoalBagCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = colossal_pouch + _storage,
            name = colossal_pouch + _storage,
            description = colossal_pouch + _storage,
            section = debug
        ) default String getColossalPouchStorage() { return ""; }

        @ConfigItem(
            keyName = colossal_pouch_decay_count,
            name = colossal_pouch_decay_count,
            description = "Colossal pouch decay count",
            section = debug
        ) default int getColossalPouchDecayCount() { return 0; };

        @ConfigItem(
            keyName = herb_sack + _storage,
            name = herb_sack + _storage,
            description = herb_sack + _storage,
            section = debug
        ) default String getHerbSackStorage() { return ""; }

        @ConfigItem(
            keyName = escape_crystal_status,
            name = escape_crystal_status,
            description = escape_crystal_status,
            section = debug
        ) default ItemActivity getEscapeCrystalStatus() { return ItemActivity.DEACTIVATED; }

        @ConfigItem(
            keyName = escape_crystal_inactivity_period,
            name = escape_crystal_inactivity_period,
            description = escape_crystal_inactivity_period,
            section = debug
        ) default int getEscapeCrystalInactivityPeriod() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = strange_old_lockpick,
            name = strange_old_lockpick,
            description = strange_old_lockpick,
            section = debug
        ) default int getStrangeOldLockCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = desert_amulet,
            name = desert_amulet,
            description = desert_amulet,
            section = debug
        ) default int getDesertAmuletCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = tome_of_fire,
            name = tome_of_fire,
            description = tome_of_fire,
            section = debug
        ) default int getTomeOfFireCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = dodgy_necklace,
            name = dodgy_necklace,
            description = dodgy_necklace,
            section = debug
        ) default int getDodgyNecklaceCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = kandarin_headgear,
            name = kandarin_headgear,
            description = kandarin_headgear,
            section = debug
        ) default int getKandarinHeadgearCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = fremennik_sea_boots,
            name = fremennik_sea_boots,
            description = fremennik_sea_boots,
            section = debug
        ) default int getFremennikSeaBootsCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = fur_pouch + _storage,
            name = fur_pouch + _storage,
            description = fur_pouch + _storage,
            section = debug
        ) default String getFurPouchStorageCharges() { return ""; }

        @ConfigItem(
            keyName = jar_generator,
            name = jar_generator,
            description = jar_generator,
            section = debug
        ) default int getJarGeneratorCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = explorers_ring + _storage,
            name = explorers_ring + _storage,
            description = explorers_ring + _storage,
            section = debug
        ) default String getExplorersRingCharges() { return ""; }

        @ConfigItem(
            keyName = enchanted_lyre,
            name = enchanted_lyre,
            description = enchanted_lyre,
            section = debug
        ) default int getEnchantedLyreCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = ring_of_the_elements,
            name = ring_of_the_elements,
            description = ring_of_the_elements,
            section = debug
        ) default int getRingOfElementsCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = ring_of_endurance,
            name = ring_of_endurance,
            description = ring_of_endurance,
            section = debug
        ) default int getRingOfEnduranceCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = plank_sack,
            name = plank_sack,
            description = plank_sack,
            section = debug
        ) default int getPlankSackCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = slayer_staff_e,
            name = slayer_staff_e,
            description = slayer_staff_e,
            section = debug
        ) default int getSlayerStaffECharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = warped_sceptre,
            name = warped_sceptre,
            description = warped_sceptre,
            section = debug
        ) default int getWarpedSceptreCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = crystal_saw,
            name = crystal_saw,
            description = crystal_saw,
            section = debug
        ) default int getCrystalSawCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = quetzal_whistle,
            name = quetzal_whistle,
            description = quetzal_whistle,
            section = debug
        ) default int getQuetzalWhistleCharges() { return ChargeId.UNKNOWN; }

        @ConfigItem(
            keyName = tackle_box + _storage,
            name = tackle_box + _storage,
            description = tackle_box + _storage,
            section = debug
        ) default String getTackleBoxStorage() { return ""; }

        @ConfigItem(
            keyName = amulet_of_chemistry,
            name = amulet_of_chemistry,
            description = amulet_of_chemistry,
            section = debug
        ) default int getAmuletOfChemistryCharges() { return ChargeId.UNKNOWN; }
}
