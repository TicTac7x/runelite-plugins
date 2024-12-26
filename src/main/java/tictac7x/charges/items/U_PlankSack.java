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
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_PlankSack extends ChargedItemWithStorage {
    public U_PlankSack(
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
        super(ChargesImprovedConfig.plank_sack, ItemID.PLANK_SACK, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);
        storage.maximumTotalQuantity(28).storeableItems(
            new StorageItem(ItemID.MAHOGANY_PLANK).checkName("Mahogany plank"),
            new StorageItem(ItemID.TEAK_PLANK).checkName("Teak plank"),
            new StorageItem(ItemID.OAK_PLANK).checkName("Oak plank"),
            new StorageItem(ItemID.PLANK).checkName("Plank")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.PLANK_SACK),
            new TriggerItem(ItemID.PLANK_SACK_25629),
        };

        this.triggers = new TriggerBase[]{
            // Empty.
            new OnChatMessage("Your sack is empty.").emptyStorage(),

            // Check.
            new OnChatMessage("Basic planks: (?<basic>.+), Oak planks: (?<oak>.+), Teak planks: (?<teak>.+), Mahogany planks: (?<mahogany>.+)").matcherConsumer(m -> {
                storage.clear();
                storage.put(ItemID.PLANK, Integer.parseInt(m.group("basic")));
                storage.put(ItemID.OAK_PLANK, Integer.parseInt(m.group("oak")));
                storage.put(ItemID.TEAK_PLANK, Integer.parseInt(m.group("teak")));
                storage.put(ItemID.MAHOGANY_PLANK, Integer.parseInt(m.group("mahogany")));
            }),

            // Empty to inventory.
            new OnItemContainerChanged(INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventoryAll().onMenuOption("Fill"),
        };
    }
}
