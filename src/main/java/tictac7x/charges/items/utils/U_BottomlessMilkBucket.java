package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

import java.util.*;

public class U_BottomlessMilkBucket extends ChargedItemWithStorage {
    public U_BottomlessMilkBucket(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bottomless_milk_bucket, ItemID.BOTTOMLESS_MILK_BUCKET_FILLED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BOTTOMLESS_MILK_BUCKET).fixedCharges(0),
            new TriggerItem(ItemID.BOTTOMLESS_MILK_BUCKET_FILLED),
        };

        this.storage.storableItems(
            new StorableItem (ItemID.BUCKET_MILK).checkName("regular milk"),
            new StorableItem (ItemID.CHOCOLATY_MILK).checkName("chocolatey milk"),
            new StorableItem (ItemID.CHICKENQUEST_MILKY_MIXTURE).checkName("creamy milk"),
            new StorableItem (ItemID.HANGOVER_CURE).checkName("hangover cure")
        );

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("Your bottomless milk bucket is currently holding (?<charges>.+) uses? of (?<type>.+).").matcherConsumer(m -> {
                Optional<StorageItem> storageItem = getStorageItemFromName(m.group("type"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
                storage.clearAndPut(storageItem);
            }),

            // Auto uses left message
            new OnChatMessage("Your bottomless milk bucket has (?<charges>.+) uses? of (?<type>.+) remaining.").matcherConsumer(m -> {
                Optional<StorageItem> storageItem = getStorageItemFromName(m.group("type"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
                storage.clearAndPut(storageItem);
            }),

            // Fill
            new OnChatMessage("You fill your bottomless milk bucket with .+ buckets? of (?<type>.+). Your bottomless milk bucket now contains a total of (?<charges>.+) uses?.").matcherConsumer(m -> {
                Optional<StorageItem> storageItem = getStorageItemFromName(m.group("type"), TicTac7xChargesImprovedPlugin.getNumberFromCommaString(m.group("charges")));
                storage.clearAndPut(storageItem);
            }),

            // Empty
            new OnChatMessage("You discard the contents of your bottomless milk bucket.").emptyStorage(),

            // Milk
            new OnChatMessage("You milk the cow.").consumer(() -> {
                if (storage.hasItem (ItemID.BUCKET_MILK)) {
                    storage.add(ItemID.BUCKET_MILK, 1);
                }
            }),

            // Churn
            new OnChatMessage("You churn your milk to make (cream|butter|cheese).").unallowedItem (ItemID.BUCKET_MILK).consumer(() -> {
                if (storage.hasItem (ItemID.BUCKET_MILK)) {
                    storage.remove (ItemID.BUCKET_MILK, 1);
                }
            }),

            // Make tea
            new OnItemUsed (ItemID.BOTTOMLESS_MILK_BUCKET_FILLED, ItemID.BOWL_NETTLETEA).isBothWays().unallowedItem (ItemID.BUCKET_MILK).runConsumerOnNextGameTick(() -> {
                if (storage.hasItem (ItemID.BUCKET_MILK)) {
                    storage.remove (ItemID.BUCKET_MILK, 1);
                }
            }),
            new OnItemUsed (ItemID.BOTTOMLESS_MILK_BUCKET_FILLED, ItemID.CUP_OF_NETTLETEA).isBothWays().unallowedItem (ItemID.BUCKET_MILK).runConsumerOnNextGameTick(() -> {
                if (storage.hasItem (ItemID.BUCKET_MILK)) {
                    storage.remove (ItemID.BUCKET_MILK, 1);
                }
            }),
            new OnItemUsed (ItemID.BOTTOMLESS_MILK_BUCKET_FILLED, ItemID.BOWL_DAMIANA_TEA).isBothWays().unallowedItem (ItemID.BUCKET_MILK).runConsumerOnNextGameTick(() -> {
                if (storage.hasItem (ItemID.BUCKET_MILK)) {
                    storage.remove (ItemID.BUCKET_MILK, 1);
                }
            }),
            new OnItemUsed (ItemID.BOTTOMLESS_MILK_BUCKET_FILLED, ItemID.CUP_DAMIANA_TEA).isBothWays().unallowedItem (ItemID.BUCKET_MILK).runConsumerOnNextGameTick(() -> {
                if (storage.hasItem (ItemID.BUCKET_MILK)) {
                    storage.remove (ItemID.BUCKET_MILK, 1);
                }
            }),

            // Make uncooked cake
            new OnChatMessage("You mix the milk, flour and egg together to make a raw cake mix.").unallowedItem (ItemID.BUCKET_MILK).consumer(() -> {
                if (storage.hasItem (ItemID.BUCKET_MILK)) {
                    storage.remove (ItemID.BUCKET_MILK, 1);
                }
            }),

            // Make chocolatey milk
            new OnChatMessage("You mix the chocolate into the bucket.").unallowedItem (ItemID.BUCKET_MILK).consumer(() -> {
                if (storage.hasItem (ItemID.BUCKET_MILK)) {
                    storage.remove (ItemID.BUCKET_MILK, 1);
                }
            }),

            // Make hangover cure
            new OnChatMessage("You mix the snape grass into the bucket.").unallowedItem (ItemID.CHOCOLATY_MILK).consumer(() -> {
                if (storage.hasItem (ItemID.CHOCOLATY_MILK)) {
                    storage.remove (ItemID.CHOCOLATY_MILK, 1);
                }
            }),

            // Feed milk to cat
            new OnChatMessage(".+ laps up the milk").unallowedItem (ItemID.BUCKET_MILK).consumer(() -> {
                if (storage.hasItem (ItemID.BUCKET_MILK)) {
                    storage.remove (ItemID.BUCKET_MILK, 1);
                }
            })
        ));
    }
}
