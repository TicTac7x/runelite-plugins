package tictac7x.charges.items.capes;

import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.store.ids.ItemId;
import net.runelite.api.widgets.Widget;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.ItemContainerId;
import tictac7x.charges.store.Provider;

import java.util.List;
import java.util.Optional;

import static tictac7x.charges.store.ids.ItemContainerId.INVENTORY;

public class C_ForestryKit extends ChargedItemWithStorage {
    private Optional<StorageItem> lastLeaves = Optional.empty();

    public C_ForestryKit(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.forestry_kit, ItemId.FORESTRY_KIT, provider);

        this.storage = storage.storableItems(
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
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FORESTRY_KIT),
        };

        this.triggers.addAll(List.of(
            // View contents.
            new OnItemContainerChanged(ItemContainerId.FORESTRY_KIT).updateStorage(),

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

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onItemClick().onMenuOption("Fill"),

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
            new OnMenuEntryAdded("Destroy").hide()
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
}
