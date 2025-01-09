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
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.OnXpDrop;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

import static tictac7x.charges.store.ItemContainerId.BANK;
import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_MeatPouch extends ChargedItemWithStorage {
    public U_MeatPouch(
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
        super(ChargesImprovedConfig.meat_pouch, ItemID.SMALL_MEAT_POUCH, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);
        this.storage = storage.storeableItems(
            // Tracking.
            new StorageItem(ItemID.RAW_BEAST_MEAT),

            // Deadfall.
            new StorageItem(ItemID.RAW_WILD_KEBBIT),
            new StorageItem(ItemID.RAW_BARBTAILED_KEBBIT),
            new StorageItem(ItemID.RAW_PYRE_FOX),

            // Pitfalls.
            new StorageItem(ItemID.RAW_LARUPIA),
            new StorageItem(ItemID.RAW_GRAAHK),
            new StorageItem(ItemID.RAW_KYATT),
            new StorageItem(ItemID.RAW_SUNLIGHT_ANTELOPE),
            new StorageItem(ItemID.RAW_MOONLIGHT_ANTELOPE),

            // Aerial.
            new StorageItem(ItemID.RAW_DASHING_KEBBIT)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SMALL_MEAT_POUCH).maxCharges(14),
            new TriggerItem(ItemID.SMALL_MEAT_POUCH_OPEN).maxCharges(14),
            new TriggerItem(ItemID.LARGE_MEAT_POUCH).maxCharges(28),
            new TriggerItem(ItemID.LARGE_MEAT_POUCH_OPEN).maxCharges(28),
        };

        this.triggers = new TriggerBase[]{
            // Empty.
            new OnChatMessage("Your meat pouch is currently holding 0 meat").emptyStorage(),
            new OnChatMessage("Your meat pouch is empty.").emptyStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption("Empty"),

            // Use meat on pouch.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStoreableItems()),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide(),

            // Tracking.
            new OnChatMessage("You manage to noose a polar kebbit that is hiding in the snowdrift.").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a common kebbit that is hiding in the bush.").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a Feldip weasel that is hiding in the bush.").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a desert devil that is hiding in the sand.").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a razor-backed kebbit that is hiding in the bush.").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_BEAST_MEAT),

            // Deadfall.
            new OnChatMessage("You've caught a wild kebbit.").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_WILD_KEBBIT),
            new OnChatMessage("You've caught a barb-tailed kebbit.").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_BARBTAILED_KEBBIT),
            new OnChatMessage("You've caught a pyre fox.").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_PYRE_FOX),

            // Pitfalls.
            new OnChatMessage("You've caught a spined larupia!").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_LARUPIA),
            new OnChatMessage("You've caught a horned graahk!").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_GRAAHK),
            new OnChatMessage("You've caught a sabre-?toothed kyatt!").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_KYATT),
            new OnChatMessage("You've caught a sunlight antelope!").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_SUNLIGHT_ANTELOPE),
            new OnChatMessage("You've caught a moonlight antelope!").requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).addToStorage(ItemID.RAW_MOONLIGHT_ANTELOPE),

            // Aerial.
            new OnXpDrop(Skill.HUNTER, 156).requiredItem(ItemID.SMALL_MEAT_POUCH_OPEN, ItemID.LARGE_MEAT_POUCH_OPEN).hasChatMessage("You retrieve the falcon as well as the fur of the dead kebbit.").consumer(() -> {
                storage.add(ItemID.RAW_DASHING_KEBBIT, 1);
            }),
        };
    }
}
