package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.store.ItemId;
import net.runelite.api.Skill;
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
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;
import tictac7x.charges.store.WidgetId;

import static tictac7x.charges.store.ItemContainerId.BANK;
import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_MeatPouch extends ChargedItemWithStorage {
    public U_MeatPouch(
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
        super(TicTac7xChargesImprovedConfig.meat_pouch, ItemId.MEAT_POUCH_SMALL, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.storage = storage.storableItems(
            // Tracking.
            new StorableItem(ItemId.RAW_BEAST_MEAT),

            // Deadfall.
            new StorableItem(ItemId.RAW_WILD_KEBBIT),
            new StorableItem(ItemId.RAW_BARBTAILED_KEBBIT),
            new StorableItem(ItemId.RAW_PYRE_FOX),

            // Pitfalls.
            new StorableItem(ItemId.RAW_LARUPIA),
            new StorableItem(ItemId.RAW_GRAAHK),
            new StorableItem(ItemId.RAW_KYATT),
            new StorableItem(ItemId.RAW_SUNLIGHT_ANTELOPE),
            new StorableItem(ItemId.RAW_MOONLIGHT_ANTELOPE),

            // Aerial.
            new StorableItem(ItemId.RAW_DASHING_KEBBIT)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.MEAT_POUCH_SMALL).maxCharges(14),
            new TriggerItem(ItemId.MEAT_POUCH_SMALL_OPEN).maxCharges(14),
            new TriggerItem(ItemId.MEAT_POUCH_LARGE).maxCharges(28),
            new TriggerItem(ItemId.MEAT_POUCH_LARGE_OPEN).maxCharges(28),
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
            new OnItemContainerChanged(BANK).emptyStorageToBank().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank),

            // Empty from deposit box.
            new OnMenuOptionClicked(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).onItemClick().isWidgetVisible(WidgetId.DEPOSIT_BOX).emptyStorage(),

            // Use meat on pouch.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Replace "Empty" with proper "Empty to bank".
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToBank).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide(),

            // Tracking.
            new OnChatMessage("You manage to noose a polar kebbit that is hiding in the snowdrift.").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a common kebbit that is hiding in the bush.").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a Feldip weasel that is hiding in the bush.").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a desert devil that is hiding in the sand.").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_BEAST_MEAT),
            new OnChatMessage("You manage to noose a razor-backed kebbit that is hiding in the bush.").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_BEAST_MEAT),

            // Deadfall.
            new OnChatMessage("You've caught a wild kebbit.").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_WILD_KEBBIT),
            new OnChatMessage("You've caught a barb-tailed kebbit.").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_BARBTAILED_KEBBIT),
            new OnChatMessage("You've caught a pyre fox.").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_PYRE_FOX),

            // Pitfalls.
            new OnChatMessage("You've caught a spined larupia!").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_LARUPIA),
            new OnChatMessage("You've caught a horned graahk!").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_GRAAHK),
            new OnChatMessage("You've caught a sabre-?toothed kyatt!").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_KYATT),
            new OnChatMessage("You've caught a sunlight antelope!").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_SUNLIGHT_ANTELOPE),
            new OnChatMessage("You've caught a moonlight antelope!").requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).addToStorage(ItemId.RAW_MOONLIGHT_ANTELOPE),

            // Aerial.
            new OnXpDrop(Skill.HUNTER, 156).requiredItem(ItemId.MEAT_POUCH_SMALL_OPEN, ItemId.MEAT_POUCH_LARGE_OPEN).hasChatMessage("You retrieve the falcon as well as the fur of the dead kebbit.").consumer(() -> {
                storage.add(ItemId.RAW_DASHING_KEBBIT, 1);
            }),
        };
    }
}
