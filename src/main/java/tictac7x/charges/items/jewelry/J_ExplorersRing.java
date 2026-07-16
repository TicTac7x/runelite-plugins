package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

class ExplorersRingStorageItemId {
    public static int TELEPORTS = -1000;
    public static int ALCHEMY = -1001;
    public static int ENERGY_RESTORES = -1002;
}

public class J_ExplorersRing extends ChargedItemWithStorageMultipleCharges {
    public J_ExplorersRing(Provider provider) {
        super(TicTac7xChargesImprovedConfig.explorers_ring, ItemID.LUMBRIDGE_RING_EASY, provider);
        storage = storage.storableItems(
            new StorableItem(ExplorersRingStorageItemId.ALCHEMY).displayName("Alchemy charges"),
            new StorableItem(ExplorersRingStorageItemId.TELEPORTS).displayName("Teleports"),
            new StorableItem(ExplorersRingStorageItemId.ENERGY_RESTORES).displayName("Energy restores")
        ).showIndividualCharges();

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.LUMBRIDGE_RING_EASY),
            new TriggerItem(ItemID.LUMBRIDGE_RING_MEDIUM),
            new TriggerItem(ItemID.LUMBRIDGE_RING_HARD),
            new TriggerItem(ItemID.LUMBRIDGE_RING_ELITE),
        };

        this.triggers.addAll(List.of(
            // Use.
            new OnVarbitChanged(VarbitID.LUMBRIDGE_FREE_ALCHS).consumer(() -> updateStorage()),
            new OnVarbitChanged(VarbitID.LUMBRIDGE_ENERGY_RESTORE).consumer(() -> updateStorage()),
            new OnVarbitChanged(VarbitID.LUMBRIDGE_CABBAGE_TELEPORT).consumer(() -> updateStorage()),

            // Check.
            new OnMenuOptionClicked("Check").onItemClick().runConsumerOnNextGameTick(() -> updateStorage()),

            new OnResetDaily().specificItem(ItemID.LUMBRIDGE_RING_EASY).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 2);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 0);
            }),

            new OnResetDaily().specificItem(ItemID.LUMBRIDGE_RING_MEDIUM).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 3);
            }),

            new OnResetDaily().specificItem(ItemID.LUMBRIDGE_RING_HARD).consumer(() -> {
                storage.clear();
                storage.put(ExplorersRingStorageItemId.ALCHEMY, 30);
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 4);
                storage.put(ExplorersRingStorageItemId.TELEPORTS, ChargeId.UNLIMITED);
            }),

            new OnResetDaily().specificItem(ItemID.LUMBRIDGE_RING_ELITE).consumer(() -> {
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
        storage.put(ExplorersRingStorageItemId.ALCHEMY, 30 - provider.client.getVarbitValue(VarbitID.LUMBRIDGE_FREE_ALCHS));

        // Energy restores.
        int energyRestoresUsed = provider.client.getVarbitValue(VarbitID.LUMBRIDGE_ENERGY_RESTORE);
        switch (itemId) {
            case ItemID.LUMBRIDGE_RING_EASY:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 2 - energyRestoresUsed);
                break;
            case ItemID.LUMBRIDGE_RING_MEDIUM:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3 - energyRestoresUsed);
                break;
            case ItemID.LUMBRIDGE_RING_HARD:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 4 - energyRestoresUsed);
                break;
            case ItemID.LUMBRIDGE_RING_ELITE:
                storage.put(ExplorersRingStorageItemId.ENERGY_RESTORES, 3 - energyRestoresUsed);
                break;
        }

        // Teleports.
        int teleportsUsed = provider.client.getVarbitValue(VarbitID.LUMBRIDGE_CABBAGE_TELEPORT);
        switch (itemId) {
            case ItemID.LUMBRIDGE_RING_EASY:
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 0);
                break;
            case ItemID.LUMBRIDGE_RING_MEDIUM:
                storage.put(ExplorersRingStorageItemId.TELEPORTS, 3 - teleportsUsed);
                break;
            case ItemID.LUMBRIDGE_RING_HARD:
            case ItemID.LUMBRIDGE_RING_ELITE:
                storage.put(ExplorersRingStorageItemId.TELEPORTS, ChargeId.UNLIMITED);
                break;
        }
    }
}
