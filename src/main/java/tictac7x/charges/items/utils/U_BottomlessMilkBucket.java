package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnItemUsed;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

import java.util.List;
import java.util.Optional;

public class U_BottomlessMilkBucket extends ChargedItemWithStorage {
    public U_BottomlessMilkBucket(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bottomless_milk_bucket, ItemId.BOTTOMLESS_MILK_BUCKET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BOTTOMLESS_MILK_BUCKET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.BOTTOMLESS_MILK_BUCKET),
        };

        this.storage.storableItems(
            new StorableItem(ItemId.BUCKET_OF_MILK).checkName("regular milk"),
            new StorableItem(ItemId.BUCKET_OF_CHOCOLATEY_MILK).checkName("chocolatey milk"),
            new StorableItem(ItemId.BUCKET_OF_MILKY_MIXTURE).checkName("creamy milk"),
            new StorableItem(ItemId.BUCKET_OF_HANGOVER_CURE).checkName("hangover cure")
        );

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("Your bottomless milk bucket is currently holding (?<charges>.+) uses? of (?<type>.+).").matcherConsumer(m -> {
                final Optional<StorageItem> storageItem = getStorageItemFromName(m.group("type"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
                storage.clearAndPut(storageItem);
            }),

            // Auto uses left message
            new OnChatMessage("Your bottomless milk bucket has (?<charges>.+) uses? of (?<type>.+) remaining.").matcherConsumer(m -> {
                final Optional<StorageItem> storageItem = getStorageItemFromName(m.group("type"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
                storage.clearAndPut(storageItem);
            }),

            // Fill
            new OnChatMessage("You fill your bottomless milk bucket with .+ buckets? of (?<type>.+). Your bottomless milk bucket now contains a total of (?<charges>.+) uses?.").matcherConsumer(m -> {
                final Optional<StorageItem> storageItem = getStorageItemFromName(m.group("type"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
                storage.clearAndPut(storageItem);
            }),

            // Empty
            new OnChatMessage("You discard the contents of your bottomless milk bucket.").emptyStorage(),

            // Milk
            new OnChatMessage("You milk the cow.").consumer(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_MILK)) {
                    storage.add(ItemId.BUCKET_OF_MILK, 1);
                }
            }),

            // Churn
            new OnChatMessage("You churn your milk to make (cream|butter|cheese).").unallowedItem(ItemId.BUCKET_OF_MILK).consumer(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_MILK)) {
                    storage.remove(ItemId.BUCKET_OF_MILK, 1);
                }
            }),

            // Make tea
            new OnItemUsed(ItemId.BOTTOMLESS_MILK_BUCKET, ItemId.BOWL_OF_NETTLE_TEA).isBothWays().unallowedItem(ItemId.BUCKET_OF_MILK).runConsumerOnNextGameTick(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_MILK)) {
                    storage.remove(ItemId.BUCKET_OF_MILK, 1);
                }
            }),
            new OnItemUsed(ItemId.BOTTOMLESS_MILK_BUCKET, ItemId.CUP_OF_NETTLE_TEA).isBothWays().unallowedItem(ItemId.BUCKET_OF_MILK).runConsumerOnNextGameTick(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_MILK)) {
                    storage.remove(ItemId.BUCKET_OF_MILK, 1);
                }
            }),
            new OnItemUsed(ItemId.BOTTOMLESS_MILK_BUCKET, ItemId.BOWL_OF_DAMIANA_TEA).isBothWays().unallowedItem(ItemId.BUCKET_OF_MILK).runConsumerOnNextGameTick(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_MILK)) {
                    storage.remove(ItemId.BUCKET_OF_MILK, 1);
                }
            }),
            new OnItemUsed(ItemId.BOTTOMLESS_MILK_BUCKET, ItemId.CUP_OF_DAMIANA_TEA).isBothWays().unallowedItem(ItemId.BUCKET_OF_MILK).runConsumerOnNextGameTick(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_MILK)) {
                    storage.remove(ItemId.BUCKET_OF_MILK, 1);
                }
            }),

            // Make uncooked cake
            new OnChatMessage("You mix the milk, flour and egg together to make a raw cake mix.").unallowedItem(ItemId.BUCKET_OF_MILK).consumer(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_MILK)) {
                    storage.remove(ItemId.BUCKET_OF_MILK, 1);
                }
            }),

            // Make chocolatey milk
            new OnChatMessage("You mix the chocolate into the bucket.").unallowedItem(ItemId.BUCKET_OF_MILK).consumer(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_MILK)) {
                    storage.remove(ItemId.BUCKET_OF_MILK, 1);
                }
            }),

            // Make hangover cure
            new OnChatMessage("You mix the snape grass into the bucket.").unallowedItem(ItemId.BUCKET_OF_CHOCOLATEY_MILK).consumer(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_CHOCOLATEY_MILK)) {
                    storage.remove(ItemId.BUCKET_OF_CHOCOLATEY_MILK, 1);
                }
            }),

            // Feed milk to cat
            new OnChatMessage(".+ laps up the milk").unallowedItem(ItemId.BUCKET_OF_MILK).consumer(() -> {
                if (storage.hasItem(ItemId.BUCKET_OF_MILK)) {
                    storage.remove(ItemId.BUCKET_OF_MILK, 1);
                }
            })
        ));
    }
}
