package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.store.ItemId;
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

import static tictac7x.charges.store.ItemContainerId.INVENTORY;

public class U_FlamtaerBag extends ChargedItemWithStorage {
//    private boolean flamtaerBagEmptyDialogVisible = false;

    public U_FlamtaerBag(
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
        super(TicTac7xChargesImprovedConfig.flamtaer_bag, ItemId.FLAMTAER_BAG, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        storage.storableItems(
            new StorableItem(ItemId.TIMBER_BEAM).specificOrder(1),
            new StorableItem(ItemId.LIMESTONE_BRICK).specificOrder(2),
            new StorableItem(ItemId.SWAMP_PASTE).specificOrder(3)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FLAMTAER_BAG),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Timber beams: (?<beams>.+) Limestone bricks: (?<bricks>.+) Swamp paste: (?<paste>.+)").matcherConsumer(m -> {
                storage.clear();
                storage.put(ItemId.TIMBER_BEAM, Integer.parseInt(m.group("beams")));
                storage.put(ItemId.LIMESTONE_BRICK, Integer.parseInt(m.group("bricks")));
                storage.put(ItemId.SWAMP_PASTE, Integer.parseInt(m.group("paste")));
            }),

            // Repaired.
            new OnChatMessage("Your temple repair resource pool is full").consumer(() -> {
                storage.removeAndPrioritizeInventory(ItemId.TIMBER_BEAM, 1);
                storage.removeAndPrioritizeInventory(ItemId.LIMESTONE_BRICK, 1);
                storage.removeAndPrioritizeInventory(ItemId.SWAMP_PASTE, 5);
            }),

            // Replace "Empty" with proper "Empty to inventory" at bank.
            new OnMenuEntryAdded("Empty").replaceOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory).isWidgetVisible(WidgetId.BANK, WidgetId.DEPOSIT_BOX),

            // Fill from inventory.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory at bank.
            new OnItemContainerChanged(INVENTORY).emptyStorageToInventory().onMenuOption(TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory),

            // Use storable items on flamtaer bag.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Use flamtaer bag on storable item.
            new OnItemContainerChanged(INVENTORY).fillStorageFromInventory().onUseChargedItemOnStorageItem(storage.getStorableItems()),

//            // Flamtaer empty widget appeared.
//            new OnWidgetLoaded(219, 1).widgetConsumer(widget -> {
//                final Optional<Widget> emptyEverything = Optional.ofNullable(widget.getChild(1));
//                final Optional<Widget> emptyFirstOption = Optional.ofNullable(widget.getChild(2));
//
//                flamtaerBagEmptyDialogVisible = (
//                    emptyEverything.isPresent() && emptyEverything.get().getText().equals("Everything") &&
//                    emptyFirstOption.isPresent() && (
//                        emptyFirstOption.get().getText().contains("Timber beams") ||
//                        emptyFirstOption.get().getText().contains("Limestone bricks") ||
//                        emptyFirstOption.get().getText().contains("Swamp paste")
//                    )
//                );
//            }),
//            // TODO - figure out how to detect which option was chosen from empty dialog

            // Trying to empty already empty bag.
            new OnChatMessage("The bag is empty").onMenuOption("Empty", TicTac7xChargesImprovedPlugin.menuOptionEmptyToInventory).onItemClick().emptyStorage(),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }
}
