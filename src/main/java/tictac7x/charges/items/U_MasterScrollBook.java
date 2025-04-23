package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import tictac7x.charges.store.ItemId;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ColorUtil;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.OnVarbitChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

import java.util.Optional;

public class U_MasterScrollBook extends ChargedItemWithStorage {
    public U_MasterScrollBook(
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
        super(TicTac7xChargesImprovedConfig.master_scroll_book, ItemId.MASTER_SCROLL_BOOK, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
        storage = storage.setMaximumIndividualQuantity(1000).storableItems(
            new StorableItem(ItemId.NARDAH_TELEPORT).displayName("Nardah").specificOrder(1),
            new StorableItem(ItemId.DIGSITE_TELEPORT).displayName("Digsite").specificOrder(2),
            new StorableItem(ItemId.FELDIP_HILLS_TELEPORT).displayName("Feldip Hills").specificOrder(3),
            new StorableItem(ItemId.LUNAR_ISLE_TELEPORT).displayName("Lunar Isle").specificOrder(4),
            new StorableItem(ItemId.MORTTON_TELEPORT).displayName("Mort'ton").specificOrder(5),
            new StorableItem(ItemId.PEST_CONTROL_TELEPORT).displayName("Pest Control").specificOrder(6),
            new StorableItem(ItemId.PISCATORIS_TELEPORT).displayName("Piscatoris").specificOrder(7),
            new StorableItem(ItemId.TAI_BWO_WANNAI_TELEPORT).displayName("Tai Bwo Wannai").specificOrder(8),
            new StorableItem(ItemId.IORWERTH_CAMP_TELEPORT).displayName("Iorwerth Camp").specificOrder(9),
            new StorableItem(ItemId.MOS_LEHARMLESS_TELEPORT).displayName("Mos Le' Harmless").specificOrder(10),
            new StorableItem(ItemId.LUMBERYARD_TELEPORT).displayName("Lumberyard").specificOrder(11),
            new StorableItem(ItemId.ZULANDRA_TELEPORT).displayName("Zul-Andra").specificOrder(12),
            new StorableItem(ItemId.KEY_MASTER_TELEPORT).displayName("Key Master").specificOrder(13),
            new StorableItem(ItemId.REVENANT_CAVE_TELEPORT).displayName("Revenant Caves").specificOrder(14),
            new StorableItem(ItemId.WATSON_TELEPORT).displayName("Watson").specificOrder(15),
            new StorableItem(ItemId.GUTHIXIAN_TEMPLE_TELEPORT).displayName("Guthixian Temple").specificOrder(16),
            new StorableItem(ItemId.SPIDER_CAVE_TELEPORT).displayName("Spider Cave").specificOrder(17),
            new StorableItem(ItemId.COLOSSAL_WYRM_TELEPORT_SCROLL).displayName("Colossal Wyrm").specificOrder(18)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.MASTER_SCROLL_BOOK_EMPTY),
            new TriggerItem(ItemId.MASTER_SCROLL_BOOK),
        };

        this.triggers = new TriggerBase[]{
            new OnVarbitChanged(5672).varbitValueConsumer(charges -> storage.put(ItemId.NARDAH_TELEPORT, charges)),
            new OnVarbitChanged(5673).varbitValueConsumer(charges -> storage.put(ItemId.DIGSITE_TELEPORT, charges)),
            new OnVarbitChanged(5674).varbitValueConsumer(charges -> storage.put(ItemId.FELDIP_HILLS_TELEPORT, charges)),
            new OnVarbitChanged(5675).varbitValueConsumer(charges -> storage.put(ItemId.LUNAR_ISLE_TELEPORT, charges)),
            new OnVarbitChanged(5676).varbitValueConsumer(charges -> storage.put(ItemId.MORTTON_TELEPORT, charges)),
            new OnVarbitChanged(5677).varbitValueConsumer(charges -> storage.put(ItemId.PEST_CONTROL_TELEPORT, charges)),
            new OnVarbitChanged(5678).varbitValueConsumer(charges -> storage.put(ItemId.PISCATORIS_TELEPORT, charges)),
            new OnVarbitChanged(5679).varbitValueConsumer(charges -> storage.put(ItemId.TAI_BWO_WANNAI_TELEPORT, charges)),
            new OnVarbitChanged(5680).varbitValueConsumer(charges -> storage.put(ItemId.IORWERTH_CAMP_TELEPORT, charges)),
            new OnVarbitChanged(5681).varbitValueConsumer(charges -> storage.put(ItemId.MOS_LEHARMLESS_TELEPORT, charges)),
            new OnVarbitChanged(5682).varbitValueConsumer(charges -> storage.put(ItemId.LUMBERYARD_TELEPORT, charges)),
            new OnVarbitChanged(5683).varbitValueConsumer(charges -> storage.put(ItemId.ZULANDRA_TELEPORT, charges)),
            new OnVarbitChanged(5684).varbitValueConsumer(charges -> storage.put(ItemId.KEY_MASTER_TELEPORT, charges)),
            new OnVarbitChanged(6056).varbitValueConsumer(charges -> storage.put(ItemId.REVENANT_CAVE_TELEPORT, charges)),
            new OnVarbitChanged(8253).varbitValueConsumer(charges -> storage.put(ItemId.WATSON_TELEPORT, charges)),
            new OnVarbitChanged(10967).varbitValueConsumer(charges -> storage.put(ItemId.GUTHIXIAN_TEMPLE_TELEPORT, charges)),
            new OnVarbitChanged(10995).varbitValueConsumer(charges -> storage.put(ItemId.SPIDER_CAVE_TELEPORT, charges)),
            new OnVarbitChanged(11029).varbitValueConsumer(charges -> storage.put(ItemId.COLOSSAL_WYRM_TELEPORT_SCROLL, charges)),

            // Replace default teleport option.
            new OnMenuEntryAdded("Teleport").replaceTargetDynamically("Master scroll book", this::getDefaultTeleportLocation),
        };
    }

