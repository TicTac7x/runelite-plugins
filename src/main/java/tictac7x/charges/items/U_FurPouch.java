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
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.Store;

import static tictac7x.charges.store.ItemContainerId.BANK;
import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_FurPouch extends ChargedItemWithStorage {
    public U_FurPouch(
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
        super(TicTac7xChargesImprovedConfig.fur_pouch, ItemID.SMALL_FUR_POUCH, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.storage = storage
            .storableItems(
                // Tracking.
                new StorableItem(ItemID.POLAR_KEBBIT_FUR),
                new StorableItem(ItemID.COMMON_KEBBIT_FUR),
                new StorableItem(ItemID.FELDIP_WEASEL_FUR),
                new StorableItem(ItemID.DESERT_DEVIL_FUR),

                // Deadfall.
                new StorableItem(ItemID.FOX_FUR),

                // Pitfalls.
                new StorableItem(ItemID.LARUPIA_FUR),
                new StorableItem(ItemID.GRAAHK_FUR),
                new StorableItem(ItemID.KYATT_FUR),
                new StorableItem(ItemID.SUNLIGHT_ANTELOPE_FUR),
                new StorableItem(ItemID.MOONLIGHT_ANTELOPE_FUR),

                // Aerial.
                new StorableItem(ItemID.SPOTTED_KEBBIT_FUR),
                new StorableItem(ItemID.DARK_KEBBIT_FUR),
                new StorableItem(ItemID.DASHING_KEBBIT_FUR)
            );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SMALL_FUR_POUCH).maxCharges(14),
            new TriggerItem(ItemID.SMALL_FUR_POUCH_OPEN).maxCharges(14),
            new TriggerItem(ItemID.MEDIUM_FUR_POUCH).maxCharges(21),
            new TriggerItem(ItemID.MEDIUM_FUR_POUCH_OPEN).maxCharges(21),
            new TriggerItem(ItemID.LARGE_FUR_POUCH).maxCharges(28),
            new TriggerItem(ItemID.LARGE_FUR_POUCH_OPEN).maxCharges(28),
        };

        this.triggers = new TriggerBase[]{
            // Empty.
            new OnChatMessage("Your fur pouch is currently holding 0 fur.").emptyStorage(),
            new OnChatMessage("Your fur pouch is empty.").emptyStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Empty to bank.
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption("Empty"),

            // Empty from deposit box.
            new OnMenuOptionClicked("Empty").isWidgetVisible(192, 1).emptyStorage(),

            // Use fur on pouch.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide(),

            // Tracking.
            new OnChatMessage("You manage to noose a polar kebbit that is hiding in the snowdrift.").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.POLAR_KEBBIT_FUR),
            new OnChatMessage("You manage to noose a common kebbit that is hiding in the bush.").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.COMMON_KEBBIT_FUR),
            new OnChatMessage("You manage to noose a Feldip weasel that is hiding in the bush.").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.FELDIP_WEASEL_FUR),
            new OnChatMessage("You manage to noose a desert devil that is hiding in the sand.").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.DESERT_DEVIL_FUR),

            // Deadfalls.
            new OnChatMessage("You've caught a pyre fox.").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.FOX_FUR),

            // Pitfalls.
            new OnChatMessage("You've caught a spined larupia!").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.LARUPIA_FUR),
            new OnItemContainerChanged(ItemContainerId.INVENTORY).hasChatMessage("You've caught a spined larupia!").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).onItemContainerDifference(itemsDifference -> {
                if (itemsDifference.hasItem(ItemID.TATTY_LARUPIA_FUR)) {
                    storage.remove(ItemID.LARUPIA_FUR, 1);
                }
            }),
            new OnChatMessage("You've caught a horned graahk!").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.GRAAHK_FUR),
            new OnItemContainerChanged(ItemContainerId.INVENTORY).hasChatMessage("You've caught a horned graahk!").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).onItemContainerDifference(itemsDifference -> {
                if (itemsDifference.hasItem(ItemID.TATTY_GRAAHK_FUR)) {
                    storage.remove(ItemID.GRAAHK_FUR, 1);
                }
            }),
            new OnChatMessage("You've caught a sabre-?toothed kyatt!").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.KYATT_FUR),
            new OnItemContainerChanged(ItemContainerId.INVENTORY).hasChatMessage("You've caught a sabre-?toothed kyatt!").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).onItemContainerDifference(itemsDifference -> {
                if (itemsDifference.hasItem(ItemID.TATTY_KYATT_FUR)) {
                    storage.remove(ItemID.KYATT_FUR, 1);
                }
            }),
            new OnChatMessage("You've caught a sunlight antelope!").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.SUNLIGHT_ANTELOPE_FUR),
            new OnChatMessage("You've caught a moonlight antelope!").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).addToStorage(ItemID.MOONLIGHT_ANTELOPE_FUR),

            // Aerial.
            new OnXpDrop(Skill.HUNTER, 104).hasChatMessage("You retrieve the falcon as well as the fur of the dead kebbit.").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).consumer(() -> {
                storage.add(ItemID.SPOTTED_KEBBIT_FUR, 1);
            }),
            new OnXpDrop(Skill.HUNTER, 132).hasChatMessage("You retrieve the falcon as well as the fur of the dead kebbit.").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).consumer(() -> {
                storage.add(ItemID.DARK_KEBBIT_FUR, 1);
            }),
            new OnXpDrop(Skill.HUNTER, 156).hasChatMessage("You retrieve the falcon as well as the fur of the dead kebbit.").requiredItem(ItemID.SMALL_FUR_POUCH_OPEN, ItemID.MEDIUM_FUR_POUCH_OPEN, ItemID.LARGE_FUR_POUCH_OPEN).consumer(() -> {
                storage.add(ItemID.DASHING_KEBBIT_FUR, 1);
            }),
        };
    }
}
