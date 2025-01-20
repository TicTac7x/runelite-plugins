package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.Store;

import java.util.Optional;

import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class C_ForestryKit extends ChargedItemWithStorage {
    public C_ForestryKit(
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
        super(TicTac7xChargesImprovedConfig.forestry_kit, ItemID.FORESTRY_KIT, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);

        this.storage = storage.storableItems(
            new StorableItem(ItemID.ANIMAINFUSED_BARK).specificOrder(1),
            new StorableItem(ItemID.FORESTERS_RATION).specificOrder(2),
            new StorableItem(ItemID.NATURE_OFFERINGS).specificOrder(3),
            new StorableItem(ItemID.SECATEURS_ATTACHMENT).specificOrder(4),
            new StorableItem(ItemID.LEAVES).displayName("Regular leaves").checkName("regular").specificOrder(5),
            new StorableItem(ItemID.OAK_LEAVES).checkName("oak").specificOrder(6),
            new StorableItem(ItemID.WILLOW_LEAVES).checkName("willow").specificOrder(7),
            new StorableItem(ItemID.MAPLE_LEAVES).checkName("maple").specificOrder(8),
            new StorableItem(ItemID.YEW_LEAVES).checkName("yew").specificOrder(9),
            new StorableItem(ItemID.MAGIC_LEAVES).checkName("magic").specificOrder(10),
            new StorableItem(ItemID.FORESTRY_HAT).specificOrder(11),
            new StorableItem(ItemID.FORESTRY_TOP).specificOrder(12),
            new StorableItem(ItemID.FORESTRY_LEGS).specificOrder(13),
            new StorableItem(ItemID.FORESTRY_BOOTS).specificOrder(14),
            new StorableItem(ItemID.LUMBERJACK_HAT).specificOrder(15),
            new StorableItem(ItemID.LUMBERJACK_TOP).specificOrder(16),
            new StorableItem(ItemID.LUMBERJACK_LEGS).specificOrder(17),
            new StorableItem(ItemID.LUMBERJACK_BOOTS).specificOrder(18),
            new StorableItem(ItemID.WOODCUTTING_CAPE).specificOrder(19),
            new StorableItem(ItemID.WOODCUT_CAPET).specificOrder(20)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FORESTRY_KIT),
        };

        this.triggers = new TriggerBase[]{
            // View contents.
            new OnItemContainerChanged(ItemContainerId.FORESTRY_KIT).updateStorage(),

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
                storage.remove(ItemID.FORESTERS_RATION, 1);
            }),

            // Out of rations.
            new OnChatMessage("You've eaten your last Forester's ration.").consumer(() -> {
                storage.put(ItemID.FORESTERS_RATION, 0);
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
            new OnMenuEntryAdded("Destroy").hide(),
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
}