    @Override
    public String getCharges(final int itemId) {
        final int varbit10966 = client.getVarbitValue(10966);
        final int varbit10968 = client.getVarbitValue(10968);

        // Default teleport not set, show all scrolls.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return super.getCharges(itemId);
        }

        // Default teleport set, but no teleports.
        if (!storage.getStorage().hasItem(storage.getStorableItems()[varbit10968 * 15 + varbit10966 - 1].getId())) {
            return "0";
        }

        final Optional<StorageItem> storageItem = storage.getStorage().getItem(storage.getStorableItems()[varbit10968 * 15 + varbit10966 - 1].getId());

        if (!storageItem.isPresent()) {
            return "0";
        }

        return String.valueOf(storageItem.get().getQuantity());
    }

    @Override
    public String getTooltip() {
        final int varbit10966 = client.getVarbitValue(10966);
        final int varbit10968 = client.getVarbitValue(10968);

        // Default teleport not set, show all scrolls.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return super.getTooltip();
        }

        final int teleportScrollIndex = varbit10968 * 15 + varbit10966 - 1;

        // Default teleport set, but no teleports.
        if (!storage.getStorage().hasItem(storage.getStorableItems()[teleportScrollIndex].getId())) {
            return super.getTooltip().replaceAll(getDefaultTeleportLocation() + ": <col=" + JagexColors.MENU_TARGET + ">.+?</col>", getDefaultTeleportLocation() + ": " + ColorUtil.wrapWithColorTag("0", config.getColorEmpty()));
        }

        final StorageItem defaultTeleportScrollStoreableItem = storage.getStorableItems()[teleportScrollIndex];
        final Optional<StorageItem> defaultTeleportScrollStorageItem = storage.getStorage().getItem(defaultTeleportScrollStoreableItem.getId());

        if (!defaultTeleportScrollStorageItem.isPresent()) {
            return "?";
        }

        return super.getTooltip().replaceAll(getDefaultTeleportLocation() + ": <col=ff9040>.+?</col>", getDefaultTeleportLocation() + ": <col=00ff00>" + defaultTeleportScrollStorageItem.get().getQuantity() + "</col>");
    }

    private String getDefaultTeleportLocation() {
        final int varbit10966 = client.getVarbitValue(10966);
        final int varbit10968 = client.getVarbitValue(10968);

        // Default teleport not set, show default.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return itemManager.getItemComposition(itemId).getName();

        // Default teleport set, show correct location display name.
        } else {
            return storage.getStorableItems()[varbit10968 * 15 + varbit10966 - 1].displayName.get();
        }
    }
}
