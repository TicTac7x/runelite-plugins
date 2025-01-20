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
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.Store;

public class C_Coffin extends ChargedItemWithStorage {
    public C_Coffin(
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
        super(TicTac7xChargesImprovedConfig.coffin, ItemID.GOLD_COFFIN, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.storage = storage.storableItems(
            new StorableItem(ItemID.LOAR_REMAINS).checkName("Loar"),
            new StorableItem(ItemID.PHRIN_REMAINS).checkName("Phrin"),
            new StorableItem(ItemID.RIYL_REMAINS).checkName("Riyl"),
            new StorableItem(ItemID.ASYN_REMAINS).checkName("Asyn"),
            new StorableItem(ItemID.FIYR_REMAINS).checkName("Fiyr"),
            new StorableItem(ItemID.URIUM_REMAINS).checkName("Urium")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BROKEN_COFFIN).fixedCharges(0),
            new TriggerItem(ItemID.BRONZE_COFFIN).maxCharges(3),
            new TriggerItem(ItemID.OPEN_BRONZE_COFFIN).maxCharges(3),
            new TriggerItem(ItemID.STEEL_COFFIN).maxCharges(8),
            new TriggerItem(ItemID.OPEN_STEEL_COFFIN).maxCharges(8),
            new TriggerItem(ItemID.BLACK_COFFIN).maxCharges(14),
            new TriggerItem(ItemID.OPEN_BLACK_COFFIN).maxCharges(14),
            new TriggerItem(ItemID.SILVER_COFFIN).maxCharges(20),
            new TriggerItem(ItemID.OPEN_SILVER_COFFIN).maxCharges(20),
            new TriggerItem(ItemID.GOLD_COFFIN).maxCharges(28),
            new TriggerItem(ItemID.OPEN_GOLD_COFFIN).maxCharges(28),
        };

        this.triggers = new TriggerBase[] {
            // Add remains to coffin.
            new OnChatMessage("You put the (?<remains>.+) remains into your open coffin.").matcherConsumer(m -> {
                storage.add(getStorageItemFromName(m.group("remains")), 1);
            }),

            // Check.
            new OnChatMessage("Loar (?<loar>.+) / Phrin (?<phrin>.+) / Riyl (?<riyl>.+) / Asyn (?<asyn>.+) / Fiyr (?<fiyr>.+) / Urium (?<urium>.+)").matcherConsumer(m -> {
                storage.clear();
                storage.put(ItemID.LOAR_REMAINS, Integer.parseInt(m.group("loar")));
                storage.put(ItemID.PHRIN_REMAINS, Integer.parseInt(m.group("phrin")));
                storage.put(ItemID.RIYL_REMAINS, Integer.parseInt(m.group("riyl")));
                storage.put(ItemID.ASYN_REMAINS, Integer.parseInt(m.group("asyn")));
                storage.put(ItemID.FIYR_REMAINS, Integer.parseInt(m.group("fiyr")));
                storage.put(ItemID.URIUM_REMAINS, Integer.parseInt(m.group("urium")));
            }),

            // Try to empty already empty.
            new OnChatMessage("Your coffin is empty.").onItemClick().emptyStorage(),

            // Fill from inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),
        };
    }
}
