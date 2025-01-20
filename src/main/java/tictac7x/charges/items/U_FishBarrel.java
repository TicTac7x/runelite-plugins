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
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tictac7x.charges.store.ItemContainerId.BANK;
import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_FishBarrel extends ChargedItemWithStorage {
    private Optional<StorageItem> lastCaughtFish = Optional.empty();

    public U_FishBarrel(
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
        super(TicTac7xChargesImprovedConfig.fish_barrel, ItemID.FISH_BARREL, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        storage = storage.setMaximumTotalQuantity(28).storableItems(
            // Small net
            new StorableItem(ItemID.RAW_SHRIMPS).checkName("Shrimp"),
            new StorableItem(ItemID.RAW_ANCHOVIES).checkName("Anchovies"),
            new StorableItem(ItemID.RAW_MONKFISH).checkName("Monkfish"),

            // Big net
            new StorableItem(ItemID.RAW_MACKEREL).checkName("Mackerel"),
            new StorableItem(ItemID.RAW_COD).checkName("Cod"),
            new StorableItem(ItemID.RAW_BASS).checkName("Bass"),

            // Rod
            new StorableItem(ItemID.RAW_SARDINE).checkName("Sardine"),
            new StorableItem(ItemID.RAW_HERRING).checkName("Herring"),
            new StorableItem(ItemID.RAW_TROUT).checkName("Trout"),
            new StorableItem(ItemID.RAW_PIKE).checkName("Pike"),
            new StorableItem(ItemID.RAW_SLIMY_EEL).checkName("Slimy swamp eel"),
            new StorableItem(ItemID.RAW_SLIMY_EEL).checkName("Slimy eel"),
            new StorableItem(ItemID.RAW_SALMON).checkName("Salmon"),
            new StorableItem(ItemID.RAW_RAINBOW_FISH).checkName("Rainbow fish"),
            new StorableItem(ItemID.RAW_CAVE_EEL).checkName("Cave eel"),
            new StorableItem(ItemID.RAW_LAVA_EEL).checkName("Lava eel"),
            new StorableItem(ItemID.INFERNAL_EEL).checkName("Infernal eel"),
            new StorableItem(ItemID.RAW_ANGLERFISH).checkName("Anglerfish"),
            new StorableItem(ItemID.SACRED_EEL).checkName("Sacred eel"),

            // Harpoon
            new StorableItem(ItemID.RAW_TUNA).checkName("Tuna"),
            new StorableItem(ItemID.RAW_SWORDFISH).checkName("Swordfish"),
            new StorableItem(ItemID.RAW_SHARK).checkName("Shark"),

            // Aerial
            new StorableItem(ItemID.BLUEGILL).checkName("Bluegill"),
            new StorableItem(ItemID.COMMON_TENCH).checkName("Common tench"),
            new StorableItem(ItemID.MOTTLED_EEL).checkName("Mottled eel"),
            new StorableItem(ItemID.GREATER_SIREN).checkName("Greater siren"),

            // Cage
            new StorableItem(ItemID.RAW_LOBSTER).checkName("Lobster"),
            new StorableItem(ItemID.RAW_DARK_CRAB).checkName("Dark crab"),

            // Barbarian
            new StorableItem(ItemID.LEAPING_TROUT).checkName("Leaping trout"),
            new StorableItem(ItemID.LEAPING_SALMON).checkName("Leaping salmon"),
            new StorableItem(ItemID.LEAPING_STURGEON).checkName("Leaping sturgeon"),

            // Other
            new StorableItem(ItemID.RAW_KARAMBWAN).checkName("Karambwan"),
            new StorableItem(ItemID.RAW_SEA_TURTLE).checkName("Sea turtle"),
            new StorableItem(ItemID.RAW_MANTA_RAY).checkName("Manta ray")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FISH_BARREL),
            new TriggerItem(ItemID.OPEN_FISH_BARREL),
            new TriggerItem(ItemID.FISH_SACK_BARREL),
            new TriggerItem(ItemID.OPEN_FISH_SACK_BARREL),
        };

        this.triggers = new TriggerBase[]{
            // Check or empty already empty.
            new OnChatMessage("(Your|The) barrel is empty.").onItemClick().emptyStorage(),

            // Catch fish.
            new OnChatMessage("You catch (a|an|some) (?<fish>.+).").matcherConsumer(m -> {
                lastCaughtFish = getStorageItemFromName(m.group("fish"));
                storage.add(lastCaughtFish, 1);
            }).requiredItem(ItemID.OPEN_FISH_BARREL, ItemID.OPEN_FISH_SACK_BARREL),

            // Extra fish.
            new OnChatMessage(".* enabled you to catch an extra fish.").requiredItem(ItemID.OPEN_FISH_BARREL, ItemID.OPEN_FISH_SACK_BARREL).consumer(() -> {
                storage.add(lastCaughtFish, 1);
            }),

            // Check.
            new OnChatMessage("The barrel contains:").stringConsumer(s -> {
                storage.clear();

                final Pattern pattern = Pattern.compile("(?<quantity>\\d+).x.(?<fish>.*?)(,|$)");
                final Matcher matcher = pattern.matcher(s);

                while (matcher.find()) {
                    storage.put(getStorageItemFromName(matcher.group("fish")), Integer.parseInt(matcher.group("quantity")));
                }
            }).onItemClick(),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Use fish on barrel.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption("Empty"),

            // Empty to deposit box.
            new OnChatMessage("You empty the barrel.").onMenuOption("Empty").emptyStorage(),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }
}
