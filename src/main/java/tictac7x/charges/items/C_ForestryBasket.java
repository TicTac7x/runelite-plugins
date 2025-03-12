package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.Store;
import tictac7x.charges.store.WidgetId;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tictac7x.charges.store.ItemContainerId.BANK;
import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class C_ForestryBasket extends ChargedItemWithStorage {
    private Optional<StorageItem> lastLogs = Optional.empty();
    private int infernalQuantityTracker = 0;
    private final String menuOptionEmptyLogsToBank = "Empty logs to bank";
    private final String menuOptionFillLeavesFromBank = "Fill leaves from bank";
    private final String menuOptionEmptyLeavesToBank = "Empty leaves to bank";

    public C_ForestryBasket(
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
        super(TicTac7xChargesImprovedConfig.forestry_kit, ItemID.FORESTRY_BASKET, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.storage = storage.storableItems(
            // Log basket.
            new StorableItem(ItemID.LOGS).displayName("Regular logs").checkName("some logs", "x Logs").specificOrder(1),
            new StorableItem(ItemID.ACHEY_TREE_LOGS).checkName("Achey tree logs").specificOrder(2),
            new StorableItem(ItemID.OAK_LOGS).checkName("Oak logs").specificOrder(3),
            new StorableItem(ItemID.WILLOW_LOGS).checkName("Willow logs").specificOrder(4),
            new StorableItem(ItemID.TEAK_LOGS).checkName("Teak logs").specificOrder(5),
            new StorableItem(ItemID.JUNIPER_LOGS).checkName("Juniper logs").specificOrder(6),
            new StorableItem(ItemID.MAPLE_LOGS).checkName("Maple logs").specificOrder(7),
            new StorableItem(ItemID.MAHOGANY_LOGS).checkName("Mahogany logs").specificOrder(8),
            new StorableItem(ItemID.ARCTIC_PINE_LOGS).checkName("Arctic pine logs").specificOrder(9),
            new StorableItem(ItemID.YEW_LOGS).checkName("Yew logs").specificOrder(10),
            new StorableItem(ItemID.BLISTERWOOD_LOGS).checkName("Blisterwood logs").specificOrder(11),
            new StorableItem(ItemID.MAGIC_LOGS).checkName("Magic logs").specificOrder(12),
            new StorableItem(ItemID.REDWOOD_LOGS).checkName("Redwood logs").specificOrder(3),

            // Forestry kit.
            new StorableItem(ItemID.ANIMAINFUSED_BARK).specificOrder(14),
            new StorableItem(ItemID.FORESTERS_RATION).specificOrder(15),
            new StorableItem(ItemID.NATURE_OFFERINGS).specificOrder(16),
            new StorableItem(ItemID.SECATEURS_ATTACHMENT).specificOrder(17),
            new StorableItem(ItemID.LEAVES).displayName("Regular leaves").checkName("regular").specificOrder(18),
            new StorableItem(ItemID.OAK_LEAVES).checkName("oak").specificOrder(19),
            new StorableItem(ItemID.WILLOW_LEAVES).checkName("willow").specificOrder(20),
            new StorableItem(ItemID.MAPLE_LEAVES).checkName("maple").specificOrder(21),
            new StorableItem(ItemID.YEW_LEAVES).checkName("yew").specificOrder(22),
            new StorableItem(ItemID.MAGIC_LEAVES).checkName("magic").specificOrder(23),
            new StorableItem(ItemID.FORESTRY_HAT).specificOrder(24),
            new StorableItem(ItemID.FORESTRY_TOP).specificOrder(25),
            new StorableItem(ItemID.FORESTRY_LEGS).specificOrder(26),
            new StorableItem(ItemID.FORESTRY_BOOTS).specificOrder(27),
            new StorableItem(ItemID.LUMBERJACK_HAT).specificOrder(28),
            new StorableItem(ItemID.LUMBERJACK_TOP).specificOrder(29),
            new StorableItem(ItemID.LUMBERJACK_LEGS).specificOrder(30),
            new StorableItem(ItemID.LUMBERJACK_BOOTS).specificOrder(31),
            new StorableItem(ItemID.WOODCUTTING_CAPE).specificOrder(32),
            new StorableItem(ItemID.WOODCUT_CAPET).specificOrder(33)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FORESTRY_BASKET),
            new TriggerItem(ItemID.OPEN_FORESTRY_BASKET),
        };

        this.triggers = new TriggerBase[]{
            // View contents.
            new OnItemContainerChanged(ItemContainerId.FORESTRY_KIT).itemsConsumer(storageItems -> {
                storage.put(ItemID.ANIMAINFUSED_BARK, storageItems.count(ItemID.ANIMAINFUSED_BARK));
                storage.put(ItemID.FORESTERS_RATION, storageItems.count(ItemID.FORESTERS_RATION));
                storage.put(ItemID.NATURE_OFFERINGS, storageItems.count(ItemID.NATURE_OFFERINGS));
                storage.put(ItemID.SECATEURS_ATTACHMENT, storageItems.count(ItemID.SECATEURS_ATTACHMENT));
                storage.put(ItemID.LEAVES, storageItems.count(ItemID.LEAVES));
                storage.put(ItemID.OAK_LEAVES, storageItems.count(ItemID.OAK_LEAVES));
                storage.put(ItemID.WILLOW_LEAVES, storageItems.count(ItemID.WILLOW_LEAVES));
                storage.put(ItemID.MAPLE_LEAVES, storageItems.count(ItemID.MAPLE_LEAVES));
                storage.put(ItemID.YEW_LEAVES, storageItems.count(ItemID.YEW_LEAVES));
                storage.put(ItemID.MAGIC_LEAVES, storageItems.count(ItemID.MAGIC_LEAVES));
                storage.put(ItemID.FORESTRY_HAT, storageItems.count(ItemID.FORESTRY_HAT));
                storage.put(ItemID.FORESTRY_TOP, storageItems.count(ItemID.FORESTRY_TOP));
                storage.put(ItemID.FORESTRY_LEGS, storageItems.count(ItemID.FORESTRY_LEGS));
                storage.put(ItemID.FORESTRY_BOOTS, storageItems.count(ItemID.FORESTRY_BOOTS));
                storage.put(ItemID.LUMBERJACK_HAT, storageItems.count(ItemID.LUMBERJACK_HAT));
                storage.put(ItemID.LUMBERJACK_TOP, storageItems.count(ItemID.LUMBERJACK_TOP));
                storage.put(ItemID.LUMBERJACK_LEGS, storageItems.count(ItemID.LUMBERJACK_LEGS));
                storage.put(ItemID.LUMBERJACK_BOOTS, storageItems.count(ItemID.LUMBERJACK_BOOTS));
                storage.put(ItemID.WOODCUTTING_CAPE, storageItems.count(ItemID.WOODCUTTING_CAPE));
                storage.put(ItemID.WOODCUT_CAPET, storageItems.count(ItemID.WOODCUT_CAPET));
            }),

            // Get leaves while chopping wood.
            new OnChatMessage("Some (?<leaves>.+) leaves fall to the ground and you place them into your Forestry kit.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("leaves")), 1);
            }),

            // Get leaves from event.
            new OnChatMessage("You've been awarded (?<amount>.+) piles of (?<leaves>.+) leaves which you put into your Forestry kit.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("leaves")), Integer.parseInt(m.group("amount")));
            }),

            // Get bark from an event.
            new OnChatMessage("You've been awarded (?<bark>.+) Anima-infused bark.").matcherConsumer(m -> {
                storage.add(ItemID.ANIMAINFUSED_BARK, Integer.parseInt(m.group("bark")));
            }),

            // Use ration when choping.
            new OnChatMessage("You consume a Forester's ration to fuel a mighty chop.").consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemID.FORESTERS_RATION, 1);
            }),

            // Nature offering used.
            new OnChatMessage("The nature offerings enabled you to chop an extra log").consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemID.NATURE_OFFERINGS, 1);
            }),

            // Out of rations.
            new OnChatMessage("You've eaten your last Forester's ration.").consumer(() -> {
                storage.put(ItemID.FORESTERS_RATION, 0);
            }),

            // Buy items from Friendly Forester by 1.
            new OnMenuOptionClicked("Buy-1").consumer(() -> {
                purchaseFromFriendlyForesterShop(1);
            }),

            // Buy items from Friendly Forester by 5.
            new OnMenuOptionClicked("Buy-5").consumer(() -> {
                purchaseFromFriendlyForesterShop(5);
            }),

            // Buy items from Friendly Forester by 10.
            new OnMenuOptionClicked("Buy-10").consumer(() -> {
                purchaseFromFriendlyForesterShop(10);
            }),

            // Buy items from Friendly Forester by 50.
            new OnMenuOptionClicked("Buy-50").consumer(() -> {
                purchaseFromFriendlyForesterShop(50);
            }),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),

            // Check while empty.
            new OnChatMessage("(Your|The) basket is empty.").onItemClick().emptyStorage().consumer(() -> {
                infernalQuantityTracker = 0;
                lastLogs = Optional.empty();
            }),

            // Empty to bank.
            new OnChatMessage("You empty your basket( into the bank)?.").onItemClick().consumer(() -> {
                emptyLogBasket();
                infernalQuantityTracker = 0;
                lastLogs = Optional.empty();
            }),

            // Check.
            new OnChatMessage("The basket contains:").stringConsumer(s -> {
                final Pattern pattern = Pattern.compile("(?<quantity>\\d+).x.(?<logs>.*?)(,|$)");
                final Matcher matcher = pattern.matcher(s);

                while (matcher.find()) {
                    storage.put(getStorageItemFromName(matcher.group("logs")), Integer.parseInt(matcher.group("quantity")));
                }

                infernalQuantityTracker = getQuantity();
            }),

            // Miscellania support.
            new OnChatMessage("You get some maple logs and give them to Lumberjack Leif.").requiredItem(ItemID.OPEN_FORESTRY_BASKET).addToStorage(ItemID.MAPLE_LOGS, 0),

            // Chop.
            new OnChatMessage("You get (?<logs>some .+).").matcherConsumer(m -> {
                lastLogs = getStorageItemFromName(m.group("logs"));
                storage.add(lastLogs, 1);
                infernalQuantityTracker++;
            }).requiredItem(ItemID.OPEN_FORESTRY_BASKET),

            // Extra logs from nature offerings.
            new OnChatMessage("The nature offerings enabled you to chop an extra log.").requiredItem(ItemID.OPEN_FORESTRY_BASKET).runConsumerOnNextGameTick(() -> {
                if (lastLogs.isPresent()) {
                    storage.add(lastLogs.get().itemId, 1);
                }
            }),

            new OnItemPickup(storage.getStorableItems()).isByOne().requiredItem(ItemID.OPEN_FORESTRY_BASKET).pickUpToStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Continue").hasChatMessage("You empty as many logs as you can carry.").emptyStorageToInventory(),

            // Use log on basket.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption(menuOptionEmptyLogsToBank, menuOptionEmptyLeavesToBank),

            // Fill from bank.
            new OnItemContainerChanged(BANK).onMenuOption(menuOptionFillLeavesFromBank).onBankDifference(itemsDifference -> {
                for (final StorageItem item : itemsDifference.getItems()) {
                    switch (item.itemId) {
                        case ItemID.LEAVES:
                        case ItemID.OAK_LEAVES:
                        case ItemID.WILLOW_LEAVES:
                        case ItemID.MAPLE_LEAVES:
                        case ItemID.YEW_LEAVES:
                        case ItemID.MAGIC_LEAVES:
                            break;
                        default:
                            continue;
                    }
                    storage.add(item.itemId, Math.abs(item.getQuantity()));
                }
            }),

            // Leprechaun.
            new OnMenuOptionClicked("Continue").consumer(() -> {
                final Optional<Widget> bankWoodcuttingResourcesWidget = TicTac7xChargesImprovedPlugin.getWidget(client, 219, 1, 2);
                if (bankWoodcuttingResourcesWidget.isPresent() && bankWoodcuttingResourcesWidget.get().getText().equals("Only bank woodcutting resources")) {
                    emptyLogBasket();
                }
            }),

            // Replace "Use" with proper "Empty/Fill".
            new OnMenuEntryAdded("Use").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),

            // Infernal axe support.
            new OnXpDrop(Skill.FIREMAKING).onMenuOption("Chop down", "Cut").consumer(() -> {
                if (infernalQuantityTracker < 29 && lastLogs.isPresent()) {
                    storage.remove(lastLogs, 1);
                    infernalQuantityTracker--;
                }
            }).requiredItem(ItemID.OPEN_FORESTRY_BASKET),
        };
    }

    private void purchaseFromFriendlyForesterShop(final int amountToBuy) {
        final Optional<Widget> forestryShopWidget = TicTac7xChargesImprovedPlugin.getWidget(client, 819, 3);
        if (!forestryShopWidget.isPresent()) return;

        int animaBarkPerItem = 0;
        final int selectedShopItem = client.getVarpValue(3869);
        switch (selectedShopItem) {
            case 0: // Forestry kit
                break;
            case 1: // Secateurs blade
                animaBarkPerItem = 20;
                break;
            case 2: // Ritual mulch
                animaBarkPerItem = 150;
                break;
            case 6: // Log brace
                animaBarkPerItem = 3_000;
                break;
            case 7: // Clothes pouch blueprint
                animaBarkPerItem = 10_000;
                break;
            case 8: // Cape pouch
                animaBarkPerItem = 2_500;
                break;
            case 9: // Log basket
                animaBarkPerItem = 5_000;
                break;
            case 10: // Felling axe handle
                animaBarkPerItem = 10_000;
                break;
            case 11: // Twitcher's gloves
                animaBarkPerItem = 5_000;
                break;
            case 12: // Funky shaped log
                animaBarkPerItem = 15_000;
                break;
            case 13: // Sawmill voucher (x10)
                animaBarkPerItem = 150;
                break;
            case 14: // Lumberjack boots
                animaBarkPerItem = 1_000;
                break;
            case 15: // Lumberjack hat
                animaBarkPerItem = 1_200;
                break;
            case 16: // Lumberjack legs
                animaBarkPerItem = 1_300;
                break;
            case 17: // Lumberjack top
                animaBarkPerItem = 1_500;
                break;
            case 18: // Forestry boots
            case 19: // Forestry hat
            case 20: // Forestry legs
            case 21: // Forestry top
                animaBarkPerItem = 1_250;
                break;
        }

        storage.removeAndPrioritizeInventory(ItemID.ANIMAINFUSED_BARK, animaBarkPerItem * amountToBuy);
    }

    private String getMenuOptionForUse() {
        return isLogsInBasket()
            ? menuOptionEmptyLogsToBank
            : isLeavesInBasket()
                ? menuOptionEmptyLeavesToBank
                : menuOptionFillLeavesFromBank;
    }

    private void emptyLogBasket() {
        storage.put(ItemID.LOGS, 0);
        storage.put(ItemID.ACHEY_TREE_LOGS, 0);
        storage.put(ItemID.OAK_LOGS, 0);
        storage.put(ItemID.WILLOW_LOGS, 0);
        storage.put(ItemID.TEAK_LOGS, 0);
        storage.put(ItemID.JUNIPER_LOGS, 0);
        storage.put(ItemID.MAPLE_LOGS, 0);
        storage.put(ItemID.MAHOGANY_LOGS, 0);
        storage.put(ItemID.ARCTIC_PINE_LOGS, 0);
        storage.put(ItemID.YEW_LOGS, 0);
        storage.put(ItemID.BLISTERWOOD_LOGS, 0);
        storage.put(ItemID.MAGIC_LOGS, 0);
        storage.put(ItemID.REDWOOD_LOGS, 0);
    }

    private boolean isLogsInBasket() {
        for (final StorageItem storageItem : storage.getStorage().getItems()) {
            if (storageItem.getQuantity() == 0) continue;

            switch (storageItem.itemId) {
                case ItemID.LOGS:
                case ItemID.ACHEY_TREE_LOGS:
                case ItemID.OAK_LOGS:
                case ItemID.WILLOW_LOGS:
                case ItemID.TEAK_LOGS:
                case ItemID.JUNIPER_LOGS:
                case ItemID.MAPLE_LOGS:
                case ItemID.MAHOGANY_LOGS:
                case ItemID.ARCTIC_PINE_LOGS:
                case ItemID.YEW_LOGS:
                case ItemID.BLISTERWOOD_LOGS:
                case ItemID.MAGIC_LOGS:
                case ItemID.REDWOOD_LOGS:
                    return true;
            }
        }
        return false;
    }

    private boolean isLeavesInBasket() {
        for (final StorageItem storageItem : storage.getStorage().getItems()) {
            if (storageItem.getQuantity() == 0) continue;

            switch (storageItem.itemId) {
                case ItemID.LEAVES:
                case ItemID.OAK_LEAVES:
                case ItemID.WILLOW_LEAVES:
                case ItemID.MAPLE_LEAVES:
                case ItemID.YEW_LEAVES:
                case ItemID.MAGIC_LEAVES:
                    return true;
            }
        }
        return false;
    }
}
