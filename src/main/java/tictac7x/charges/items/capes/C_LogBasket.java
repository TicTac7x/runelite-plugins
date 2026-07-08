package tictac7x.charges.items.capes;

import tictac7x.charges.item.ChargedItemWithStorageEmptyable;
import tictac7x.charges.item.storage.Storage;
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
import tictac7x.charges.store.utils.WidgetMenuAction;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static tictac7x.charges.store.ids.ItemContainerId.BANK;
import static tictac7x.charges.store.ids.ItemContainerId.INVENTORY;

public class C_LogBasket extends ChargedItemWithStorageEmptyable {
    private Optional<StorageItem> lastLogs = Optional.empty();
    private int infernalQuantityTracker = 0;
    private Optional<Integer> lastLogUsedFromBasketForBeehive = Optional.empty();

    private final List<StorableLog> storableLogs = List.of(
        new StorableLog(ItemId.LOGS, "Logs", true).displayName("Regular logs").checkName("some logs", "x Logs"),
        new StorableLog(ItemId.ACHEY_TREE_LOGS, "Achey tree logs", true).checkName("Achey tree logs"),
        new StorableLog(ItemId.OAK_LOGS, "Oak logs", true).checkName("Oak logs"),
        new StorableLog(ItemId.WILLOW_LOGS, "Willow logs", true).checkName("Willow logs"),
        new StorableLog(ItemId.TEAK_LOGS, "Teak logs", true).checkName("Teak logs"),
        new StorableLog(ItemId.JATOBA_LOGS, "Jatoba logs", true).checkName("Jatoba logs"),
        new StorableLog(ItemId.JUNIPER_LOGS, "Juniper logs", true).checkName("Juniper logs"),
        new StorableLog(ItemId.MAPLE_LOGS, "Maple logs", true).checkName("Maple logs"),
        new StorableLog(ItemId.BARK, "Bark", true).checkName("Bark"),
        new StorableLog(ItemId.MAHOGANY_LOGS, "Mahogany logs", true).checkName("Mahogany logs"),
        new StorableLog(ItemId.ARCTIC_PINE_LOGS, "Arctic pine logs", true).checkName("Arctic pine logs"),
        new StorableLog(ItemId.YEW_LOGS, "Yew logs", true).checkName("Yew logs"),
        new StorableLog(ItemId.BLISTERWOOD_LOGS, "Blisterwood logs", true).checkName("Blisterwood logs"),
        new StorableLog(ItemId.CAMPHOR_LOGS, "Camphor logs", true).checkName("Camphor logs"),
        new StorableLog(ItemId.MAGIC_LOGS, "Magic logs", true).checkName("Magic logs"),
        new StorableLog(ItemId.IRONWOOD_LOGS, "Ironwood logs", true).checkName("Ironwood logs"),
        new StorableLog(ItemId.REDWOOD_LOGS, "Redwood logs", true).checkName("Redwood logs"),
        new StorableLog(ItemId.ROSEWOOD_LOGS, "Rosewood logs", true).checkName("Rosewood logs")
    );

    public C_LogBasket(final String configKey, final int itemId, final int openItemId, final Storage storage, final Provider provider) {
        super(configKey, itemId, provider);
        this.storage = storage;
        setup(itemId, openItemId);
    }

