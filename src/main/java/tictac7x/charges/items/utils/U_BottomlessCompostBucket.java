package tictac7x.charges.items.utils;

import net.runelite.api.*;
import net.runelite.api.widgets.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.enums.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

import java.util.*;

import static tictac7x.charges.TicTac7xChargesImprovedPlugin.*;

public class U_BottomlessCompostBucket extends ChargedItemWithStorage {
    public U_BottomlessCompostBucket(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bottomless_compost_bucket, ItemId.BOTTOMLESS_COMPOST_BUCKET, provider);
        storage = storage.setMaximumTotalQuantity(10_000).storableItems(
            new StorableItem(ItemId.ULTRACOMPOST).checkName("ultra"),
            new StorableItem(ItemId.SUPERCOMPOST).checkName("super"),
            new StorableItem(ItemId.COMPOST).checkName("regular").displayName("Regular compost")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BOTTOMLESS_COMPOST_BUCKET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.BOTTOMLESS_COMPOST_BUCKET),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your bottomless compost bucket is currently holding one use of (?<type>.+) ?compost.").matcherConsumer(m -> {
                storage.clearAndPut(getStorageItemFromName(m.group("type"), 1));
            }),
            new OnChatMessage("Your bottomless compost bucket is currently holding (?<quantity>.+) uses of (?<type>.+) ?compost.").matcherConsumer(m -> {
                int quantity = getNumberFromCommaString(m.group("quantity"));
                storage.clearAndPut(getStorageItemFromName(m.group("type"), quantity));
            }),

            // Use compost on a patch, run on next gametick, because the "You treat" message appears on same tick after this one.
            new OnChatMessage("Your bottomless compost bucket has a single use of (?<type>.+) ?compost remaining.").matcherConsumer(m -> {
                provider.store.addConsumerToNextTickQueue(() -> {
                    storage.clearAndPut(getStorageItemFromName(m.group("type"), 1));
                });
            }),
            new OnChatMessage("Your bottomless compost bucket has (?<quantity>.+) uses of (?<type>.+) ?compost remaining.").matcherConsumer(m -> {
                provider.store.addConsumerToNextTickQueue(() -> {
                    int quantity = getNumberFromCommaString(m.group("quantity"));
                    storage.clearAndPut(getStorageItemFromName(m.group("type"), quantity));
                });
            }),
            new OnChatMessage("You treat the .* with (?<type>.*) ?compost.").matcherConsumer(m -> {
                String type = m.group("type");
                storage.remove(getStorageItemFromName(type.isEmpty() ? "regular" : type, 1));
            }).onItemClick(),

            // Discard.
            new OnChatMessage("You discard the contents of your bottomless compost bucket.").emptyStorage(),

            // Empty.
            new OnChatMessage("Your bottomless compost bucket has run out of compost!").emptyStorage(),

            // Fill.
            new OnChatMessage("You fill your bottomless compost bucket with a single bucket of (?<type>.+) ?compost. Your bottomless compost bucket now contains a total of (?<quantity>.+) uses.").matcherConsumer(m -> {
                int quantity = getNumberFromCommaString(m.group("quantity"));
                storage.clearAndPut(getStorageItemFromName(m.group("type"), quantity));
            }),
            new OnChatMessage("You fill your bottomless compost bucket with .* buckets of (?<type>.+) ?compost. Your bottomless compost bucket now contains a total of (?<quantity>.+) uses.").matcherConsumer(m -> {
                int quantity = getNumberFromCommaString(m.group("quantity"));
                storage.clearAndPut(getStorageItemFromName(m.group("type"), quantity));
            }),

            // Almost full.
            new OnChatMessage("Your bottomless compost bucket is just about full. You won't be able to squeeze any more compost in there.").consumer(() -> {
                if (getCompostType().isPresent()) {
                    storage.clearAndPut(getCompostType().get().itemId, 9999);
                }
            }),

            // Full.
            new OnChatMessage("Your bottomless compost bucket is now full!").consumer(() -> {
                if (getCompostType().isPresent()) {
                    storage.clearAndPut(getCompostType().get().itemId, 10_000);
                }
            }),

            // Fill compost from bin.
            new OnXpDrop(Skill.FARMING).unallowedItem(ItemId.BUCKET).onMenuOption("Take").onMenuTarget("Compost Bin", "Big Compost Bin").consumer(() -> {
                if (getCompostType().isPresent()) {
                    storage.add(getCompostType().get().itemId, 2);
                }
            }),

            // Use on compost bin.
            new OnXpDrop(Skill.FARMING).onMenuOption("Use").onMenuTarget("Bottomless compost bucket -> Compost Bin", "Bottomless compost bucket -> Big Compost Bin").consumer(() -> {
                if (getCompostType().isPresent()) {
                    storage.add(getCompostType().get().itemId, 2);
                }
            })
        ));
    }

    private Optional<StorageItem> getCompostType() {
        for (StorageItem storageItem : getStorage().getItems()) {
            if (storageItem.getQuantity() > 0) {
                return Optional.of(storageItem);
            }
        }

        return Optional.empty();
    }
}
