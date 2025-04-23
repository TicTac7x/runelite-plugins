package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;

public class U_SeedBox extends ChargedItemWithStorage {
    public U_SeedBox(
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ChatMessageManager chatMessageManager,
        final Notifier notifier,
        final TicTac7xChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(TicTac7xChargesImprovedConfig.seed_box, ItemId.SEED_BOX, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SEED_BOX),
            new TriggerItem(ItemId.SEED_BOX_OPEN),
        };

        this.storage.storableItems(
            new StorableItem(ItemId.POTATO_SEED).checkName("potato"),
            new StorableItem(ItemId.ONION_SEED).checkName("onion"),
            new StorableItem(ItemId.CABBAGE_SEED).checkName("cabbage"),
            new StorableItem(ItemId.TOMATO_SEED).checkName("tomato"),
            new StorableItem(ItemId.SWEETCORN_SEED).checkName("sweetcorn"),
            new StorableItem(ItemId.STRAWBERRY_SEED).checkName("strawberry"),
            new StorableItem(ItemId.WATERMELON_SEED).checkName("watermelon"),
            new StorableItem(ItemId.SNAPE_GRASS_SEED).checkName("snape"),
            new StorableItem(ItemId.BARLEY_SEED).checkName("barley"),
            new StorableItem(ItemId.HAMMERSTONE_SEED).checkName("hammerstone"),
            new StorableItem(ItemId.ASGARNIAN_SEED).checkName("asgarnian"),
            new StorableItem(ItemId.JUTE_SEED).checkName("jute"),
            new StorableItem(ItemId.YANILLIAN_SEED).checkName("vanillian"),
            new StorableItem(ItemId.KRANDORIAN_SEED).checkName("krandorian"),
            new StorableItem(ItemId.WILDBLOOD_SEED).checkName("wildblood"),
            new StorableItem(ItemId.ACORN).checkName("acorn"),
            new StorableItem(ItemId.WILLOW_SEED).checkName("willow"),
            new StorableItem(ItemId.MAPLE_SEED).checkName("maple"),
            new StorableItem(ItemId.YEW_SEED).checkName("yew"),
            new StorableItem(ItemId.MAGIC_SEED).checkName("magic"),
            new StorableItem(ItemId.REDWOOD_TREE_SEED).checkName("redwood"),
            new StorableItem(ItemId.PINEAPPLE_SEED).checkName("pineapple"),
            new StorableItem(ItemId.APPLE_TREE_SEED).checkName("apple"),
            new StorableItem(ItemId.BANANA_TREE_SEED).checkName("banana"),
            new StorableItem(ItemId.ORANGE_TREE_SEED).checkName("orange"),
            new StorableItem(ItemId.CURRY_TREE_SEED).checkName("curry"),
            new StorableItem(ItemId.PAPAYA_TREE_SEED).checkName("papaya"),
            new StorableItem(ItemId.PALM_TREE_SEED).checkName("palm"),
            new StorableItem(ItemId.DRAGONFRUIT_TREE_SEED).checkName("dragonfruit"),
            new StorableItem(ItemId.REDBERRY_SEED).checkName("redberry"),
            new StorableItem(ItemId.CADAVABERRY_SEED).checkName("cadavaberry"),
            new StorableItem(ItemId.DWELLBERRY_SEED).checkName("dwellberry"),
            new StorableItem(ItemId.JANGERBERRY_SEED).checkName("jangerberry"),
            new StorableItem(ItemId.WHITEBERRY_SEED).checkName("whiteberry"),
            new StorableItem(ItemId.POISON_IVY_SEED).checkName("poison ivy"),
            new StorableItem(ItemId.MARIGOLD_SEED).checkName("marigold"),
            new StorableItem(ItemId.ROSEMARY_SEED).checkName("rosemary"),
            new StorableItem(ItemId.NASTURTIUM_SEED).checkName("nasturtium"),
            new StorableItem(ItemId.WOAD_SEED).checkName("woad"),
            new StorableItem(ItemId.LIMPWURT_SEED).checkName("limpwurt"),
            new StorableItem(ItemId.WHITE_LILY_SEED).checkName("white lily"),
            new StorableItem(ItemId.GUAM_SEED).checkName("guam"),
            new StorableItem(ItemId.MARRENTILL_SEED).checkName("marrentill"),
            new StorableItem(ItemId.TARROMIN_SEED).checkName("tarromin"),
            new StorableItem(ItemId.HARRALANDER_SEED).checkName("harralander"),
            new StorableItem(ItemId.RANARR_SEED).checkName("ranarr"),
            new StorableItem(ItemId.TOADFLAX_SEED).checkName("toadflax"),
            new StorableItem(ItemId.IRIT_SEED).checkName("irit"),
            new StorableItem(ItemId.AVANTOE_SEED).checkName("avantoe"),
            new StorableItem(ItemId.HUASCA_SEED).checkName("huasca"),
            new StorableItem(ItemId.KWUARM_SEED).checkName("kwuarm"),
            new StorableItem(ItemId.SNAPDRAGON_SEED).checkName("snapdragon"),
            new StorableItem(ItemId.CADANTINE_SEED).checkName("cadantine"),
            new StorableItem(ItemId.LANTADYME_SEED).checkName("lantadyme"),
            new StorableItem(ItemId.DWARF_WEED_SEED).checkName("dwarf weed"),
            new StorableItem(ItemId.TORSTOL_SEED).checkName("torstol"),
            new StorableItem(ItemId.SEAWEED_SPORE).checkName("seaweed spore"),
            new StorableItem(ItemId.TEAK_SEED).checkName("teak"),
            new StorableItem(ItemId.GRAPE_SEED).checkName("grape"),
            new StorableItem(ItemId.MUSHROOM_SPORE).checkName("mushroom spore"),
            new StorableItem(ItemId.MAHOGANY_SEED).checkName("mahogany"),
            new StorableItem(ItemId.POTATO_CACTUS_SEED).checkName("potato cactus"),
            new StorableItem(ItemId.CACTUS_SEED).checkName("cactus"),
            new StorableItem(ItemId.BELLADONNA_SEED).checkName("belladonna"),
            new StorableItem(ItemId.HESPORI_SEED).checkName("hespori"),
            new StorableItem(ItemId.CALQUAT_TREE_SEED).checkName("calquat"),
            new StorableItem(ItemId.CRYSTAL_ACORN).checkName("crystal acorn"),
            new StorableItem(ItemId.KRONOS_SEED).checkName("kronos"),
            new StorableItem(ItemId.IASOR_SEED).checkName("iasor"),
            new StorableItem(ItemId.ATTAS_SEED).checkName("attas"),
            new StorableItem(ItemId.SPIRIT_SEED).checkName("spirit"),
            new StorableItem(ItemId.CELASTRUS_SEED).checkName("celastrus")
        );

