package tictac7x.charges.items.capes;

import tictac7x.charges.store.*;
import net.runelite.api.Skill;
import net.runelite.api.widgets.Widget;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.ItemContainerId;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.ids.WidgetId;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tictac7x.charges.store.ids.ItemContainerId.BANK;
import static tictac7x.charges.store.ids.ItemContainerId.INVENTORY;

public class C_ForestryBasket extends ChargedItemWithStorage {
    private Optional<StorageItem> lastLogs = Optional.empty();
    private int infernalQuantityTracker = 0;
    private final String menuOptionEmptyLogsToBank = "Empty logs to bank";
    private final String menuOptionFillLeavesFromBank = "Fill leaves from bank";
    private final String menuOptionEmptyLeavesToBank = "Empty leaves to bank";

    public C_ForestryBasket(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.forestry_basket, ItemId.FORESTRY_BASKET, provider);

        this.storage = storage.storableItems(
            // Log basket.
            new StorableItem(ItemId.LOGS).displayName("Regular logs").checkName("some logs", "x Logs").specificOrder(1),
            new StorableItem(ItemId.ACHEY_TREE_LOGS).checkName("Achey tree logs").specificOrder(2),
            new StorableItem(ItemId.OAK_LOGS).checkName("Oak logs").specificOrder(3),
            new StorableItem(ItemId.WILLOW_LOGS).checkName("Willow logs").specificOrder(4),
            new StorableItem(ItemId.TEAK_LOGS).checkName("Teak logs").specificOrder(5),
            new StorableItem(ItemId.JUNIPER_LOGS).checkName("Juniper logs").specificOrder(6),
            new StorableItem(ItemId.MAPLE_LOGS).checkName("Maple logs").specificOrder(7),
            new StorableItem(ItemId.MAHOGANY_LOGS).checkName("Mahogany logs").specificOrder(8),
            new StorableItem(ItemId.ARCTIC_PINE_LOGS).checkName("Arctic pine logs").specificOrder(9),
            new StorableItem(ItemId.YEW_LOGS).checkName("Yew logs").specificOrder(10),
            new StorableItem(ItemId.BLISTERWOOD_LOGS).checkName("Blisterwood logs").specificOrder(11),
            new StorableItem(ItemId.MAGIC_LOGS).checkName("Magic logs").specificOrder(12),
            new StorableItem(ItemId.REDWOOD_LOGS).checkName("Redwood logs").specificOrder(3),

            // Forestry kit.
            new StorableItem(ItemId.ANIMAINFUSED_BARK).specificOrder(14),
            new StorableItem(ItemId.FORESTERS_RATION).specificOrder(15),
            new StorableItem(ItemId.NATURE_OFFERINGS).specificOrder(16),
            new StorableItem(ItemId.SECATEURS_ATTACHMENT).specificOrder(17),
            new StorableItem(ItemId.LEAVES).displayName("Regular leaves").checkName("regular").specificOrder(18),
            new StorableItem(ItemId.OAK_LEAVES).checkName("oak").specificOrder(19),
            new StorableItem(ItemId.WILLOW_LEAVES).checkName("willow").specificOrder(20),
            new StorableItem(ItemId.MAPLE_LEAVES).checkName("maple").specificOrder(21),
            new StorableItem(ItemId.YEW_LEAVES).checkName("yew").specificOrder(22),
            new StorableItem(ItemId.MAGIC_LEAVES).checkName("magic").specificOrder(23),
            new StorableItem(ItemId.FORESTRY_HAT).specificOrder(24),
            new StorableItem(ItemId.FORESTRY_TOP).specificOrder(25),
            new StorableItem(ItemId.FORESTRY_LEGS).specificOrder(26),
            new StorableItem(ItemId.FORESTRY_BOOTS).specificOrder(27),
            new StorableItem(ItemId.LUMBERJACK_HAT).specificOrder(28),
            new StorableItem(ItemId.LUMBERJACK_TOP).specificOrder(29),
            new StorableItem(ItemId.LUMBERJACK_LEGS).specificOrder(30),
            new StorableItem(ItemId.LUMBERJACK_BOOTS).specificOrder(31),
            new StorableItem(ItemId.WOODCUTTING_CAPE).specificOrder(32),
            new StorableItem(ItemId.WOODCUTTING_CAPE_TRIMMED).specificOrder(33)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FORESTRY_BASKET),
            new TriggerItem(ItemId.FORESTRY_BASKET_OPEN),
        };

        this.triggers = new TriggerBase[]{
            // View contents.
            new OnItemContainerChanged(ItemContainerId.FORESTRY_KIT).itemsConsumer(storageItems -> {
                storage.put(ItemId.ANIMAINFUSED_BARK, storageItems.count(ItemId.ANIMAINFUSED_BARK));
                storage.put(ItemId.FORESTERS_RATION, storageItems.count(ItemId.FORESTERS_RATION));
                storage.put(ItemId.NATURE_OFFERINGS, storageItems.count(ItemId.NATURE_OFFERINGS));
                storage.put(ItemId.SECATEURS_ATTACHMENT, storageItems.count(ItemId.SECATEURS_ATTACHMENT));
                storage.put(ItemId.LEAVES, storageItems.count(ItemId.LEAVES));
                storage.put(ItemId.OAK_LEAVES, storageItems.count(ItemId.OAK_LEAVES));
                storage.put(ItemId.WILLOW_LEAVES, storageItems.count(ItemId.WILLOW_LEAVES));
                storage.put(ItemId.MAPLE_LEAVES, storageItems.count(ItemId.MAPLE_LEAVES));
                storage.put(ItemId.YEW_LEAVES, storageItems.count(ItemId.YEW_LEAVES));
                storage.put(ItemId.MAGIC_LEAVES, storageItems.count(ItemId.MAGIC_LEAVES));
                storage.put(ItemId.FORESTRY_HAT, storageItems.count(ItemId.FORESTRY_HAT));
                storage.put(ItemId.FORESTRY_TOP, storageItems.count(ItemId.FORESTRY_TOP));
                storage.put(ItemId.FORESTRY_LEGS, storageItems.count(ItemId.FORESTRY_LEGS));
                storage.put(ItemId.FORESTRY_BOOTS, storageItems.count(ItemId.FORESTRY_BOOTS));
                storage.put(ItemId.LUMBERJACK_HAT, storageItems.count(ItemId.LUMBERJACK_HAT));
                storage.put(ItemId.LUMBERJACK_TOP, storageItems.count(ItemId.LUMBERJACK_TOP));
                storage.put(ItemId.LUMBERJACK_LEGS, storageItems.count(ItemId.LUMBERJACK_LEGS));
                storage.put(ItemId.LUMBERJACK_BOOTS, storageItems.count(ItemId.LUMBERJACK_BOOTS));
                storage.put(ItemId.WOODCUTTING_CAPE, storageItems.count(ItemId.WOODCUTTING_CAPE));
                storage.put(ItemId.WOODCUTTING_CAPE_TRIMMED, storageItems.count(ItemId.WOODCUTTING_CAPE_TRIMMED));
            }),

            // Get leaves while chopping wood.
            new OnChatMessage("Some (?<leaves>.+) leaves fall to the ground and you place them into your Forestry kit.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("leaves"), 1));
            }),

            // Get leaves from event.
            new OnChatMessage("You've been awarded (?<amount>.+) piles of (?<leaves>.+) leaves which you put into your Forestry kit.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("leaves"), Integer.parseInt(m.group("amount"))));
            }),

            // Get bark from an event.
            new OnChatMessage("You've been awarded (?<bark>.+) Anima-infused bark.").matcherConsumer(m -> {
                storage.add(ItemId.ANIMAINFUSED_BARK, Integer.parseInt(m.group("bark")));
            }),

            // Use ration when choping.
            new OnChatMessage("You consume a Forester's ration to fuel a mighty chop.").consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemId.FORESTERS_RATION, 1);
            }),

            // Nature offering used.
            new OnChatMessage("The nature offerings enabled you to chop an extra log").consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemId.NATURE_OFFERINGS, 1);
            }),

            // Out of rations.
            new OnChatMessage("You've eaten your last Forester's ration.").consumer(() -> {
                storage.put(ItemId.FORESTERS_RATION, 0);
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
                    storage.put(getStorageItemFromName(matcher.group("logs"), Integer.parseInt(matcher.group("quantity"))));
                }

                infernalQuantityTracker = getQuantity();
            }),

            // Miscellania support.
            new OnChatMessage("You get some maple logs and give them to Lumberjack Leif.").requiredItem(ItemId.FORESTRY_BASKET_OPEN).addToStorage(ItemId.MAPLE_LOGS, 0),

            // Chop.
            new OnChatMessage("You get (?<logs>some .+).").matcherConsumer(m -> {
                lastLogs = getStorageItemFromName(m.group("logs"), 1);
                storage.add(lastLogs);
                infernalQuantityTracker++;
            }).requiredItem(ItemId.FORESTRY_BASKET_OPEN),

            // Extra logs from nature offerings.
            new OnChatMessage("The nature offerings enabled you to chop an extra log.").requiredItem(ItemId.FORESTRY_BASKET_OPEN).runConsumerOnNextGameTick(() -> {
                if (lastLogs.isPresent()) {
                    storage.add(lastLogs.get().getId(), 1);
                }
            }),

            new OnItemPickup(storage.getStorableItems()).isByOne().requiredItem(ItemId.FORESTRY_BASKET_OPEN).pickUpToStorage(),

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
                    switch (item.getId()) {
                        case ItemId.LEAVES:
                        case ItemId.OAK_LEAVES:
                        case ItemId.WILLOW_LEAVES:
                        case ItemId.MAPLE_LEAVES:
                        case ItemId.YEW_LEAVES:
                        case ItemId.MAGIC_LEAVES:
                            break;
                        default:
                            continue;
                    }
                    storage.add(item.getId(), Math.abs(item.getQuantity()));
                }
            }),

            // Leprechaun.
            new OnMenuOptionClicked("Continue").consumer(() -> {
                final Optional<Widget> bankWoodcuttingResourcesWidget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 219, 1, 2);
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
                    storage.remove(lastLogs.get().getId(), 1);
                    infernalQuantityTracker--;
                }
            }).requiredItem(ItemId.FORESTRY_BASKET_OPEN),
        };
    }

    private void purchaseFromFriendlyForesterShop(final int amountToBuy) {
        final Optional<Widget> forestryShopWidget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 819, 3);
        if (!forestryShopWidget.isPresent()) return;

        int animaBarkPerItem = 0;
        final int selectedShopItem = provider.client.getVarpValue(3869);
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

        storage.removeAndPrioritizeInventory(ItemId.ANIMAINFUSED_BARK, animaBarkPerItem * amountToBuy);
    }

    private String getMenuOptionForUse() {
        return isLogsInBasket()
            ? menuOptionEmptyLogsToBank
            : isLeavesInBasket()
                ? menuOptionEmptyLeavesToBank
                : menuOptionFillLeavesFromBank;
    }

    private void emptyLogBasket() {
        storage.put(ItemId.LOGS, 0);
        storage.put(ItemId.ACHEY_TREE_LOGS, 0);
        storage.put(ItemId.OAK_LOGS, 0);
        storage.put(ItemId.WILLOW_LOGS, 0);
        storage.put(ItemId.TEAK_LOGS, 0);
        storage.put(ItemId.JUNIPER_LOGS, 0);
        storage.put(ItemId.MAPLE_LOGS, 0);
        storage.put(ItemId.MAHOGANY_LOGS, 0);
        storage.put(ItemId.ARCTIC_PINE_LOGS, 0);
        storage.put(ItemId.YEW_LOGS, 0);
        storage.put(ItemId.BLISTERWOOD_LOGS, 0);
        storage.put(ItemId.MAGIC_LOGS, 0);
        storage.put(ItemId.REDWOOD_LOGS, 0);
    }

    private boolean isLogsInBasket() {
        for (final StorageItem storageItem : storage.getStorage().getItems()) {
            if (storageItem.getQuantity() == 0) continue;

            switch (storageItem.getId()) {
                case ItemId.LOGS:
                case ItemId.ACHEY_TREE_LOGS:
                case ItemId.OAK_LOGS:
                case ItemId.WILLOW_LOGS:
                case ItemId.TEAK_LOGS:
                case ItemId.JUNIPER_LOGS:
                case ItemId.MAPLE_LOGS:
                case ItemId.MAHOGANY_LOGS:
                case ItemId.ARCTIC_PINE_LOGS:
                case ItemId.YEW_LOGS:
                case ItemId.BLISTERWOOD_LOGS:
                case ItemId.MAGIC_LOGS:
                case ItemId.REDWOOD_LOGS:
                    return true;
            }
        }
        return false;
    }

    private boolean isLeavesInBasket() {
        for (final StorageItem storageItem : storage.getStorage().getItems()) {
            if (storageItem.getQuantity() == 0) continue;

            switch (storageItem.getId()) {
                case ItemId.LEAVES:
                case ItemId.OAK_LEAVES:
                case ItemId.WILLOW_LEAVES:
                case ItemId.MAPLE_LEAVES:
                case ItemId.YEW_LEAVES:
                case ItemId.MAGIC_LEAVES:
                    return true;
            }
        }
        return false;
    }
}
