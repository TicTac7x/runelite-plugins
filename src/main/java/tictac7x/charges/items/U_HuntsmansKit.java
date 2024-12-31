package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.Store;

public class U_HuntsmansKit extends ChargedItemWithStorage {
    public U_HuntsmansKit(
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
        super(ChargesImprovedConfig.huntsmans_kit, ItemID.HUNTSMANS_KIT, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);
        this.storage = storage.storeableItems(
            new StorageItem(ItemID.BIRD_SNARE),
            new StorageItem(ItemID.BUTTERFLY_NET),
            new StorageItem(ItemID.BUTTERFLY_JAR),
            new StorageItem(ItemID.RABBIT_SNARE),
            new StorageItem(ItemID.SMALL_FISHING_NET),
            new StorageItem(ItemID.MAGIC_BOX),
            new StorageItem(ItemID.TEASING_STICK),
            new StorageItem(ItemID.WOOD_CAMO_TOP),
            new StorageItem(ItemID.WOOD_CAMO_LEGS),
            new StorageItem(ItemID.JUNGLE_CAMO_TOP),
            new StorageItem(ItemID.JUNGLE_CAMO_LEGS),
            new StorageItem(ItemID.LARUPIA_HAT),
            new StorageItem(ItemID.LARUPIA_TOP),
            new StorageItem(ItemID.LARUPIA_LEGS),
            new StorageItem(ItemID.KYATT_HAT),
            new StorageItem(ItemID.KYATT_TOP),
            new StorageItem(ItemID.KYATT_LEGS),
            new StorageItem(ItemID.GUILD_HUNTER_HEADWEAR),
            new StorageItem(ItemID.GUILD_HUNTER_TOP),
            new StorageItem(ItemID.GUILD_HUNTER_LEGS),
            new StorageItem(ItemID.GUILD_HUNTER_BOOTS),
            new StorageItem(ItemID.RING_OF_PURSUIT),
            new StorageItem(ItemID.NOOSE_WAND),
            new StorageItem(ItemID.MAGIC_BUTTERFLY_NET),
            new StorageItem(ItemID.BOX_TRAP),
            new StorageItem(ItemID.UNLIT_TORCH),
            new StorageItem(ItemID.ROPE),
            new StorageItem(ItemID.HUNTERS_SPEAR),
            new StorageItem(ItemID.POLAR_CAMO_TOP),
            new StorageItem(ItemID.POLAR_CAMO_LEGS),
            new StorageItem(ItemID.DESERT_CAMO_TOP),
            new StorageItem(ItemID.DESERT_CAMO_LEGS),
            new StorageItem(ItemID.GRAAHK_HEADDRESS),
            new StorageItem(ItemID.GRAAHK_TOP),
            new StorageItem(ItemID.GRAAHK_LEGS),
            new StorageItem(ItemID.HUNTER_HOOD),
            new StorageItem(ItemID.HUNTER_CAPE),
            new StorageItem(ItemID.HUNTER_CAPET),
            new StorageItem(ItemID.IMPLING_JAR)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.HUNTSMANS_KIT)
        };

        this.triggers = new TriggerBase[]{
            // Fill from inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Hide destroy option.
            new OnMenuEntryAdded("Destroy").hide(),

            // Update from item container when viewing huntsmans kit contents.
            new OnItemContainerChanged(ItemContainerId.HUNTSMANS_KIT).updateStorage(),
        };
    }
}