        this.triggers = new TriggerBase[] {
            // Check or empty.
            new OnChatMessage("(The|Your) seed box is( now| already)? empty.").emptyStorage(),

            // Empty into inventory.
            new OnChatMessage("Emptied (?<quantity>.+) x (?<seed>.+)( seed)? to your inventory.").matcherConsumer(m -> {
                storage.remove(getStorageItemFromName(m.group("seed"), Integer.parseInt(m.group("quantity"))));
            }),

            // Store.
            new OnChatMessage("Stored (?<quantity>.+) x (?<seed>.+)( seed)? in your seed box.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("seed"), Integer.parseInt(m.group("quantity"))));
            }),

            // Pickup.
            new OnChatMessage("You put (?<quantity>.+) x (?<seed>.+)( seed)? straight into your open seed box.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("seed"), Integer.parseInt(m.group("quantity"))));
            }),

            // Check.
            new OnItemContainerChanged(ItemContainerId.SEED_BOX).updateStorage(),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        };
    }

    private int getSeedIdFromName(final String seed) {
        switch (seed.toLowerCase().replace(" seed", "")) {
            // Allotments.
            case "potato":
                return ItemId.POTATO_SEED;
            case "onion":
                return ItemId.ONION_SEED;
            case "cabbage":
                return ItemId.CABBAGE_SEED;
            case "tomato":
                return ItemId.TOMATO_SEED;
            case "sweetcorn":
                return ItemId.SWEETCORN_SEED;
            case "strawberry":
                return ItemId.STRAWBERRY_SEED;
            case "watermelon":
                return ItemId.WATERMELON_SEED;
            case "snape grass":
                return ItemId.SNAPE_GRASS_SEED;

            // Hops.
            case "barley":
                return ItemId.BARLEY_SEED;
            case "hammerstone":
                return ItemId.HAMMERSTONE_SEED;
            case "asgarnian":
                return ItemId.ASGARNIAN_SEED;
            case "jute":
                return ItemId.JUTE_SEED;
            case "yanillian":
                return ItemId.YANILLIAN_SEED;
            case "krandorian":
                return ItemId.KRANDORIAN_SEED;
            case "wildblood":
                return ItemId.WILDBLOOD_SEED;

            // Trees.
            case "acorn":
                return ItemId.ACORN;
            case "willow":
                return ItemId.WILLOW_SEED;
            case "maple":
                return ItemId.MAPLE_SEED;
            case "yew":
                return ItemId.YEW_SEED;
            case "magic":
                return ItemId.MAGIC_SEED;
            case "redwood tree":
                return ItemId.REDWOOD_TREE_SEED;

            // Fruit trees.
            case "apple tree":
                return ItemId.APPLE_TREE_SEED;
            case "banana tree":
                return ItemId.BANANA_TREE_SEED;
            case "orange tree":
                return ItemId.ORANGE_TREE_SEED;
            case "curry tree":
                return ItemId.CURRY_TREE_SEED;
            case "pineapple":
                return ItemId.PINEAPPLE_SEED;
            case "papaya tree":
                return ItemId.PAPAYA_TREE_SEED;
            case "palm tree":
                return ItemId.PALM_TREE_SEED;
            case "dragonfruit tree":
                return ItemId.DRAGONFRUIT_TREE_SEED;

            // Bushes.
            case "redberry":
                return ItemId.REDBERRY_SEED;
            case "cadavaberry":
                return ItemId.CADAVABERRY_SEED;
            case "dwellberry":
                return ItemId.DWELLBERRY_SEED;
            case "jangerberry":
                return ItemId.JANGERBERRY_SEED;
            case "whiteberry":
                return ItemId.WHITEBERRY_SEED;
            case "poison ivy":
                return ItemId.POISON_IVY_SEED;

            // Flowers.
            case "marigold":
                return ItemId.MARIGOLD_SEED;
            case "rosemary":
                return ItemId.ROSEMARY_SEED;
            case "nasturtium":
                return ItemId.NASTURTIUM_SEED;
            case "woad":
                return ItemId.WOAD_SEED;
            case "limpwurt":
                return ItemId.LIMPWURT_SEED;
            case "white lily":
                return ItemId.WHITE_LILY_SEED;

            // Herbs.
            case "guam":
                return ItemId.GUAM_SEED;
            case "marrentill":
                return ItemId.MARRENTILL_SEED;
            case "tarromin":
                return ItemId.TARROMIN_SEED;
            case "harralander":
                return ItemId.HARRALANDER_SEED;
            case "ranarr":
                return ItemId.RANARR_SEED;
            case "toadflax":
                return ItemId.TOADFLAX_SEED;
            case "irit":
                return ItemId.IRIT_SEED;
            case "avantoe":
                return ItemId.AVANTOE_SEED;
            case "kwuarm":
                return ItemId.KWUARM_SEED;
            case "snapdragon":
                return ItemId.SNAPDRAGON_SEED;
            case "cadantine":
                return ItemId.CADANTINE_SEED;
            case "lantadyme":
                return ItemId.LANTADYME_SEED;
            case "dwarf weed":
                return ItemId.DWARF_WEED_SEED;
            case "torstol":
                return ItemId.TORSTOL_SEED;

            // Special seeds.
            case "seaweed spore":
                return ItemId.SEAWEED_SPORE;
            case "teak":
                return ItemId.TEAK_SEED;
            case "grape":
                return ItemId.GRAPE_SEED;
            case "mushroom spore":
                return ItemId.MUSHROOM_SPORE;
            case "mahogany":
                return ItemId.MAHOGANY_SEED;
            case "cactus":
                return ItemId.CACTUS_SEED;
            case "belladonna":
                return ItemId.BELLADONNA_SEED;
            case "potato cactus":
                return ItemId.POTATO_CACTUS_SEED;
            case "hespori":
                return ItemId.HESPORI_SEED;
            case "calquat tree":
                return ItemId.CALQUAT_TREE_SEED;
            case "crystal acorn":
                return ItemId.CRYSTAL_ACORN;
            case "kronos":
                return ItemId.KRONOS_SEED;
            case "iasor":
                return ItemId.IASOR_SEED;
            case "attas":
                return ItemId.ATTAS_SEED;
            case "spirit":
                return ItemId.SPIRIT_SEED;
            case "celastrus":
                return ItemId.CELASTRUS_SEED;
        }

        return Charges.UNKNOWN;
    }
}