    public C_LogBasket(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.log_basket, ItemId.LOG_BASKET, provider);
        setup(ItemId.LOG_BASKET, ItemId.LOG_BASKET_OPEN);
    }

    public void setup(final int itemId, final int openItemId) {
        this.items = new TriggerItem[]{
            new TriggerItem(itemId),
            new TriggerItem(openItemId),
        };

        storage
            .addStorableItems(storableLogs.stream().map(storableLog -> new StorableItem(storableLog.itemId).checkName(storableLog.checkName).displayName(storableLog.displayName)).collect(Collectors.toList()))
            .setMaximumComboQuantity(storableLogs.stream().map(storableLog -> storableLog.itemId).collect(Collectors.toList()), 28);

        this.triggers.addAll(List.of(
            // Check while empty or empty to inventory or bank.
            new OnChatMessage("(Your basket is empty.|The basket is empty.|You empty your basket.|You empty your basket into the bank.)").onItemClick().consumer(() -> {
                emptyStorage();
            }),

            // Check.
            new OnChatMessage("The basket contains:").stringConsumer(s -> {
                emptyStorage();

                final Pattern pattern = Pattern.compile("(?<quantity>\\d+).x.(?<logs>.*?)(,|$)");
                final Matcher matcher = pattern.matcher(s);

                while (matcher.find()) {
                    storage.put(getStorageItemFromName(matcher.group("logs"), Integer.parseInt(matcher.group("quantity"))));
                }

                infernalQuantityTracker = getTotalCharges();
            }).onItemClick(),

            // Miscellania support.
            new OnChatMessage("You get some maple logs and give them to Lumberjack Leif.").requiredItem(openItemId).addToStorage(ItemId.MAPLE_LOGS, 0),
            new OnChatMessage("You get some teak logs and give them to Carpenter Kjallak.").requiredItem(openItemId).addToStorage(ItemId.TEAK_LOGS, 0),
            new OnChatMessage("You get some mahogany logs and give them to Carpenter Kjallak.").requiredItem(openItemId).addToStorage(ItemId.MAHOGANY_LOGS, 0),


            // Achey tree.
            new OnChatMessage("You get some logs.").onMenuTarget("Achey Tree").consumer(() -> {
                lastLogs = Optional.of(new StorageItem(ItemId.ACHEY_TREE_LOGS, 1));
                storage.add(lastLogs);
                infernalQuantityTracker++;
            }).requiredItem(openItemId),

            // Chop.
            new OnChatMessage("You get (?<logs>some .+).").matcherConsumer(m -> {
                lastLogs = getStorageItemFromName(m.group("logs"), 1);
                storage.add(lastLogs);
                infernalQuantityTracker++;
            }).requiredItem(openItemId),

            // Extra logs from nature offerings.
            new OnChatMessage("The nature offerings enabled you to chop an extra log.").requiredItem(openItemId).runConsumerOnNextGameTick(() -> {
                if (lastLogs.isPresent()) {
                    storage.add(lastLogs.get().itemId, 1);
                }
            }),

            new OnItemPickup(storage.getStorableItems()).isByOne().requiredItem(openItemId).pickUpToStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Fill").onItemClick().fillStorageFromInventory(),

            // Partially empty to inventory.
            new OnItemContainerChanged(INVENTORY).onMenuOption("Empty").onItemClick().emptyStorageToInventory(),

            // Empty from check dialog.
            new OnItemContainerChanged(INVENTORY).onWidgetMenuAction(new WidgetMenuAction("Yes", 0, "Empty the log basket into your inventory?")).emptyStorageToInventory(),

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
                    provider.store.addConsumerToNextTickQueue(() -> emptyStorage());
                }
            }),

            // Beehives.
            new OnXpDrop(Skill.WOODCUTTING).onMenuOption("Use").onMenuTarget(storableLogs.stream().map(storableLog -> storableLog.itemName).collect(Collectors.toList())).consumer(this::buildBeehive),
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
                    storage.remove(lastLogs.get().itemId, 1);
                    infernalQuantityTracker--;
                }
            }).requiredItem(openItemId)
        ));
    }

    public void emptyStorage() {
        infernalQuantityTracker = 0;
        lastLogs = Optional.empty();
        storableLogs.forEach(storableLog -> {
            storage.remove(storableLog.itemId);
        });
    }

    public boolean hasLogsInStorage() {
        for (final StorableLog storableLog : storableLogs) {
            if (storage.hasItem(storableLog.itemId)) {
                return true;
            }
        }

        return false;
    }

    private void buildBeehive() {
        for (final int logsId : storableLogs.stream().filter(storableLog -> storableLog.beehiveBuildable).map(storableLog -> storableLog.itemId).collect(Collectors.toList())) {
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

class StorableLog extends StorableItem {
    public final String itemName;
    public final boolean beehiveBuildable;

    StorableLog(final int itemId, final String itemName, final boolean beehiveBuildable) {
        super(itemId);
        this.itemName = itemName;
        this.beehiveBuildable = beehiveBuildable;
    }

    @Override
    public StorableLog displayName(String displayName) {
        super.displayName(displayName);
        return this;
    }

    @Override
    public StorableLog checkName(String... checkName) {
        super.checkName(checkName);
        return this;
    }
}