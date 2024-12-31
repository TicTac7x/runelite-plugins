package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ColorUtil;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.OnVarbitChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class U_MasterScrollBook extends ChargedItemWithStorage {
    public U_MasterScrollBook(
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
        super(ChargesImprovedConfig.master_scroll_book, ItemID.MASTER_SCROLL_BOOK, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);
        storage = storage.setMaximumIndividualQuantity(1000).storeableItems(
            new StorageItem(ItemID.NARDAH_TELEPORT).displayName("Nardah").specificOrder(1),
            new StorageItem(ItemID.DIGSITE_TELEPORT).displayName("Digsite").specificOrder(2),
            new StorageItem(ItemID.FELDIP_HILLS_TELEPORT).displayName("Feldip Hills").specificOrder(3),
            new StorageItem(ItemID.LUNAR_ISLE_TELEPORT).displayName("Lunar Isle").specificOrder(4),
            new StorageItem(ItemID.MORTTON_TELEPORT).displayName("Mort'ton").specificOrder(5),
            new StorageItem(ItemID.PEST_CONTROL_TELEPORT).displayName("Pest Control").specificOrder(6),
            new StorageItem(ItemID.PISCATORIS_TELEPORT).displayName("Piscatoris").specificOrder(7),
            new StorageItem(ItemID.TAI_BWO_WANNAI_TELEPORT).displayName("Tai Bwo Wannai").specificOrder(8),
            new StorageItem(ItemID.IORWERTH_CAMP_TELEPORT).displayName("Iorwerth Camp").specificOrder(9),
            new StorageItem(ItemID.MOS_LEHARMLESS_TELEPORT).displayName("Mos Le' Harmless").specificOrder(10),
            new StorageItem(ItemID.LUMBERYARD_TELEPORT).displayName("Lumberyard").specificOrder(11),
            new StorageItem(ItemID.ZULANDRA_TELEPORT).displayName("Zul-Andra").specificOrder(12),
            new StorageItem(ItemID.KEY_MASTER_TELEPORT).displayName("Key Master").specificOrder(13),
            new StorageItem(ItemID.REVENANT_CAVE_TELEPORT).displayName("Revenant Caves").specificOrder(14),
            new StorageItem(ItemID.WATSON_TELEPORT).displayName("Watson").specificOrder(15),
            new StorageItem(ItemID.GUTHIXIAN_TEMPLE_TELEPORT).displayName("Guthixian Temple").specificOrder(16),
            new StorageItem(ItemID.SPIDER_CAVE_TELEPORT).displayName("Spider Cave").specificOrder(17),
            new StorageItem(ItemID.COLOSSAL_WYRM_TELEPORT_SCROLL).displayName("Colossal Wyrm").specificOrder(18)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.MASTER_SCROLL_BOOK_EMPTY),
            new TriggerItem(ItemID.MASTER_SCROLL_BOOK),
        };

        this.triggers = new TriggerBase[]{
            new OnVarbitChanged(5672).varbitValueConsumer(charges -> storage.put(ItemID.NARDAH_TELEPORT, charges)),
            new OnVarbitChanged(5673).varbitValueConsumer(charges -> storage.put(ItemID.DIGSITE_TELEPORT, charges)),
            new OnVarbitChanged(5674).varbitValueConsumer(charges -> storage.put(ItemID.FELDIP_HILLS_TELEPORT, charges)),
            new OnVarbitChanged(5675).varbitValueConsumer(charges -> storage.put(ItemID.LUNAR_ISLE_TELEPORT, charges)),
            new OnVarbitChanged(5676).varbitValueConsumer(charges -> storage.put(ItemID.MORTTON_TELEPORT, charges)),
            new OnVarbitChanged(5677).varbitValueConsumer(charges -> storage.put(ItemID.PEST_CONTROL_TELEPORT, charges)),
            new OnVarbitChanged(5678).varbitValueConsumer(charges -> storage.put(ItemID.PISCATORIS_TELEPORT, charges)),
            new OnVarbitChanged(5679).varbitValueConsumer(charges -> storage.put(ItemID.TAI_BWO_WANNAI_TELEPORT, charges)),
            new OnVarbitChanged(5680).varbitValueConsumer(charges -> storage.put(ItemID.IORWERTH_CAMP_TELEPORT, charges)),
            new OnVarbitChanged(5681).varbitValueConsumer(charges -> storage.put(ItemID.MOS_LEHARMLESS_TELEPORT, charges)),
            new OnVarbitChanged(5682).varbitValueConsumer(charges -> storage.put(ItemID.LUMBERYARD_TELEPORT, charges)),
            new OnVarbitChanged(5683).varbitValueConsumer(charges -> storage.put(ItemID.ZULANDRA_TELEPORT, charges)),
            new OnVarbitChanged(5684).varbitValueConsumer(charges -> storage.put(ItemID.KEY_MASTER_TELEPORT, charges)),
            new OnVarbitChanged(6056).varbitValueConsumer(charges -> storage.put(ItemID.REVENANT_CAVE_TELEPORT, charges)),
            new OnVarbitChanged(8253).varbitValueConsumer(charges -> storage.put(ItemID.WATSON_TELEPORT, charges)),
            new OnVarbitChanged(10967).varbitValueConsumer(charges -> storage.put(ItemID.GUTHIXIAN_TEMPLE_TELEPORT, charges)),
            new OnVarbitChanged(10995).varbitValueConsumer(charges -> storage.put(ItemID.SPIDER_CAVE_TELEPORT, charges)),
            new OnVarbitChanged(11029).varbitValueConsumer(charges -> storage.put(ItemID.COLOSSAL_WYRM_TELEPORT_SCROLL, charges)),

            // Replace default teleport option.
            new OnMenuEntryAdded("Teleport").replaceTargetDynamically("Master scroll book", this::getDefaultTeleportLocation),
        };
    }

    @Override
    public String getCharges() {
        final int varbit10966 = client.getVarbitValue(10966);
        final int varbit10968 = client.getVarbitValue(10968);

        // Default teleport not set, show all scrolls.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return super.getCharges();
        }

        // Default teleport set, but no teleports.
        if (!storage.getStorage().containsKey(storage.getStoreableItems()[varbit10968 * 15 + varbit10966 - 1].itemId)) {
            return "0";
        }

        return String.valueOf(storage.getStorage().get(storage.getStoreableItems()[varbit10968 * 15 + varbit10966 - 1].itemId).quantity);
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
        if (!storage.getStorage().containsKey(storage.getStoreableItems()[teleportScrollIndex].itemId)) {
            return super.getTooltip().replaceAll(getDefaultTeleportLocation() + ": <col=" + JagexColors.MENU_TARGET + ">.+?</col>", getDefaultTeleportLocation() + ": " + ColorUtil.wrapWithColorTag("0", config.getColorEmpty()));
        }

        final StorageItem defaultTeleportScrollStoreableItem = storage.getStoreableItems()[teleportScrollIndex];
        final StorageItem defaultTeleportScrollStorageItem = storage.getStorage().get(defaultTeleportScrollStoreableItem.itemId);

        return super.getTooltip().replaceAll(getDefaultTeleportLocation() + ": <col=ff9040>.+?</col>", getDefaultTeleportLocation() + ": <col=00ff00>" + defaultTeleportScrollStorageItem.getQuantity() + "</col>");
    }

    private String getDefaultTeleportLocation() {
        final int varbit10966 = client.getVarbitValue(10966);
        final int varbit10968 = client.getVarbitValue(10968);

        // Default teleport not set, show default.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return itemManager.getItemComposition(itemId).getName();

        // Default teleport set, show correct location display name.
        } else {
            return storage.getStoreableItems()[varbit10968 * 15 + varbit10966 - 1].displayName.get();
        }
    }
}
