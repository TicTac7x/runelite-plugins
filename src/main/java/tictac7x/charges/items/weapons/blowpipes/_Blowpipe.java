package tictac7x.charges.items.weapons.blowpipes;

import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnHitsplatApplied;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.enums.HitsplatGroup;
import tictac7x.charges.store.enums.HitsplatTarget;
import tictac7x.charges.store.ids.ItemId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class _Blowpipe extends ChargedItemWithStorage {
    public _Blowpipe(
        final String configKey,
        final int itemId,
        final Provider provider,
        final TriggerItem[] items,
        final boolean supportsAdamantiteDarts,
        final int attackAnimationId
    ) {
        super(configKey, itemId, provider);
        this.items = items;

        final List<StorableItem> storableItems = new ArrayList<>();
        storableItems.add(new StorableItem(ItemId.BRONZE_DART).checkName("Bronze dart"));
        storableItems.add(new StorableItem(ItemId.IRON_DART).checkName("Iron dart"));
        storableItems.add(new StorableItem(ItemId.STEEL_DART).checkName("Steel dart"));
        storableItems.add(new StorableItem(ItemId.MITHRIL_DART).checkName("Mithril dart"));
        if (supportsAdamantiteDarts) {
            storableItems.add(
                new StorableItem(ItemId.ADAMANT_DART).checkName("Adamant dart")
            );
        }
        storage.storableItems(
            storableItems.toArray(new StorableItem[0])
        );

        triggers.addAll(List.of(
            // Check
            new OnChatMessage("Darts: (?<type>.+) x (?<amount>.+).").matcherConsumer(m -> {
                final Optional<StorageItem> darts = getStorageItemFromName(m.group("type"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("amount")));
                storage.clearAndPut(darts);
            }).onItemClick(),

            // Attack
            new OnHitsplatApplied(HitsplatTarget.ENEMY, HitsplatGroup.ALL).isEquipped().hasAnimationId(attackAnimationId).consumer(() -> {
                for (final StorageItem item : storage.getStorage().getItems()) {
                    if (TicTac7xChargesImprovedPlugin.guessIfRangedAmmoRetrievalWasSuccessful(provider)) {
                        storage.remove(item.itemId, 1);
                    }
                }
            })
        ));
    }
}
