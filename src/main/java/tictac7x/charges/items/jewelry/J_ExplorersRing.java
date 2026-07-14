package tictac7x.charges.items.jewelry;

import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

class ExplorersRingStorageItemId {
    public static int TELEPORTS = -1000;
    public static int ALCHEMY = -1001;
    public static int ENERGY_RESTORES = -1002;
}

public class J_ExplorersRing extends ChargedItemWithStorageMultipleCharges {
    public J_ExplorersRing(Provider provider) {
        super(TicTac7xChargesImprovedConfig.explorers_ring, ItemId.EXPLORERS_RING_1, provider);
        storage = storage.storableItems(
            new StorableItem(ExplorersRingStorageItemId.ALCHEMY).displayName("Alchemy charges"),
            new StorableItem(ExplorersRingStorageItemId.TELEPORTS).displayName("Teleports"),
            new StorableItem(ExplorersRingStorageItemId.ENERGY_RESTORES).displayName("Energy restores")
        ).showIndividualCharges();

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.EXPLORERS_RING_1),
            new TriggerItem(ItemId.EXPLORERS_RING_2),
            new TriggerItem(ItemId.EXPLORERS_RING_3),
            new TriggerItem(ItemId.EXPLORERS_RING_4),
        };

        this.triggers.addAll(List.of(
            // Use.
            new OnVarbitChanged(VarbitId.EXPLORER_RING_ALCHS).consumer(() -> updateStorage()),
            new OnVarbitChanged(VarbitId.EXPLORER_RING_RUNENERGY).consumer(() -> updateStorage()),
            new OnVarbitChanged(VarbitId.EXPLORER_RING_TELEPORTS).consumer(() -> updateStorage()),

            // Check.
            new OnMenuOptionClicked("Check").onItemClick().runConsumerOnNextGameTick(() -> updateStorage()),

            new OnResetDaily().specificItem(ItemId.EXPLORERS_RING_1).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 2);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 0);
            }),

            new OnResetDaily().specificItem(ItemId.EXPLORERS_RING_2).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 3);
            }),

            new OnResetDaily().specificItem(ItemId.EXPLORERS_RING_3).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 4);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, ChargeId.UNLIMITED);
            }),

            new OnResetDaily().specificItem(ItemId.EXPLORERS_RING_4).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, ChargeId.UNLIMITED);
            })
        ));
    }

    private void updateStorage() {
        storage.clear();

        // Alchemy.
        storage.put(ExplorersRingStorageItemId.ALCHEMY, 30 - provider.client.getVarbitValue(VarbitId.EXPLORER_RING_ALCHS));

        // Energy restores.
        int energyRestoresUsed = provider.client.getVarbitValue(VarbitId.EXPLORER_RING_RUNENERGY);
        switch (itemId) {
            case ItemId.EXPLORERS_RING_1:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 2 - energyRestoresUsed);
                break;
            case ItemId.EXPLORERS_RING_2:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3 - energyRestoresUsed);
                break;
            case ItemId.EXPLORERS_RING_3:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 4 - energyRestoresUsed);
                break;
            case ItemId.EXPLORERS_RING_4:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3 - energyRestoresUsed);
                break;
        }

        // Teleports.
        int teleportsUsed = provider.client.getVarbitValue(VarbitId.EXPLORER_RING_TELEPORTS);
        switch (itemId) {
            case ItemId.EXPLORERS_RING_1:
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 0);
                break;
            case ItemId.EXPLORERS_RING_2:
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 3 - teleportsUsed);
                break;
            case ItemId.EXPLORERS_RING_3:
            case ItemId.EXPLORERS_RING_4:
                storage.put(ExplorersRingStorageItemId.TELEPORTS, ChargeId.UNLIMITED);
                break;
        }
    }
}
