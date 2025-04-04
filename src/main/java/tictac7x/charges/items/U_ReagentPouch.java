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
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.storage.StorageItems;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;
import tictac7x.charges.store.WidgetId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_ReagentPouch extends ChargedItemWithStorage {
    public U_ReagentPouch(
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
        super(TicTac7xChargesImprovedConfig.reagent_pouch, ItemID.REAGENT_POUCH, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        storage.emptyIsNegative().setMaximumIndividualQuantity(26).storableItems(
            new StorableItem(ItemID.EYE_OF_NEWT).checkName("Eye of newt"),
            new StorableItem(ItemID.LIMPWURT_ROOT).checkName("Limpwurt root"),
            new StorableItem(ItemID.RED_SPIDERS_EGGS).checkName("Red spider's eggs", "eggs"),
            new StorableItem(ItemID.TOADS_LEGS).checkName("Toad's legs", "toad legs"),
            new StorableItem(ItemID.SNAPE_GRASS).checkName("Snape grass"),
            new StorableItem(ItemID.MORT_MYRE_FUNGUS).checkName("Mort myre fungus", "Mort Myre fungi"),
            new StorableItem(ItemID.WHITE_BERRIES).checkName("White berries"),
            new StorableItem(ItemID.JANGERBERRIES).checkName("Jangerberries"),
            new StorableItem(ItemID.POISON_IVY_BERRIES).checkName("Poison ivy berries"),
            new StorableItem(ItemID.POTATO_CACTUS).checkName("Potato cactus"),
            new StorableItem(ItemID.CACTUS_SPINE).checkName("Cactus spine"),
            new StorableItem(ItemID.ASHES).checkName("Ashes"),
            new StorableItem(ItemID.CHOCOLATE_DUST).checkName("Chocolate dust"),
            new StorableItem(ItemID.DRAGON_SCALE_DUST).checkName("Dragon scale dust", "dragon scale"),
            new StorableItem(ItemID.GOAT_HORN_DUST).checkName("Goat horn dust", "ground desert goat horn"),
            new StorableItem(ItemID.GORAK_CLAW_POWDER).checkName("Gorak claw powder"),
            new StorableItem(ItemID.KEBBIT_TEETH_DUST).checkName("Kebbit teeth dust", "ground kebbit teeth"),
            new StorableItem(ItemID.SILVER_DUST).checkName("Silver dust"),
            new StorableItem(ItemID.UNICORN_HORN_DUST).checkName("Unicorn horn dust", "unicorn horn"),
            new StorableItem(ItemID.WINE_OF_ZAMORAK).checkName("Wine of zamorak", "wine"),
            new StorableItem(ItemID.ALDARIUM).checkName("Aldarium"),
            new StorableItem(ItemID.YEW_ROOTS).checkName("Yew roots"),
            new StorableItem(ItemID.MAGIC_ROOTS).checkName("Magic roots"),
            new StorableItem(ItemID.CRUSHED_NEST).checkName("Crushed nest", "crushed bird nest"),
            new StorableItem(ItemID.CRUSHED_SUPERIOR_DRAGON_BONES).checkName("Crushed superior dragon bones"),
            new StorableItem(ItemID.NAIL_BEAST_NAILS).checkName("Nail beast nails"),
            new StorableItem(ItemID.LILY_OF_THE_SANDS).checkName("Lily of the sands"),
            new StorableItem(ItemID.CAVIAR).checkName("Caviar"),
            new StorableItem(ItemID.ROE).checkName("Roe")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.REAGENT_POUCH),
            new TriggerItem(ItemID.OPEN_REAGENT_POUCH),
        };

        this.triggers = new TriggerBase[] {
            // Check empty.
            new OnChatMessage("The Reagent pouch is empty.").emptyStorage(),

            // Check.
            new OnChatMessage("You look in your Reagent pouch and see:").emptyStorage(),
            new OnChatMessage("(?<amount>.+) x (?<item>.+)").matcherConsumer(m -> {
                final Optional<StorageItem> item = getStorageItemFromName(m.group("item"), Integer.parseInt(m.group("amount")));
                storage.put(item);
            }).hasChatMessage("You look in your Reagent pouch and see:"),

            // Pick up.
            new OnChatMessage("You put the (?<item>.+) into your Reagent pouch.").matcherConsumer(m -> {
                final Optional<StorageItem> item = getStorageItemFromName(m.group("item"), 1);
                storage.add(item);
            }),

            // Empty to bank.
            new OnChatMessage("You empty your pouch into the bank").onItemClick().emptyStorage(),

            // Empty to inventory.
            new OnItemContainerChanged(INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).onInventoryDifference(inventoryDifference -> {
                for (final StorageItem inventoryDifferenceItem : inventoryDifference.getItems()) {
                    // Item was put into the reagent pouch, but there is more in inventory, meaning that item is filled to maximum.
                    if (store.inventory.hasItem(inventoryDifferenceItem.getId())) {
                        storage.put(inventoryDifferenceItem.getId(), 26);
                    }
                }
            }).onMenuOption("Fill", TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory),

            new OnMenuOptionClicked("Fill", TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory).consumer(() -> {
                for (final StorageItem item : store.inventory.getItems()) {
                    storage.add(item);
                }
            }),

            // Replace "Use" with proper Fill/Empty option.
            new OnMenuEntryAdded("Use").replaceOptionConsumer(this::getMenuOptionForUse).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Mix potions.
            new OnChatMessage("You mix the (?<item>.+) into (your|the unfinished)( antifire)? (potion|antidote\\+\\+).*").matcherConsumer((m) -> {
                final Optional<StorageItem> item = getStorageItemFromName(m.group("item"), 1);
                storage.remove(item);
            }).requiredItem(ItemID.OPEN_REAGENT_POUCH),

            // Pick
            new OnChatMessage("You pick some whiteberries").requiredItem(ItemID.OPEN_REAGENT_POUCH).consumer(() -> {
                storage.add(ItemID.WHITE_BERRIES, 1);
            }),
        };
    }

    private String getMenuOptionForUse() {
        return storage.isStorableItemInInventory()
            ? TicTac7xChargesImprovedPlugin.menuOptionFillFromInventory
            : TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank;
    }
}
