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
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.Store;

public class U_HuntsmansKit extends ChargedItemWithStorage {
    public U_HuntsmansKit(
        final Client client,
        final ClientThread clientThread,
        final ConfigManager configManager,
        final ItemManager itemManager,
        final InfoBoxManager infoBoxManager,
        final ChatMessageManager chatMessageManager,
        final Notifier notifier,
        final TicTac7xChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(TicTac7xChargesImprovedConfig.huntsmans_kit, ItemID.HUNTSMANS_KIT, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.storage = storage.storableItems(
            new StorableItem(ItemID.BIRD_SNARE),
            new StorableItem(ItemID.BUTTERFLY_NET),
            new StorableItem(ItemID.BUTTERFLY_JAR),
            new StorableItem(ItemID.RABBIT_SNARE),
            new StorableItem(ItemID.SMALL_FISHING_NET),
            new StorableItem(ItemID.MAGIC_BOX),
            new StorableItem(ItemID.TEASING_STICK),
            new StorableItem(ItemID.WOOD_CAMO_TOP),
            new StorableItem(ItemID.WOOD_CAMO_LEGS),
            new StorableItem(ItemID.JUNGLE_CAMO_TOP),
            new StorableItem(ItemID.JUNGLE_CAMO_LEGS),
            new StorableItem(ItemID.LARUPIA_HAT),
            new StorableItem(ItemID.LARUPIA_TOP),
            new StorableItem(ItemID.LARUPIA_LEGS),
            new StorableItem(ItemID.KYATT_HAT),
            new StorableItem(ItemID.KYATT_TOP),
            new StorableItem(ItemID.KYATT_LEGS),
            new StorableItem(ItemID.GUILD_HUNTER_HEADWEAR),
            new StorableItem(ItemID.GUILD_HUNTER_TOP),
            new StorableItem(ItemID.GUILD_HUNTER_LEGS),
            new StorableItem(ItemID.GUILD_HUNTER_BOOTS),
            new StorableItem(ItemID.RING_OF_PURSUIT),
            new StorableItem(ItemID.NOOSE_WAND),
            new StorableItem(ItemID.MAGIC_BUTTERFLY_NET),
            new StorableItem(ItemID.BOX_TRAP),
            new StorableItem(ItemID.UNLIT_TORCH),
            new StorableItem(ItemID.ROPE),
            new StorableItem(ItemID.HUNTERS_SPEAR),
            new StorableItem(ItemID.POLAR_CAMO_TOP),
            new StorableItem(ItemID.POLAR_CAMO_LEGS),
            new StorableItem(ItemID.DESERT_CAMO_TOP),
            new StorableItem(ItemID.DESERT_CAMO_LEGS),
            new StorableItem(ItemID.GRAAHK_HEADDRESS),
            new StorableItem(ItemID.GRAAHK_TOP),
            new StorableItem(ItemID.GRAAHK_LEGS),
            new StorableItem(ItemID.HUNTER_HOOD),
            new StorableItem(ItemID.HUNTER_CAPE),
            new StorableItem(ItemID.HUNTER_CAPET),
            new StorableItem(ItemID.IMPLING_JAR)
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
