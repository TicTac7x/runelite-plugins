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
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.ItemContainerId;
import tictac7x.charges.store.Store;

public class U_TackleBox extends ChargedItemWithStorage {
    public U_TackleBox(
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
        super(TicTac7xChargesImprovedConfig.tackle_box, ItemID.TACKLE_BOX, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        this.storage = storage.storableItems(
            new StorableItem(ItemID.ANGLER_HAT),
            new StorableItem(ItemID.ANGLER_TOP),
            new StorableItem(ItemID.ANGLER_WADERS),
            new StorableItem(ItemID.ANGLER_BOOTS),
            new StorableItem(ItemID.SPIRIT_ANGLER_HEADBAND),
            new StorableItem(ItemID.SPIRIT_ANGLER_TOP),
            new StorableItem(ItemID.SPIRIT_ANGLER_WADERS),
            new StorableItem(ItemID.SPIRIT_ANGLER_BOOTS),
            new StorableItem(ItemID.SPIRIT_FLAKES),
            new StorableItem(ItemID.FISHBOWL_HELMET),
            new StorableItem(ItemID.FLIPPERS),
            new StorableItem(ItemID.DARK_FLIPPERS),
            new StorableItem(ItemID.DIVING_APPARATUS),
            new StorableItem(ItemID.TINY_NET),
            new StorableItem(ItemID.RADAS_BLESSING_1),
            new StorableItem(ItemID.RADAS_BLESSING_2),
            new StorableItem(ItemID.RADAS_BLESSING_3),
            new StorableItem(ItemID.RADAS_BLESSING_4),
            new StorableItem(ItemID.HARPOON),
            new StorableItem(ItemID.BARBTAIL_HARPOON),
            new StorableItem(ItemID.DRAGON_HARPOON),
            new StorableItem(ItemID.DRAGON_HARPOON_OR),
            new StorableItem(ItemID.DRAGON_HARPOON_OR_30349),
            new StorableItem(ItemID.INFERNAL_HARPOON),
            new StorableItem(ItemID.INFERNAL_HARPOON_UNCHARGED),
            new StorableItem(ItemID.INFERNAL_HARPOON_UNCHARGED_25367),
            new StorableItem(ItemID.INFERNAL_HARPOON_UNCHARGED_30343),
            new StorableItem(ItemID.INFERNAL_HARPOON_OR),
            new StorableItem(ItemID.INFERNAL_HARPOON_OR_30342),
            new StorableItem(ItemID.CRYSTAL_HARPOON),
            new StorableItem(ItemID.CRYSTAL_HARPOON_23864),
            new StorableItem(ItemID.CRYSTAL_HARPOON_INACTIVE),
            new StorableItem(ItemID.MERFOLK_TRIDENT),
            new StorableItem(ItemID.FISHING_ROD),
            new StorableItem(ItemID.PEARL_FISHING_ROD),
            new StorableItem(ItemID.FLY_FISHING_ROD),
            new StorableItem(ItemID.PEARL_FLY_FISHING_ROD),
            new StorableItem(ItemID.OILY_FISHING_ROD),
            new StorableItem(ItemID.OILY_PEARL_FISHING_ROD),
            new StorableItem(ItemID.BARBARIAN_ROD),
            new StorableItem(ItemID.PEARL_BARBARIAN_ROD),
            new StorableItem(ItemID.SMALL_FISHING_NET),
            new StorableItem(ItemID.BIG_FISHING_NET),
            new StorableItem(ItemID.DRIFT_NET),
            new StorableItem(ItemID.LOBSTER_POT),
            new StorableItem(ItemID.KARAMBWAN_VESSEL),
            new StorableItem(ItemID.KARAMBWAN_VESSEL_3159),
            new StorableItem(ItemID.RAW_KARAMBWANJI),
            new StorableItem(ItemID.FISHING_BAIT),
            new StorableItem(ItemID.FEATHER),
            new StorableItem(ItemID.DARK_FISHING_BAIT),
            new StorableItem(ItemID.SANDWORMS),
            new StorableItem(ItemID.FISH_OFFCUTS),
            new StorableItem(ItemID.FISH_CHUNKS),
            new StorableItem(ItemID.FISHING_POTION1),
            new StorableItem(ItemID.FISHING_POTION2),
            new StorableItem(ItemID.FISHING_POTION3),
            new StorableItem(ItemID.FISHING_POTION4),
            new StorableItem(ItemID.MOLCH_PEARL),
            new StorableItem(ItemID.STRIPY_FEATHER)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.TACKLE_BOX),
        };

        this.triggers = new TriggerBase[]{
            // Fill from inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onMenuOption("Fill"),

            // Empty to inventory.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).emptyStorageToInventory().onMenuOption("Empty"),

            // Use storable item on kit.
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onUseChargedItemOnStorageItem(storage.getStorableItems()),
            new OnItemContainerChanged(ItemContainerId.INVENTORY).fillStorageFromInventory().onUseStorageItemOnChargedItem(storage.getStorableItems()),

            // Update from item container when viewing huntsmans kit contents.
            new OnItemContainerChanged(ItemContainerId.TACKLE_BOX).updateStorage(),

            // Hide destroy.
            new OnMenuEntryAdded("Destroy").hide(),
        };
    }
}
