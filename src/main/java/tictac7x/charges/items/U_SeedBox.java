package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Store;

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
        super(TicTac7xChargesImprovedConfig.seed_box, ItemID.SEED_BOX, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SEED_BOX),
            new TriggerItem(ItemID.OPEN_SEED_BOX),
        };
        this.triggers = new TriggerBase[] {
            // Check or empty.
            new OnChatMessage("(The|Your) seed box is( now| already)? empty.").emptyStorage(),

            // Empty into inventory.
            new OnChatMessage("Emptied (?<quantity>.+) x (?<seed>.+)( seed)? to your inventory.").matcherConsumer(m -> {
                final int seed = getSeedIdFromName(m.group("seed"));
                final int quantity = Integer.parseInt(m.group("quantity"));
                storage.remove(seed, quantity);
            }),

            // Store.
            new OnChatMessage("Stored (?<quantity>.+) x (?<seed>.+)( seed)? in your seed box.").matcherConsumer(m -> {
                final int seed = getSeedIdFromName(m.group("seed"));
                final int quantity = Integer.parseInt(m.group("quantity"));
                storage.add(seed, quantity);
            }),

            // Pickup.
            new OnChatMessage("You put (?<quantity>.+) x (?<seed>.+)( seed)? straight into your open seed box.").matcherConsumer(m -> {
                final int seed = getSeedIdFromName(m.group("seed"));
                final int quantity = Integer.parseInt(m.group("quantity"));
                storage.put(seed, quantity);
            }),

            // Check first message.
            new OnChatMessage("The seed box contains:").emptyStorage(),

            // Check.
            new OnChatMessage("^(?<quantity>.+) x (?<seed>.+)( seed)?.").matcherConsumer(m -> {
                final int seed = getSeedIdFromName(m.group("seed"));
                final int quantity = Integer.parseInt(m.group("quantity"));
                storage.put(seed, quantity);
            }),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        };
    }

    private int getSeedIdFromName(final String seed) {
        switch (seed.toLowerCase().replace(" seed", "")) {
            // Allotments.
            case "potato":
                return ItemID.POTATO_SEED;
            case "onion":
                return ItemID.ONION_SEED;
            case "cabbage":
                return ItemID.CABBAGE_SEED;
            case "tomato":
                return ItemID.TOMATO_SEED;
            case "sweetcorn":
                return ItemID.SWEETCORN_SEED;
            case "strawberry":
                return ItemID.STRAWBERRY_SEED;
            case "watermelon":
                return ItemID.WATERMELON_SEED;
            case "snape grass":
                return ItemID.SNAPE_GRASS_SEED;

            // Hops.
            case "barley":
                return ItemID.BARLEY_SEED;
            case "hammerstone":
                return ItemID.HAMMERSTONE_SEED;
            case "asgarnian":
                return ItemID.ASGARNIAN_SEED;
            case "jute":
                return ItemID.JUTE_SEED;
            case "yanillian":
                return ItemID.YANILLIAN_SEED;
            case "krandorian":
                return ItemID.KRANDORIAN_SEED;
            case "wildblood":
                return ItemID.WILDBLOOD_SEED;

            // Trees.
            case "acorn":
                return ItemID.ACORN;
            case "willow":
                return ItemID.WILLOW_SEED;
            case "maple":
                return ItemID.MAPLE_SEED;
            case "yew":
                return ItemID.YEW_SEED;
            case "magic":
                return ItemID.MAGIC_SEED;
            case "redwood tree":
                return ItemID.REDWOOD_TREE_SEED;

            // Fruit trees.
            case "apple tree":
                return ItemID.APPLE_TREE_SEED;
            case "banana tree":
                return ItemID.BANANA_TREE_SEED;
            case "orange tree":
                return ItemID.ORANGE_TREE_SEED;
            case "curry tree":
                return ItemID.CURRY_TREE_SEED;
            case "pineapple":
                return ItemID.PINEAPPLE_SEED;
            case "papaya tree":
                return ItemID.PAPAYA_TREE_SEED;
            case "palm tree":
                return ItemID.PALM_TREE_SEED;
            case "dragonfruit tree":
                return ItemID.DRAGONFRUIT_TREE_SEED;

            // Bushes.
            case "redberry":
                return ItemID.REDBERRY_SEED;
            case "cadavaberry":
                return ItemID.CADAVABERRY_SEED;
            case "dwellberry":
                return ItemID.DWELLBERRY_SEED;
            case "jangerberry":
                return ItemID.JANGERBERRY_SEED;
            case "whiteberry":
                return ItemID.WHITEBERRY_SEED;
            case "poison ivy":
                return ItemID.POISON_IVY_SEED;

            // Flowers.
            case "marigold":
                return ItemID.MARIGOLD_SEED;
            case "rosemary":
                return ItemID.ROSEMARY_SEED;
            case "nasturtium":
                return ItemID.NASTURTIUM_SEED;
            case "woad":
                return ItemID.WOAD_SEED;
            case "limpwurt":
                return ItemID.LIMPWURT_SEED;
            case "white lily":
                return ItemID.WHITE_LILY_SEED;

            // Herbs.
            case "guam":
                return ItemID.GUAM_SEED;
            case "marrentill":
                return ItemID.MARRENTILL_SEED;
            case "tarromin":
                return ItemID.TARROMIN_SEED;
            case "harralander":
                return ItemID.HARRALANDER_SEED;
            case "ranarr":
                return ItemID.RANARR_SEED;
            case "toadflax":
                return ItemID.TOADFLAX_SEED;
            case "irit":
                return ItemID.IRIT_SEED;
            case "avantoe":
                return ItemID.AVANTOE_SEED;
            case "kwuarm":
                return ItemID.KWUARM_SEED;
            case "snapdragon":
                return ItemID.SNAPDRAGON_SEED;
            case "cadantine":
                return ItemID.CADANTINE_SEED;
            case "lantadyme":
                return ItemID.LANTADYME_SEED;
            case "dwarf weed":
                return ItemID.DWARF_WEED_SEED;
            case "torstol":
                return ItemID.TORSTOL_SEED;

            // Special seeds.
            case "seaweed spore":
                return ItemID.SEAWEED_SPORE;
            case "teak":
                return ItemID.TEAK_SEED;
            case "grape":
                return ItemID.GRAPE_SEED;
            case "mushroom spore":
                return ItemID.MUSHROOM_SPORE;
            case "mahogany":
                return ItemID.MAHOGANY_SEED;
            case "cactus":
                return ItemID.CACTUS_SEED;
            case "belladonna":
                return ItemID.BELLADONNA_SEED;
            case "potato cactus":
                return ItemID.POTATO_CACTUS_SEED;
            case "hespori":
                return ItemID.HESPORI_SEED;
            case "calquat tree":
                return ItemID.CALQUAT_TREE_SEED;
            case "crystal acorn":
                return ItemID.CRYSTAL_ACORN;
            case "kronos":
                return ItemID.KRONOS_SEED;
            case "iasor":
                return ItemID.IASOR_SEED;
            case "attas":
                return ItemID.ATTAS_SEED;
            case "spirit":
                return ItemID.SPIRIT_SEED;
            case "celastrus":
                return ItemID.CELASTRUS_SEED;
        }

        return Charges.UNKNOWN;
    }
}
