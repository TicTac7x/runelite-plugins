package tictac7x.charges.items.capes;

import net.runelite.api.gameval.*;
import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class C_ForestryKit extends ChargedItemWithStorage {
    public String menuOptionFillLeavesFromBank = "Fill-leaves-from-bank";
    public String menuOptionEmptyLeavesToBank = "Empty-leaves-to-bank";
    private Optional<StorageItem> lastLeaves = Optional.empty();
    private StorableItem[] storableKitItems = new StorableItem[]{
        new StorableItem(ItemID.FORESTRY_CURRENCY),
        new StorableItem(ItemID.FORESTRY_RATION),
        new StorableItem(ItemID.NATURE_OFFERINGS),
        new StorableItem(ItemID.FORESTRY_SECATEURS_ATTACHMENT),
        new StorableItem(ItemID.LEAVES).displayName("Regular leaves").checkName("regular leaves"),
        new StorableItem(ItemID.LEAVES_OAK).checkName("oak leaves"),
        new StorableItem(ItemID.LEAVES_WILLOW).checkName("willow leaves"),
        new StorableItem(ItemID.LEAVES_MAPLE).checkName("maple leaves"),
        new StorableItem(ItemID.LEAVES_YEW).checkName("yew leaves"),
        new StorableItem(ItemID.LEAVES_MAGIC).checkName("magic leaves"),
        new StorableItem(ItemID.FORESTRY_LUMBERJACK_HAT),
        new StorableItem(ItemID.FORESTRY_LUMBERJACK_TOP),
        new StorableItem(ItemID.FORESTRY_LUMBERJACK_LEGS),
        new StorableItem(ItemID.FORESTRY_LUMBERJACK_BOOTS),
        new StorableItem(ItemID.RAMBLE_LUMBERJACK_HAT),
        new StorableItem(ItemID.RAMBLE_LUMBERJACK_TOP),
        new StorableItem(ItemID.RAMBLE_LUMBERJACK_LEGS),
        new StorableItem(ItemID.RAMBLE_LUMBERJACK_BOOTS),
        new StorableItem(ItemID.SKILLCAPE_WOODCUTTING),
        new StorableItem(ItemID.SKILLCAPE_WOODCUTTING_TRIMMED)
    };

    public C_ForestryKit(String configKey, int itemId, int openItemId, Storage storage, Provider provider) {
        super(configKey, itemId, provider);

        this.storage = storage;
        setup(new TriggerItem[]{
            new TriggerItem(itemId),
            new TriggerItem(openItemId),
        });
    }

    public C_ForestryKit(Provider provider) {
        super(TicTac7xChargesImprovedConfig.forestry_kit, ItemID.FORESTRY_KIT, provider);
        setup(new TriggerItem[]{new TriggerItem(ItemID.FORESTRY_KIT)});
    }

    private void setup(TriggerItem[] items) {
        this.items = items;

        storage.addStorableItems(storableKitItems);

        this.triggers.addAll(List.of(
            // View contents.
            new OnItemContainerChanged(InventoryID.FORESTRY_KIT).itemsConsumer(kitItems -> {
                for (StorableItem storableKitItem : storableKitItems) {
                    storage.put(storableKitItem.itemId, kitItems.count(storableKitItem.itemId));
                }
            }),

            // Get leaves while chopping wood.
            new OnChatMessage("Some (?<leaves>.+ leaves) fall to the ground and you place them into your Forestry kit.").matcherConsumer(m -> {
                lastLeaves = getStorageItemFromName(m.group("leaves"), 1);
                storage.add(lastLeaves);
            }),

            // Secateurs attachment.
            new OnChatMessage("Your secateurs attachment enabled you to gather extra leaves.").runConsumerOnNextGameTick(() -> {
                storage.add(lastLeaves);
                storage.removeAndPrioritizeInventory(ItemID.FORESTRY_SECATEURS_ATTACHMENT, 1);
            }),

            // Get leaves from event.
            new OnChatMessage("You've been awarded (?<amount>.+) piles of (?<leaves>.+) leaves which you put into your Forestry kit.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("leaves"), Integer.parseInt(m.group("amount"))));
            }),

            // Get bark from an event.
            new OnChatMessage("You've been awarded (?<bark>.+) Anima-infused bark.").matcherConsumer(m -> {
                storage.add(ItemID.FORESTRY_CURRENCY, Integer.parseInt(m.group("bark")));
            }),

            // Use ration when choping.
            new OnChatMessage("You consume a Forester's ration to fuel a mighty chop.").consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemID.FORESTRY_RATION, 1);
            }),

            // Nature offering used.
            new OnChatMessage("The nature offerings enabled you to chop an extra log").consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemID.NATURE_OFFERINGS, 1);
            }),

            // Out of rations.
            new OnChatMessage("You've eaten your last Forester's ration.").consumer(() -> {
                storage.put(ItemID.FORESTRY_RATION, 0);
            }),

            // Fill from inventory.
            new OnItemContainerChanged(InventoryID.INV).fillStorageFromInventory().onItemClick().onMenuOption("Fill"),

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

            // Empty leaves to bank.
            new OnItemContainerChanged(InventoryID.BANK).emptyStorageToBank().onMenuOption(menuOptionEmptyLeavesToBank).onItemClick(),

            // Fill leaves from bank.
            new OnItemContainerChanged(InventoryID.BANK).fillStorageFromBank().onMenuOption(menuOptionFillLeavesFromBank).onItemClick(),

            // Replace "Use" with proper "Empty/Fill".
            new OnMenuEntryAdded("Use").replaceOptionConsumer(() -> getMenuOptionForUse()).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide()
        ));
    }

    public String getMenuOptionForUse() {
        return hasLeavesInForestryKit()
            ? menuOptionEmptyLeavesToBank
            : menuOptionFillLeavesFromBank;
    }

    public boolean hasLeavesInForestryKit() {
        for (StorageItem storageItem : storage.getStorage().getItems()) {
            if (storageItem.getQuantity() == 0) continue;

            switch (storageItem.itemId) {
                case ItemID.LEAVES:
                case ItemID.LEAVES_OAK:
                case ItemID.LEAVES_WILLOW:
                case ItemID.LEAVES_MAPLE:
                case ItemID.LEAVES_YEW:
                case ItemID.LEAVES_MAGIC:
                    return true;
            }
        }

        return false;
    }

    private void purchaseFromFriendlyForesterShop(int amountToBuy) {
        Optional<Widget> forestryShopWidget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, 819, 3);
        if (!forestryShopWidget.isPresent()) return;

        provider.store.addConsumerToNextTickQueue(() -> {
            int animaBarkPerItem = 0;
            int selectedShopItem = provider.client.getVarpValue(3869);
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

            storage.removeAndPrioritizeInventory(ItemID.FORESTRY_CURRENCY, animaBarkPerItem * amountToBuy);
        });
    }
}
