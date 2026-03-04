package tictac7x.charges.items.capes;

import tictac7x.charges.item.ChargedItemWithStorageEmptyable;
import tictac7x.charges.store.ids.ItemId;
import net.runelite.api.Skill;
import net.runelite.api.widgets.Widget;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.WidgetId;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tictac7x.charges.store.ids.ItemContainerId.BANK;
import static tictac7x.charges.store.ids.ItemContainerId.INVENTORY;

public class C_LogBasket extends ChargedItemWithStorageEmptyable {
    private Optional<StorageItem> lastLogs = Optional.empty();
    private int infernalQuantityTracker = 0;
    private Optional<Integer> lastLogUsedFromBasketForBeehive = Optional.empty();

    public C_LogBasket(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.log_basket, ItemId.LOG_BASKET, provider);
        storage.setMaximumTotalQuantity(28).storableItems(
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
            new StorableItem(ItemId.ROSEWOOD_LOGS).checkName("Rosewood logs")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.LOG_BASKET),
            new TriggerItem(ItemId.LOG_BASKET_OPEN),
        };

        this.triggers.addAll(List.of(
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
                    storage.put(getStorageItemFromName(matcher.group("logs"), Integer.parseInt(matcher.group("quantity"))));
                }

                infernalQuantityTracker = getTotalCharges();
            }).onItemClick(),

            // Miscellania support.
            new OnChatMessage("You get some maple logs and give them to Lumberjack Leif.").requiredItem(ItemId.LOG_BASKET_OPEN).addToStorage(ItemId.MAPLE_LOGS, 0),
            new OnChatMessage("You get some teak logs and give them to Carpenter Kjallak.").requiredItem(ItemId.LOG_BASKET_OPEN).addToStorage(ItemId.TEAK_LOGS, 0),
            new OnChatMessage("You get some mahogany logs and give them to Carpenter Kjallak.").requiredItem(ItemId.LOG_BASKET_OPEN).addToStorage(ItemId.MAHOGANY_LOGS, 0),
            

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
            }).requiredItem(ItemId.LOG_BASKET_OPEN),

            // Extra logs from nature offerings.
            new OnChatMessage("The nature offerings enabled you to chop an extra log.").requiredItem(ItemId.LOG_BASKET_OPEN).runConsumerOnNextGameTick(() -> {
                if (lastLogs.isPresent()) {
                    storage.add(lastLogs.get().getId(), 1);
                }
            }),

            new OnItemPickup(storage.getStorableItems()).isByOne().requiredItem(ItemId.LOG_BASKET_OPEN).pickUpToStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Fill").onItemClick().fillStorageFromInventory(),

            // Fully empty to inventory.
            new OnChatMessage("You empty your basket.").emptyStorage(),

            // Partially empty to inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Empty").onItemClick().emptyStorageToInventory(),

            // Partially empty to inventory from check dialog.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Continue").hasChatMessage("You empty as many logs as you can carry.").emptyStorageToInventory(),

            // Use log on basket.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption("Empty", TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank),

            // Leprechaun.
            new OnMenuOptionClicked("Continue").consumer(() -> {
                final Optional<Widget> bankWoodcuttingResourcesWidget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 219, 1, 2);
                if (bankWoodcuttingResourcesWidget.isPresent() && bankWoodcuttingResourcesWidget.get().getText().equals("Only bank woodcutting resources")) {
                    provider.store.addConsumerToNextTickQueue(() -> storage.clear());
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

            // Replace "Empty" with proper Empty to bank option.
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),

            // Infernal axe support.
            new OnXpDrop(Skill.FIREMAKING).onMenuOption("Chop down", "Cut").consumer(() -> {
                if (infernalQuantityTracker < 29 && lastLogs.isPresent()) {
                    storage.remove(lastLogs.get().getId(), 1);
                    infernalQuantityTracker--;
                }
            }).requiredItem(ItemId.LOG_BASKET_OPEN)
        ));
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
