package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class U_SeedBox extends ChargedItemWithStorage {
    public U_SeedBox(Provider provider) {
        super(TicTac7xChargesImprovedConfig.seed_box, ItemID.SEED_BOX, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SEED_BOX),
            new TriggerItem(ItemID.SEED_BOX_OPEN),
        };

        this.storage.storableItems(
            // Allotment seeds
            new StorableItem(ItemID.POTATO_SEED).checkName("potato"),
            new StorableItem(ItemID.ONION_SEED).checkName("onion"),
            new StorableItem(ItemID.CABBAGE_SEED).checkName("cabbage"),
            new StorableItem(ItemID.TOMATO_SEED).checkName("tomato"),
            new StorableItem(ItemID.SWEETCORN_SEED).checkName("sweetcorn"),
            new StorableItem(ItemID.STRAWBERRY_SEED).checkName("strawberry"),
            new StorableItem(ItemID.WATERMELON_SEED).checkName("watermelon"),
            new StorableItem(ItemID.SNAPE_GRASS_SEED).checkName("snape"),

            // Flower seeds
            new StorableItem(ItemID.MARIGOLD_SEED).checkName("marigold"),
            new StorableItem(ItemID.ROSEMARY_SEED).checkName("rosemary"),
            new StorableItem(ItemID.NASTURTIUM_SEED).checkName("nasturtium"),
            new StorableItem(ItemID.WOAD_SEED).checkName("woad"),
            new StorableItem(ItemID.LIMPWURT_SEED).checkName("limpwurt"),
            new StorableItem(ItemID.WHITE_LILY_SEED).checkName("white lily"),

            // Herb seeds
            new StorableItem(ItemID.GUAM_SEED).checkName("guam"),
            new StorableItem(ItemID.MARRENTILL_SEED).checkName("marrentill"),
            new StorableItem(ItemID.TARROMIN_SEED).checkName("tarromin"),
            new StorableItem(ItemID.HARRALANDER_SEED).checkName("harralander"),
            new StorableItem(ItemID.VILLAGE_RARE_TUBER).checkName("gout"),
            new StorableItem(ItemID.RANARR_SEED).checkName("ranarr"),
            new StorableItem(ItemID.TOADFLAX_SEED).checkName("toadflax"),
            new StorableItem(ItemID.IRIT_SEED).checkName("irit"),
            new StorableItem(ItemID.AVANTOE_SEED).checkName("avantoe"),
            new StorableItem(ItemID.KWUARM_SEED).checkName("kwuarm"),
            new StorableItem(ItemID.SNAPDRAGON_SEED).checkName("snapdragon"),
            new StorableItem(ItemID.HUASCA_SEED).checkName("huasca"),
            new StorableItem(ItemID.CADANTINE_SEED).checkName("cadantine"),
            new StorableItem(ItemID.LANTADYME_SEED).checkName("lantadyme"),
            new StorableItem(ItemID.DWARF_WEED_SEED).checkName("dwarf weed"),
            new StorableItem(ItemID.TORSTOL_SEED).checkName("torstol"),

            // Hops seeds
            new StorableItem(ItemID.BARLEY_SEED).checkName("barley"),
            new StorableItem(ItemID.HAMMERSTONE_HOP_SEED).checkName("hammerstone"),
            new StorableItem(ItemID.ASGARNIAN_HOP_SEED).checkName("asgarnian"),
            new StorableItem(ItemID.JUTE_SEED).checkName("jute"),
            new StorableItem(ItemID.YANILLIAN_HOP_SEED).checkName("vanillian"),
            new StorableItem(ItemID.FLAX_SEED).checkName("flax"),
            new StorableItem(ItemID.KRANDORIAN_HOP_SEED).checkName("krandorian"),
            new StorableItem(ItemID.WILDBLOOD_HOP_SEED).checkName("wildblood"),
            new StorableItem(ItemID.HEMP_SEED).checkName("hemp"),
            new StorableItem(ItemID.COTTON_SEED).checkName("cotton"),

            // Bush seeds
            new StorableItem(ItemID.REDBERRY_BUSH_SEED).checkName("redberry"),
            new StorableItem(ItemID.CADAVABERRY_BUSH_SEED).checkName("cadavaberry"),
            new StorableItem(ItemID.DWELLBERRY_BUSH_SEED).checkName("dwellberry"),
            new StorableItem(ItemID.JANGERBERRY_BUSH_SEED).checkName("jangerberry"),
            new StorableItem(ItemID.WHITEBERRY_BUSH_SEED).checkName("whiteberry"),
            new StorableItem(ItemID.POISONIVY_BUSH_SEED).checkName("poison ivy"),

            // Tree seeds
            new StorableItem(ItemID.ACORN).checkName("acorn"),
            new StorableItem(ItemID.WILLOW_SEED).checkName("willow"),
            new StorableItem(ItemID.MAPLE_SEED).checkName("maple"),
            new StorableItem(ItemID.YEW_SEED).checkName("yew"),
            new StorableItem(ItemID.MAGIC_TREE_SEED).checkName("magic"),

            // Fruit tree seeds
            new StorableItem(ItemID.APPLE_TREE_SEED).checkName("apple"),
            new StorableItem(ItemID.BANANA_TREE_SEED).checkName("banana"),
            new StorableItem(ItemID.ORANGE_TREE_SEED).checkName("orange"),
            new StorableItem(ItemID.CURRY_TREE_SEED).checkName("curry"),
            new StorableItem(ItemID.PINEAPPLE_TREE_SEED).checkName("pineapple"),
            new StorableItem(ItemID.PAPAYA_TREE_SEED).checkName("papaya"),
            new StorableItem(ItemID.PALM_TREE_SEED).checkName("palm"),
            new StorableItem(ItemID.DRAGONFRUIT_TREE_SEED).checkName("dragonfruit"),

            // Special seeds
            new StorableItem(ItemID.SEAWEED_SEED).checkName("seaweed spore"),
            new StorableItem(ItemID.GRAPE_SEED).checkName("grape"),
            new StorableItem(ItemID.MUSHROOM_SEED).checkName("mushroom spore"),
            new StorableItem(ItemID.BELLADONNA_SEED).checkName("belladonna"),
            new StorableItem(ItemID.HESPORI_SEED).checkName("hespori"),

            // Coral frags
            new StorableItem(ItemID.CORAL_ELKHORN_FRAG).checkName("elkhorn"),
            new StorableItem(ItemID.CORAL_PILLAR_FRAG).checkName("pillar"),
            new StorableItem(ItemID.CORAL_UMBRAL_FRAG).checkName("umbral"),

            // Anima seeds
            new StorableItem(ItemID.KRONOS_SEED).checkName("kronos"),
            new StorableItem(ItemID.IASOR_SEED).checkName("iasor"),
            new StorableItem(ItemID.ATTAS_SEED).checkName("attas"),

            // Hardwood tree seeds
            new StorableItem(ItemID.TEAK_SEED).checkName("teak"),
            new StorableItem(ItemID.MAHOGANY_SEED).checkName("mahogany"),
            new StorableItem(ItemID.CAMPHOR_SEED).checkName("camphor"),
            new StorableItem(ItemID.IRONWOOD_SEED).checkName("ironwood"),
            new StorableItem(ItemID.ROSEWOOD_SEED).checkName("rosewood"),

            // Special tree seeds
            new StorableItem(ItemID.CALQUAT_TREE_SEED).checkName("calquat"),
            new StorableItem(ItemID.CRYSTAL_TREE_SEED).checkName("crystal acorn"),
            new StorableItem(ItemID.SPIRIT_TREE_SEED).checkName("spirit"),
            new StorableItem(ItemID.CELASTRUS_TREE_SEED).checkName("celastrus"),
            new StorableItem(ItemID.REDWOOD_TREE_SEED).checkName("redwood"),

            // Cacti seeds
            new StorableItem(ItemID.CACTUS_SEED).checkName("cactus"),
            new StorableItem(ItemID.POTATO_CACTUS_SEED).checkName("potato cactus")
        );

        this.triggers.addAll(List.of(
            // Check or empty.
            new OnChatMessage("(The|Your) seed box is( now| already)? empty.").emptyStorage(),

            // Empty into inventory.
            new OnChatMessage("Emptied (?<quantity>.+) x (?<seed>.+)( seed| frag)? to your inventory.").matcherConsumer(m -> {
                storage.remove(getStorageItemFromName(m.group("seed"), Integer.parseInt(m.group("quantity"))));
            }),

            // Store.
            new OnChatMessage("Stored (?<quantity>.+) x (?<seed>.+)( seed| frag)? in your seed box.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("seed"), Integer.parseInt(m.group("quantity"))));
            }),

            // Pickup.
            new OnChatMessage("You put (?<quantity>.+) x (?<seed>.+)( seed| frag)? straight into your open seed box.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("seed"), Integer.parseInt(m.group("quantity"))));
            }),

            // Pickpocketing.
            new OnChatMessage("You put the stolen (?<seed>.+)( seed| frag)? into your seed box.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("seed"), 1));
            }),
            new OnChatMessage("The following stolen loot gets added to your seed box: (?<seed>.+)( seed| frag)? x (?<quantity>.+).").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("seed"), Integer.parseInt(m.group("quantity"))));
            }),

            // Check.
            new OnItemContainerChanged(InventoryID.SEED_BOX).updateStorage(),

            // Planting.
            new OnChatMessage("An? (?<seed>.+) seed was taken from your seed box.").matcherConsumer(m -> {
                storage.remove(getStorageItemFromName(m.group("seed"), 1));
            }),
            new OnChatMessage("(?<quantity>.+) (?<seed>.+) seeds were taken from your seed box.").matcherConsumer(m -> {
                storage.remove(getStorageItemFromName(m.group("seed"), Integer.parseInt(m.group("quantity"))));
            }),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }
}
