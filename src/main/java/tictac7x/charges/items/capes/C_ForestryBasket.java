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

import java.util.List;
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
    private Optional<Integer> lastLogUsedFromBasketForBeehive = Optional.empty();
    private Optional<StorageItem> lastLeaves = Optional.empty();

    public C_ForestryBasket(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.forestry_basket, ItemId.FORESTRY_BASKET, provider);

        this.storage = storage.storableItems(
            // Log basket.
            new StorableItem(ItemId.LOGS).displayName("Regular logs").checkName("some logs", "x Logs"),
            new StorableItem(ItemId.OAK_LOGS).checkName("Oak logs"),
            new StorableItem(ItemId.WILLOW_LOGS).checkName("Willow logs"),
            new StorableItem(ItemId.MAPLE_LOGS).checkName("Maple logs"),
            new StorableItem(ItemId.YEW_LOGS).checkName("Yew logs"),
            new StorableItem(ItemId.MAGIC_LOGS).checkName("Magic logs"),
            new StorableItem(ItemId.REDWOOD_LOGS).checkName("Redwood logs"),
            new StorableItem(ItemId.TEAK_LOGS).checkName("Teak logs"),
            new StorableItem(ItemId.MAHOGANY_LOGS).checkName("Mahogany logs"),
            new StorableItem(ItemId.ACHEY_TREE_LOGS).checkName("Achey tree logs"),
            new StorableItem(ItemId.ARCTIC_PINE_LOGS).checkName("Arctic pine logs"),
            new StorableItem(ItemId.JUNIPER_LOGS).checkName("Juniper logs"),
            new StorableItem(ItemId.BARK).checkName("Bark"),
            new StorableItem(ItemId.BLISTERWOOD_LOGS).checkName("Blisterwood logs"),
            new StorableItem(ItemId.CAMPHOR_LOGS).checkName("Camphor logs"),
            new StorableItem(ItemId.IRONWOOD_LOGS).checkName("Ironwood logs"),
            new StorableItem(ItemId.ROSEWOOD_LOGS).checkName("Rosewood logs"),

            // Forestry kit.
            new StorableItem(ItemId.ANIMAINFUSED_BARK),
            new StorableItem(ItemId.FORESTERS_RATION),
            new StorableItem(ItemId.NATURE_OFFERINGS),
            new StorableItem(ItemId.SECATEURS_ATTACHMENT),
            new StorableItem(ItemId.LEAVES).displayName("Regular leaves").checkName("regular"),
            new StorableItem(ItemId.OAK_LEAVES).checkName("oak"),
            new StorableItem(ItemId.WILLOW_LEAVES).checkName("willow"),
            new StorableItem(ItemId.MAPLE_LEAVES).checkName("maple"),
            new StorableItem(ItemId.YEW_LEAVES).checkName("yew"),
            new StorableItem(ItemId.MAGIC_LEAVES).checkName("magic"),
            new StorableItem(ItemId.FORESTRY_HAT),
            new StorableItem(ItemId.FORESTRY_TOP),
            new StorableItem(ItemId.FORESTRY_LEGS),
            new StorableItem(ItemId.FORESTRY_BOOTS),
            new StorableItem(ItemId.LUMBERJACK_HAT),
            new StorableItem(ItemId.LUMBERJACK_TOP),
            new StorableItem(ItemId.LUMBERJACK_LEGS),
            new StorableItem(ItemId.LUMBERJACK_BOOTS),
            new StorableItem(ItemId.WOODCUTTING_CAPE),
            new StorableItem(ItemId.WOODCUTTING_CAPE_TRIMMED)
        ).setMaximumComboQuantity(new int[]{
            ItemId.LOGS,
            ItemId.ACHEY_TREE_LOGS,
            ItemId.OAK_LOGS,
            ItemId.WILLOW_LOGS,
            ItemId.TEAK_LOGS,
            ItemId.JUNIPER_LOGS,
            ItemId.MAPLE_LOGS,
            ItemId.MAHOGANY_LOGS,
            ItemId.ARCTIC_PINE_LOGS,
            ItemId.YEW_LOGS,
            ItemId.BLISTERWOOD_LOGS,
            ItemId.MAGIC_LOGS,
            ItemId.REDWOOD_LOGS,
        }, 28);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FORESTRY_BASKET),
            new TriggerItem(ItemId.FORESTRY_BASKET_OPEN),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("You empty all of your containers into the bank").consumer(() -> emptyLogBasket()),

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
                lastLeaves = getStorageItemFromName(m.group("leaves"), 1);
                storage.add(lastLeaves);
            }),

            // Secateurs attachment.
            new OnChatMessage("Your secateurs attachment enabled you to gather extra leaves.").runConsumerOnNextGameTick(() -> {
                storage.add(lastLeaves);
                storage.removeAndPrioritizeInventory(ItemId.SECATEURS_ATTACHMENT, 1);
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
            new OnChatMessage("You empty your basket( into the bank)?.").consumer(() -> {
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

                infernalQuantityTracker = getLogsInBasket();
            }),

            // Miscellania support.
            new OnChatMessage("You get some maple logs and give them to Lumberjack Leif.").requiredItem(ItemId.FORESTRY_BASKET_OPEN).addToStorage(ItemId.MAPLE_LOGS, 0),

            // Achey tree.
            new OnChatMessage("You get some logs.").onMenuTarget("Achey Tree").consumer(() -> {
                lastLogs = Optional.of(new StorageItem(ItemId.ACHEY_TREE_LOGS, 1));
                storage.add(lastLogs);
                infernalQuantityTracker++;
            }).requiredItem(ItemId.LOG_BASKET_OPEN),

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

            // Fill log basket from inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Log basket").onItemClick().fillStorageFromInventory(),

            // Fill forestry kit from inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Forestry kit").onItemClick().fillStorageFromInventory(),

            // Empty to inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Empty basket").onItemClick().emptyStorageToInventory(),

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

            // Beehives.
            new OnXpDrop(Skill.WOODCUTTING).onMenuOption("Use").onMenuTarget(
                    "Logs",
                    "Achey tree logs",
                    "Oak logs",
                    "Willow logs",
                    "Teak logs",
                    "Maple logs",
                    "Mahogany logs",
                    "Arctic pine logs",
                    "Yew logs",
                    "Magic logs",
                    "Redwood logs",
                    "Camphor logs",
                    "Ironwood logs",
                    "Rosewood logs"
            ).consumer(this::buildBeehive),
            new OnChatMessage("Well done, you've completed a beehive. The bees can now be safely rehomed.").consumer(() -> {
                if (lastLogUsedFromBasketForBeehive.isPresent()) {
                    storage.add(lastLogUsedFromBasketForBeehive.get(), 1);
                    lastLogUsedFromBasketForBeehive = Optional.empty();
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
            }).requiredItem(ItemId.FORESTRY_BASKET_OPEN)
        ));
    }

    private void purchaseFromFriendlyForesterShop(final int amountToBuy) {
        final Optional<Widget> forestryShopWidget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 819, 3);
        if (!forestryShopWidget.isPresent()) return;

        provider.store.addConsumerToNextTickQueue(() -> {
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
                case 4: // Log brace
                    animaBarkPerItem = 3_000;
                    break;
                case 5: // Clothes pouch blueprint
                    animaBarkPerItem = 10_000;
                    break;
                case 6: // Cape pouch
                    animaBarkPerItem = 2_500;
                    break;
                case 7: // Log basket
                    animaBarkPerItem = 5_000;
                    break;
                case 8: // Felling axe handle
                    animaBarkPerItem = 10_000;
                    break;
                case 9: // Twitcher's gloves
                    animaBarkPerItem = 5_000;
                    break;
                case 10: // Funky shaped log
                    animaBarkPerItem = 15_000;
                    break;
                case 11: // Sawmill voucher (x10)
                    animaBarkPerItem = 150;
                    break;
                case 12: // Lumberjack boots
                    animaBarkPerItem = 1_000;
                    break;
                case 13: // Lumberjack hat
                    animaBarkPerItem = 1_200;
                    break;
                case 14: // Lumberjack legs
                    animaBarkPerItem = 1_300;
                    break;
                case 15: // Lumberjack top
                    animaBarkPerItem = 1_500;
                    break;
                case 16: // Forestry boots
                case 17: // Forestry hat
                case 18: // Forestry legs
                case 19: // Forestry top
                    animaBarkPerItem = 1_250;
                    break;
                case 21: // Golden pheasant egg
                case 22: // Fox whistle
                    animaBarkPerItem = 75_000;
            }

            storage.removeAndPrioritizeInventory(ItemId.ANIMAINFUSED_BARK, animaBarkPerItem * amountToBuy);
        });
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
        storage.put(ItemId.OAK_LOGS, 0);
        storage.put(ItemId.WILLOW_LOGS, 0);
        storage.put(ItemId.MAPLE_LOGS, 0);
        storage.put(ItemId.YEW_LOGS, 0);
        storage.put(ItemId.MAGIC_LOGS, 0);
        storage.put(ItemId.REDWOOD_LOGS, 0);
        storage.put(ItemId.TEAK_LOGS, 0);
        storage.put(ItemId.MAHOGANY_LOGS, 0);
        storage.put(ItemId.ACHEY_TREE_LOGS, 0);
        storage.put(ItemId.ARCTIC_PINE_LOGS, 0);
        storage.put(ItemId.JUNIPER_LOGS, 0);
        storage.put(ItemId.BARK, 0);
        storage.put(ItemId.BLISTERWOOD_LOGS, 0);
    }

    private int getLogsInBasket() {
        int logs = 0;

        for (final StorageItem storageItem : storage.getStorage().getItems()) {
            if (storageItem.getQuantity() == 0) continue;

            switch (storageItem.getId()) {
                case ItemId.LOGS:
                case ItemId.OAK_LOGS:
                case ItemId.WILLOW_LOGS:
                case ItemId.MAPLE_LOGS:
                case ItemId.YEW_LOGS:
                case ItemId.MAGIC_LOGS:
                case ItemId.REDWOOD_LOGS:
                case ItemId.TEAK_LOGS:
                case ItemId.MAHOGANY_LOGS:
                case ItemId.ACHEY_TREE_LOGS:
                case ItemId.ARCTIC_PINE_LOGS:
                case ItemId.JUNIPER_LOGS:
                case ItemId.BARK:
                case ItemId.BLISTERWOOD_LOGS:
                    logs += storageItem.getQuantity();
                    break;
            }
        }

        return logs;
    }

    private boolean isLogsInBasket() {
        return getLogsInBasket() > 0;
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

    private void buildBeehive() {
        final int[] logsInOrderToUse = new int[]{
            ItemId.LOGS, ItemId.ACHEY_TREE_LOGS, ItemId.OAK_LOGS, ItemId.WILLOW_LOGS,
            ItemId.TEAK_LOGS, ItemId.MAPLE_LOGS, ItemId.MAHOGANY_LOGS, ItemId.ARCTIC_PINE_LOGS,
            ItemId.YEW_LOGS, ItemId.CAMPHOR_LOGS, ItemId.IRONWOOD_LOGS, ItemId.MAGIC_LOGS,
            ItemId.REDWOOD_LOGS, ItemId.ROSEWOOD_LOGS
        };

        for (final int logsId : logsInOrderToUse) {
            if (provider.store.inventoryContainsItem(logsId)) {
                lastLogUsedFromBasketForBeehive = Optional.empty();
                return;
            } else if (storage.hasItem(logsId)) {
                storage.remove(logsId, 1);
                lastLogUsedFromBasketForBeehive = Optional.of(logsId);
                return;
            }
        }
    }
}
