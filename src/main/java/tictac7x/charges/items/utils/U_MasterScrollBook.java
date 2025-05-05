package tictac7x.charges.items.utils;

import tictac7x.charges.store.ids.ItemId;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.util.ColorUtil;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.OnVarbitChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.Optional;

public class U_MasterScrollBook extends ChargedItemWithStorage {
    public U_MasterScrollBook(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.master_scroll_book, ItemId.MASTER_SCROLL_BOOK, provider);
        storage = storage.setMaximumIndividualQuantity(1000).storableItems(
            new StorableItem(ItemId.TELEPORTSCROLL_NARDAH).displayName("Nardah").specificOrder(1),
            new StorableItem(ItemId.TELEPORTSCROLL_DIGSITE).displayName("Digsite").specificOrder(2),
            new StorableItem(ItemId.TELEPORTSCROLL_FELDIP_HILLS).displayName("Feldip Hills").specificOrder(3),
            new StorableItem(ItemId.TELEPORTSCROLL_LUNAR_ISLE).displayName("Lunar Isle").specificOrder(4),
            new StorableItem(ItemId.TELEPORTSCROLL_MORTTON).displayName("Mort'ton").specificOrder(5),
            new StorableItem(ItemId.TELEPORTSCROLL_PEST_CONTROL).displayName("Pest Control").specificOrder(6),
            new StorableItem(ItemId.TELEPORTSCROLL_PISCATORIS).displayName("Piscatoris").specificOrder(7),
            new StorableItem(ItemId.TELEPORTSCROLL_TAI_BWO_WANNAI).displayName("Tai Bwo Wannai").specificOrder(8),
            new StorableItem(ItemId.TELEPORTSCROLL_IORWERTH_CAMP).displayName("Iorwerth Camp").specificOrder(9),
            new StorableItem(ItemId.TELEPORTSCROLL_MOS_LEHARMLESS).displayName("Mos Le' Harmless").specificOrder(10),
            new StorableItem(ItemId.TELEPORTSCROLL_LUMBERYARD).displayName("Lumberyard").specificOrder(11),
            new StorableItem(ItemId.TELEPORTSCROLL_ZULANDRA).displayName("Zul-Andra").specificOrder(12),
            new StorableItem(ItemId.TELEPORTSCROLL_KEY_MASTER).displayName("Key Master").specificOrder(13),
            new StorableItem(ItemId.TELEPORTSCROLL_REVENANTS_CAVE).displayName("Revenant Caves").specificOrder(14),
            new StorableItem(ItemId.TELEPORTSCROLL_WATSON).displayName("Watson").specificOrder(15),
            new StorableItem(ItemId.TELEPORTSCROLL_GUTHIXIAN_TEMPLE).displayName("Guthixian Temple").specificOrder(16),
            new StorableItem(ItemId.TELEPORTSCROLL_SPIDER_CAVE).displayName("Spider Cave").specificOrder(17),
            new StorableItem(ItemId.TELEPORTSCROLL_COLOSSAL_WYRM).displayName("Colossal Wyrm").specificOrder(18)
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.MASTER_SCROLL_BOOK_UNCHARGED),
            new TriggerItem(ItemId.MASTER_SCROLL_BOOK),
        };

        this.triggers = new TriggerBase[]{
            new OnVarbitChanged(5672).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_NARDAH, charges)),
            new OnVarbitChanged(5673).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_DIGSITE, charges)),
            new OnVarbitChanged(5674).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_FELDIP_HILLS, charges)),
            new OnVarbitChanged(5675).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_LUNAR_ISLE, charges)),
            new OnVarbitChanged(5676).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_MORTTON, charges)),
            new OnVarbitChanged(5677).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_PEST_CONTROL, charges)),
            new OnVarbitChanged(5678).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_PISCATORIS, charges)),
            new OnVarbitChanged(5679).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_TAI_BWO_WANNAI, charges)),
            new OnVarbitChanged(5680).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_IORWERTH_CAMP, charges)),
            new OnVarbitChanged(5681).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_MOS_LEHARMLESS, charges)),
            new OnVarbitChanged(5682).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_LUMBERYARD, charges)),
            new OnVarbitChanged(5683).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_ZULANDRA, charges)),
            new OnVarbitChanged(5684).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_KEY_MASTER, charges)),
            new OnVarbitChanged(6056).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_REVENANTS_CAVE, charges)),
            new OnVarbitChanged(8253).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_WATSON, charges)),
            new OnVarbitChanged(10967).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_GUTHIXIAN_TEMPLE, charges)),
            new OnVarbitChanged(10995).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_SPIDER_CAVE, charges)),
            new OnVarbitChanged(11029).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_COLOSSAL_WYRM, charges)),

            // Replace default teleport option.
            new OnMenuEntryAdded("Teleport").replaceTargetDynamically("Master scroll book", this::getDefaultTeleportLocation),
        };
    }

    private int getDefaultTeleportsOrTotal() {
        final int varbit10966 = provider.client.getVarbitValue(10966);
        final int varbit10968 = provider.client.getVarbitValue(10968);

        // Default teleport not set, show all scrolls.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return super.getTotalCharges();
        }

        // Default teleport set, but no teleports.
        if (!storage.getStorage().hasItem(storage.getStorableItems()[varbit10968 * 15 + varbit10966 - 1].getId())) {
            return 0;
        }

        final Optional<StorageItem> storageItem = storage.getStorage().getItem(storage.getStorableItems()[varbit10968 * 15 + varbit10966 - 1].getId());

        if (!storageItem.isPresent()) {
            return 0;
        }

        return storageItem.get().getQuantity();
    }

    @Override
    public int getCharges(final int itemId) {
        return getDefaultTeleportsOrTotal();
    }

    @Override
    public int getTotalCharges() {
        return getDefaultTeleportsOrTotal();
    }

    @Override
    public String getTooltip() {
        final int varbit10966 = provider.client.getVarbitValue(10966);
        final int varbit10968 = provider.client.getVarbitValue(10968);

        // Default teleport not set, show all scrolls.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return super.getTooltip();
        }

        final int teleportScrollIndex = varbit10968 * 15 + varbit10966 - 1;

        // Default teleport set, but no teleports.
        if (!storage.getStorage().hasItem(storage.getStorableItems()[teleportScrollIndex].getId())) {
            return super.getTooltip().replaceAll(getDefaultTeleportLocation() + ": <col=" + JagexColors.MENU_TARGET + ">.+?</col>", getDefaultTeleportLocation() + ": " + ColorUtil.wrapWithColorTag("0", provider.config.getColorEmpty()));
        }

        final StorageItem defaultTeleportScrollStoreableItem = storage.getStorableItems()[teleportScrollIndex];
        final Optional<StorageItem> defaultTeleportScrollStorageItem = storage.getStorage().getItem(defaultTeleportScrollStoreableItem.getId());

        if (!defaultTeleportScrollStorageItem.isPresent()) {
            return "?";
        }

        return super.getTooltip().replaceAll(getDefaultTeleportLocation() + ": <col=ff9040>.+?</col>", getDefaultTeleportLocation() + ": <col=00ff00>" + defaultTeleportScrollStorageItem.get().getQuantity() + "</col>");
    }

    private String getDefaultTeleportLocation() {
        final int varbit10966 = provider.client.getVarbitValue(10966);
        final int varbit10968 = provider.client.getVarbitValue(10968);

        // Default teleport not set, show default.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return provider.itemManager.getItemComposition(itemId).getName();

        // Default teleport set, show correct location display name.
        } else {
            return storage.getStorableItems()[varbit10968 * 15 + varbit10966 - 1].displayName.get();
        }
    }
}
