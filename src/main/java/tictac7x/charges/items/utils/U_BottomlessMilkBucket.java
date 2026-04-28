package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnItemUsed;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

import java.util.List;

public class U_BottomlessMilkBucket extends ChargedItem {
    public U_BottomlessMilkBucket(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bottomless_milk_bucket, ItemId.BOTTOMLESS_MILK_BUCKET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BOTTOMLESS_MILK_BUCKET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.BOTTOMLESS_MILK_BUCKET),
        };

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("Your bottomless milk bucket is currently holding (?<charges>.+) uses? of regular milk.").setDynamicallyCharges(),

            // Fill
            new OnChatMessage("You fill your bottomless milk bucket with .+ buckets? of regular milk. Your bottomless milk bucket now contains a total of (?<charges>.+) uses?.").setDynamicallyCharges(),

            // Milk
            new OnChatMessage("You milk the cow.").increaseCharges(1),

            // Churn
            new OnChatMessage("You churn your milk to make (cream|butter|cheese).").unallowedItem(ItemId.BUCKET_OF_MILK).decreaseCharges(1),

            // Make tea
            new OnItemUsed(ItemId.BOTTOMLESS_MILK_BUCKET, ItemId.BOWL_OF_NETTLE_TEA).isBothWays().unallowedItem(ItemId.BUCKET_OF_MILK).runConsumerOnNextGameTick(() -> decreaseCharges(1)),
            new OnItemUsed(ItemId.BOTTOMLESS_MILK_BUCKET, ItemId.CUP_OF_NETTLE_TEA).isBothWays().unallowedItem(ItemId.BUCKET_OF_MILK).runConsumerOnNextGameTick(() -> decreaseCharges(1)),
            new OnItemUsed(ItemId.BOTTOMLESS_MILK_BUCKET, ItemId.BOWL_OF_DAMIANA_TEA).isBothWays().unallowedItem(ItemId.BUCKET_OF_MILK).runConsumerOnNextGameTick(() -> decreaseCharges(1)),
            new OnItemUsed(ItemId.BOTTOMLESS_MILK_BUCKET, ItemId.CUP_OF_DAMIANA_TEA).isBothWays().unallowedItem(ItemId.BUCKET_OF_MILK).runConsumerOnNextGameTick(() -> decreaseCharges(1)),

            // Make uncooked cake
            new OnChatMessage("You mix the milk, flour and egg together to make a raw cake mix.").unallowedItem(ItemId.BUCKET_OF_MILK).decreaseCharges(1)
        ));
    }
}
