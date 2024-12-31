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
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
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
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(ChargesImprovedConfig.fish_barrel, ItemID.FISH_BARREL, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);
        storage = storage.setMaximumTotalQuantity(28).storeableItems(
            // Small net
            new StorageItem(ItemID.RAW_SHRIMPS).checkName("Shrimp"),
            new StorageItem(ItemID.RAW_ANCHOVIES).checkName("Anchovies"),
            new StorageItem(ItemID.RAW_MONKFISH).checkName("Monkfish"),

            // Big net
            new StorageItem(ItemID.RAW_MACKEREL).checkName("Mackerel"),
            new StorageItem(ItemID.RAW_COD).checkName("Cod"),
            new StorageItem(ItemID.RAW_BASS).checkName("Bass"),

            // Rod
            new StorageItem(ItemID.RAW_SARDINE).checkName("Sardine"),
            new StorageItem(ItemID.RAW_HERRING).checkName("Herring"),
            new StorageItem(ItemID.RAW_TROUT).checkName("Trout"),
            new StorageItem(ItemID.RAW_PIKE).checkName("Pike"),
            new StorageItem(ItemID.RAW_SLIMY_EEL).checkName("Slimy swamp eel"),
            new StorageItem(ItemID.RAW_SLIMY_EEL).checkName("Slimy eel"),
            new StorageItem(ItemID.RAW_SALMON).checkName("Salmon"),
            new StorageItem(ItemID.RAW_RAINBOW_FISH).checkName("Rainbow fish"),
            new StorageItem(ItemID.RAW_CAVE_EEL).checkName("Cave eel"),
            new StorageItem(ItemID.RAW_LAVA_EEL).checkName("Lava eel"),
            new StorageItem(ItemID.INFERNAL_EEL).checkName("Infernal eel"),
            new StorageItem(ItemID.RAW_ANGLERFISH).checkName("Anglerfish"),
            new StorageItem(ItemID.SACRED_EEL).checkName("Sacred eel"),

            // Harpoon
            new StorageItem(ItemID.RAW_TUNA).checkName("Tuna"),
            new StorageItem(ItemID.RAW_SWORDFISH).checkName("Swordfish"),
            new StorageItem(ItemID.RAW_SHARK).checkName("Shark"),

            // Aerial
            new StorageItem(ItemID.BLUEGILL).checkName("Bluegill"),
            new StorageItem(ItemID.COMMON_TENCH).checkName("Common tench"),
            new StorageItem(ItemID.MOTTLED_EEL).checkName("Mottled eel"),
            new StorageItem(ItemID.GREATER_SIREN).checkName("Greater siren"),

            // Cage
            new StorageItem(ItemID.RAW_LOBSTER).checkName("Lobster"),
            new StorageItem(ItemID.RAW_DARK_CRAB).checkName("Dark crab"),

            // Barbarian
            new StorageItem(ItemID.LEAPING_TROUT).checkName("Leaping trout"),
            new StorageItem(ItemID.LEAPING_SALMON).checkName("Leaping salmon"),
            new StorageItem(ItemID.LEAPING_STURGEON).checkName("Leaping sturgeon"),

            // Other
            new StorageItem(ItemID.RAW_KARAMBWAN).checkName("Karambwan"),
            new StorageItem(ItemID.RAW_SEA_TURTLE).checkName("Sea turtle"),
            new StorageItem(ItemID.RAW_MANTA_RAY).checkName("Manta ray")
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
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStoreableItems()),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption("Empty"),

            // Empty to deposit box.
            new OnChatMessage("You empty the barrel.").onMenuOption("Empty").emptyStorage(),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }
}
