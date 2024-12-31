package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnXpDrop;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

import java.util.Optional;

import static tictac7x.charges.ChargesImprovedPlugin.getNumberFromCommaString;

public class U_BottomlessCompostBucket extends ChargedItemWithStorage {
    public U_BottomlessCompostBucket(
        final Client client,
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(ChargesImprovedConfig.bottomless_compost_bucket, ItemID.BOTTOMLESS_COMPOST_BUCKET_22997, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);
        storage = storage.setMaximumTotalQuantity(10000).storeableItems(
            new StorageItem(ItemID.ULTRACOMPOST).checkName("ultra"),
            new StorageItem(ItemID.SUPERCOMPOST).checkName("super"),
            new StorageItem(ItemID.COMPOST).checkName("regular").displayName("Regular compost")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BOTTOMLESS_COMPOST_BUCKET).fixedCharges(0),
            new TriggerItem(ItemID.BOTTOMLESS_COMPOST_BUCKET_22997),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your bottomless compost bucket is currently holding one use of (?<type>.+) ?compost.").matcherConsumer(m -> {
                storage.clearAndPut(getStorageItemFromName(m.group("type")), 1);
            }),
            new OnChatMessage("Your bottomless compost bucket is currently holding (?<quantity>.+) uses of (?<type>.+) ?compost.").matcherConsumer(m -> {
                final int quantity = getNumberFromCommaString(m.group("quantity"));
                storage.clearAndPut(getStorageItemFromName(m.group("type")), quantity);
            }),

            // Use compost on a patch.
            new OnChatMessage("Your bottomless compost bucket has a single use of (?<type>.+) ?compost remaining.").matcherConsumer(m -> {
                storage.clearAndPut(getStorageItemFromName(m.group("type")), 1);
            }),
            new OnChatMessage("Your bottomless compost bucket has (?<quantity>.+) uses of (?<type>.+) ?compost remaining.").matcherConsumer(m -> {
                final int quantity = getNumberFromCommaString(m.group("quantity"));
                storage.clearAndPut(getStorageItemFromName(m.group("type")), quantity);
            }),
            new OnChatMessage("You treat the .* with (?<type>.*) ?compost.").matcherConsumer(m -> {
                final String type = m.group("type");
                storage.remove(getStorageItemFromName(type.isEmpty() ? "regular" : type), 1);
            }).onItemClick(),

            // Discard.
            new OnChatMessage("You discard the contents of your bottomless compost bucket.").emptyStorage(),

            // Empty.
            new OnChatMessage("Your bottomless compost bucket has run out of compost!").emptyStorage(),

            // Fill.
            new OnChatMessage("You fill your bottomless compost bucket with a single bucket of (?<type>.+) ?compost. Your bottomless compost bucket now contains a total of (?<quantity>.+) uses.").matcherConsumer(m -> {
                final int quantity = getNumberFromCommaString(m.group("quantity"));
                storage.clearAndPut(getStorageItemFromName(m.group("type")), quantity);
            }),
            new OnChatMessage("You fill your bottomless compost bucket with .* buckets of (?<type>.+) ?compost. Your bottomless compost bucket now contains a total of (?<quantity>.+) uses.").matcherConsumer(m -> {
                final int quantity = getNumberFromCommaString(m.group("quantity"));
                storage.clearAndPut(getStorageItemFromName(m.group("type")), quantity);
            }),

            // Almost full.
            new OnChatMessage("Your bottomless compost bucket is just about full. You won't be able to squeeze any more compost in there.").consumer(() -> {
                storage.clearAndPut(getCompostType(), 9999);
            }),

            // Full.
            new OnChatMessage("Your bottomless compost bucket is now full!").consumer(() -> {
                storage.clearAndPut(getCompostType(), 10_000);
            }),

            // Fill compost from bin.
            new OnXpDrop(Skill.FARMING).unallowedItem(ItemID.BUCKET).onMenuOption("Take").onMenuTarget("Compost Bin", "Big Compost Bin").consumer(() -> {
                storage.add(getCompostType(), 2);
            }),

            // Use on compost bin.
            new OnXpDrop(Skill.FARMING).onMenuOption("Use").onMenuTarget("Bottomless compost bucket -> Compost Bin", "Bottomless compost bucket -> Big Compost Bin").consumer(() -> {
                storage.add(getCompostType(), 2);
            }),
        };
    }

    private Optional<StorageItem> getCompostType() {
        for (final StorageItem storageItem : getStorage().values()) {
            if (storageItem.getQuantity() > 0) {
                return Optional.of(storageItem);
            }
        }

        return Optional.empty();
    }
}
