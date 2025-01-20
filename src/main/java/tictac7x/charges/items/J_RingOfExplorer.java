package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Varbits;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorageMultipleCharges;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Store;

class ExplorersRingStorageItemId {
    public static final int TELEPORTS = -1000;
    public static final int ALCHEMY = -1001;
    public static final int ENERGY_RESTORES = -1002;
}

public class J_RingOfExplorer extends ChargedItemWithStorageMultipleCharges {
    public J_RingOfExplorer(
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
        super(TicTac7xChargesImprovedConfig.explorers_ring, ItemID.EXPLORERS_RING_1, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        storage = storage.storableItems(
            new StorableItem(ExplorersRingStorageItemId.ALCHEMY).displayName("Alchemy charges"),
            new StorableItem(ExplorersRingStorageItemId.TELEPORTS).displayName("Teleports"),
            new StorableItem(ExplorersRingStorageItemId.ENERGY_RESTORES).displayName("Energy restores")
        ).showIndividualCharges();

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.EXPLORERS_RING_1),
            new TriggerItem(ItemID.EXPLORERS_RING_2),
            new TriggerItem(ItemID.EXPLORERS_RING_3),
            new TriggerItem(ItemID.EXPLORERS_RING_4),
        };

        this.triggers = new TriggerBase[]{
            // Use.
            new OnVarbitChanged(Varbits.EXPLORER_RING_ALCHS).consumer(() -> updateStorage()),
            new OnVarbitChanged(Varbits.EXPLORER_RING_RUNENERGY).consumer(() -> updateStorage()),
            new OnVarbitChanged(Varbits.EXPLORER_RING_TELEPORTS).consumer(() -> updateStorage()),

            // Check.
            new OnMenuOptionClicked("Check").onItemClick().consumer(() -> updateStorage()),

            new OnResetDaily().specificItem(ItemID.EXPLORERS_RING_1).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 2);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 0);
            }),

            new OnResetDaily().specificItem(ItemID.EXPLORERS_RING_2).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 3);
            }),

            new OnResetDaily().specificItem(ItemID.EXPLORERS_RING_3).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 4);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, Charges.UNLIMITED);
            }),

            new OnResetDaily().specificItem(ItemID.EXPLORERS_RING_4).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, Charges.UNLIMITED);
            }),
        };
    }

    private void updateStorage() {
        storage.clear();

        // Alchemy.
        storage.put(ExplorersRingStorageItemId.ALCHEMY, 30 - client.getVarbitValue(Varbits.EXPLORER_RING_ALCHS));

        // Energy restores.
        final int energyRestoresUsed = client.getVarbitValue(Varbits.EXPLORER_RING_RUNENERGY);
        switch (itemId) {
            case ItemID.EXPLORERS_RING_1:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 2 - energyRestoresUsed);
                break;
            case ItemID.EXPLORERS_RING_2:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3 - energyRestoresUsed);
                break;
            case ItemID.EXPLORERS_RING_3:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 4 - energyRestoresUsed);
                break;
            case ItemID.EXPLORERS_RING_4:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3 - energyRestoresUsed);
                break;
        }

        // Teleports.
        final int teleportsUsed = client.getVarbitValue(Varbits.EXPLORER_RING_TELEPORTS);
        switch (itemId) {
            case ItemID.EXPLORERS_RING_1:
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 0);
                break;
            case ItemID.EXPLORERS_RING_2:
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 3 - teleportsUsed);
                break;
            case ItemID.EXPLORERS_RING_3:
            case ItemID.EXPLORERS_RING_4:
                storage.put(ExplorersRingStorageItemId.TELEPORTS, Charges.UNLIMITED);
                break;
        }
    }
}
