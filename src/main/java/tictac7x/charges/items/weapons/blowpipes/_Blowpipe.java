package tictac7x.charges.items.weapons.blowpipes;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class _Blowpipe extends ChargedItemWithStorage {
    public _Blowpipe(
        String configKey,
        int itemId,
        Provider provider,
        TriggerItem[] items,
        boolean supportsAdamantiteDarts,
        boolean supportsRuniteDarts,
        int attackAnimationId
    ) {
        super(configKey, itemId, provider);
        this.items = items;

        List<StorableItem> storableItems = new ArrayList<>();
        storableItems.add(new StorableItem(ItemID.BRONZE_DART).checkName("Bronze dart"));
        storableItems.add(new StorableItem(ItemID.IRON_DART).checkName("Iron dart"));
        storableItems.add(new StorableItem(ItemID.STEEL_DART).checkName("Steel dart"));
        storableItems.add(new StorableItem(ItemID.MITHRIL_DART).checkName("Mithril dart"));
        if (supportsAdamantiteDarts) {
            storableItems.add(
                new StorableItem(ItemID.ADAMANT_DART).checkName("Adamant dart")
            );
        }
        if (supportsRuniteDarts) {
            storableItems.add(
                new StorableItem(ItemID.RUNE_DART).checkName("Rune dart")
            );
        }
        storage.storableItems(
            storableItems.toArray(new StorableItem[0])
        );

        triggers.addAll(List.of(
            // Check
            new OnChatMessage("Darts: (?<type>.+) x (?<amount>.+).").matcherConsumer(m -> {
                Optional<StorageItem> darts = getStorageItemFromName(m.group("type"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("amount")));
                storage.clearAndPut(darts);
            }).onItemClick(),

            // Attack
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.ALL).isEquipped().hasAnimationId(attackAnimationId).consumer(() -> {
                for (StorageItem item : storage.getStorage().getItems()) {
                    if (TicTac7xChargesImprovedPlugin.guessIfRangedAmmoRetrievalWasSuccessful(provider)) {
                        storage.remove(item.itemId, 1);
                    }
                }
            })
        ));
    }
}
