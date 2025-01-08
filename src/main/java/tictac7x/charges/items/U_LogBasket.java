package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
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
import tictac7x.charges.item.triggers.OnItemPickup;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.OnXpDrop;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tictac7x.charges.store.ItemContainerId.BANK;
import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_LogBasket extends ChargedItemWithStorage {
    private Optional<StorageItem> lastLogs = Optional.empty();
    private int infernalQuantityTracker = 0;

    public U_LogBasket(
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
        super(ChargesImprovedConfig.log_basket, ItemID.LOG_BASKET, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);
        storage.setMaximumTotalQuantity(28).storeableItems(
            new StorageItem(ItemID.LOGS).displayName("Regular logs").checkName("Logs"),
            new StorageItem(ItemID.ACHEY_TREE_LOGS).checkName("Achey tree logs"),
            new StorageItem(ItemID.OAK_LOGS).checkName("Oak logs"),
            new StorageItem(ItemID.WILLOW_LOGS).checkName("Willow logs"),
            new StorageItem(ItemID.TEAK_LOGS).checkName("Teak logs"),
            new StorageItem(ItemID.JUNIPER_LOGS).checkName("Juniper logs"),
            new StorageItem(ItemID.MAPLE_LOGS).checkName("Maple logs"),
            new StorageItem(ItemID.MAHOGANY_LOGS).checkName("Mahogany logs"),
            new StorageItem(ItemID.ARCTIC_PINE_LOGS).checkName("Arctic pine logs"),
            new StorageItem(ItemID.YEW_LOGS).checkName("Yew logs"),
            new StorageItem(ItemID.BLISTERWOOD_LOGS).checkName("Blisterwood logs"),
            new StorageItem(ItemID.MAGIC_LOGS).checkName("Magic logs"),
            new StorageItem(ItemID.REDWOOD_LOGS).checkName("Redwood logs")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.LOG_BASKET),
            new TriggerItem(ItemID.OPEN_LOG_BASKET),
            new TriggerItem(ItemID.FORESTRY_BASKET),
            new TriggerItem(ItemID.OPEN_FORESTRY_BASKET),
        };
        this.triggers = new TriggerBase[] {
            // Check while empty.
            new OnChatMessage("(Your|The) basket is empty.").onItemClick().emptyStorage().consumer(() -> {
                infernalQuantityTracker = 0;
                lastLogs = Optional.empty();
            }),

            // Empty to bank.
            new OnChatMessage("You empty your basket( into the bank)?.").onItemClick().emptyStorage().consumer(() -> {
                infernalQuantityTracker = 0;
                lastLogs = Optional.empty();
            }),

            // Check.
            new OnChatMessage("The basket contains:").stringConsumer(s -> {
                storage.clear();

                final Pattern pattern = Pattern.compile("(?<quantity>\\d+).x.(?<logs>.*?)(,|$)");
                final Matcher matcher = pattern.matcher(s);

                while (matcher.find()) {
                    storage.put(getStorageItemFromName(matcher.group("logs")), Integer.parseInt(matcher.group("quantity")));
                }

                infernalQuantityTracker = getQuantity();
            }).onItemClick(),

            // Miscellania support.
            new OnChatMessage("You get some maple logs and give them to Lumberjack Leif.").requiredItem(ItemID.OPEN_LOG_BASKET, ItemID.OPEN_FORESTRY_BASKET).addToStorage(ItemID.MAPLE_LOGS, 0),

            // Chop.
            new OnChatMessage("You get some (?<logs>.+).").matcherConsumer(m -> {
                lastLogs = getStorageItemFromName(m.group("logs"));
                storage.add(lastLogs, 1);
                infernalQuantityTracker++;
            }).requiredItem(ItemID.OPEN_LOG_BASKET, ItemID.OPEN_FORESTRY_BASKET),

            new OnItemPickup(storage.getStoreableItems()).isByOne().requiredItem(ItemID.OPEN_LOG_BASKET).pickUpToStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnChatMessage("You empty as many logs as you can carry.").emptyStorageToInventory(),

            // Use log on basket.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStoreableItems()),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption("Empty"),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),

            // Infernal axe support.
            new OnXpDrop(Skill.FIREMAKING).onMenuOption("Chop down", "Cut").consumer(() -> {
                if (infernalQuantityTracker < 29 && lastLogs.isPresent()) {
                    storage.remove(lastLogs, 1);
                    infernalQuantityTracker--;
                }
            }).requiredItem(ItemID.OPEN_LOG_BASKET, ItemID.OPEN_FORESTRY_BASKET),
        };
    }

}
