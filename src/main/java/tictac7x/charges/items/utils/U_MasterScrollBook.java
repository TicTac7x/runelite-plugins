package tictac7x.charges.items.utils;

import tictac7x.charges.item.triggers.OnWidgetLoaded;
import tictac7x.charges.store.ids.ChargeId;
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

import java.awt.*;
import java.util.Optional;

public class U_MasterScrollBook extends ChargedItemWithStorage {
    public U_MasterScrollBook(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.master_scroll_book, ItemId.MASTER_SCROLL_BOOK, provider);
        storage = storage.setMaximumIndividualQuantity(1000).storableItems(
            new StorableItem(ItemId.TELEPORTSCROLL_NARDAH).displayName("Nardah"),
            new StorableItem(ItemId.TELEPORTSCROLL_DIGSITE).displayName("Digsite"),
            new StorableItem(ItemId.TELEPORTSCROLL_FELDIP_HILLS).displayName("Feldip Hills"),
            new StorableItem(ItemId.TELEPORTSCROLL_LUNAR_ISLE).displayName("Lunar Isle"),
            new StorableItem(ItemId.TELEPORTSCROLL_MORTTON).displayName("Mort'ton"),
            new StorableItem(ItemId.TELEPORTSCROLL_PEST_CONTROL).displayName("Pest Control"),
            new StorableItem(ItemId.TELEPORTSCROLL_PISCATORIS).displayName("Piscatoris"),
            new StorableItem(ItemId.TELEPORTSCROLL_TAI_BWO_WANNAI).displayName("Tai Bwo Wannai"),
            new StorableItem(ItemId.TELEPORTSCROLL_IORWERTH_CAMP).displayName("Iorwerth Camp"),
            new StorableItem(ItemId.TELEPORTSCROLL_MOS_LEHARMLESS).displayName("Mos Le' Harmless"),
            new StorableItem(ItemId.TELEPORTSCROLL_LUMBERYARD).displayName("Lumberyard"),
            new StorableItem(ItemId.TELEPORTSCROLL_ZULANDRA).displayName("Zul-Andra"),
            new StorableItem(ItemId.TELEPORTSCROLL_KEY_MASTER).displayName("Key Master"),
            new StorableItem(ItemId.TELEPORTSCROLL_REVENANTS_CAVE).displayName("Revenant Caves"),
            new StorableItem(ItemId.TELEPORTSCROLL_WATSON).displayName("Watson"),
            new StorableItem(ItemId.TELEPORTSCROLL_GUTHIXIAN_TEMPLE).displayName("Guthixian Temple"),
            new StorableItem(ItemId.TELEPORTSCROLL_SPIDER_CAVE).displayName("Spider Cave"),
            new StorableItem(ItemId.TELEPORTSCROLL_COLOSSAL_WYRM).displayName("Colossal Wyrm"),
            new StorableItem(ItemId.TELEPORTSCROLL_CHASM_OF_FIRE).displayName("Chasm of Fire")
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
            new OnVarbitChanged(16238).varbitValueConsumer(charges -> storage.put(ItemId.TELEPORTSCROLL_CHASM_OF_FIRE, charges)),

            // Replace default teleport option.
            new OnMenuEntryAdded("Teleport").replaceTargetDynamically("Master scroll book", this::getDefaultTeleportLocation),

            // Widget
            new OnWidgetLoaded(597, 2).consumer(() -> {
                storage.put(ItemId.TELEPORTSCROLL_NARDAH, Integer.parseInt(provider.client.getWidget(597, 7).getText()));
                storage.put(ItemId.TELEPORTSCROLL_DIGSITE, Integer.parseInt(provider.client.getWidget(597, 11).getText()));
                storage.put(ItemId.TELEPORTSCROLL_FELDIP_HILLS, Integer.parseInt(provider.client.getWidget(597, 15).getText()));
                storage.put(ItemId.TELEPORTSCROLL_LUNAR_ISLE, Integer.parseInt(provider.client.getWidget(597, 19).getText()));
                storage.put(ItemId.TELEPORTSCROLL_MORTTON, Integer.parseInt(provider.client.getWidget(597, 23).getText()));
                storage.put(ItemId.TELEPORTSCROLL_PEST_CONTROL, Integer.parseInt(provider.client.getWidget(597, 27).getText()));
                storage.put(ItemId.TELEPORTSCROLL_PISCATORIS, Integer.parseInt(provider.client.getWidget(597, 31).getText()));
                storage.put(ItemId.TELEPORTSCROLL_TAI_BWO_WANNAI, Integer.parseInt(provider.client.getWidget(597, 35).getText()));
                storage.put(ItemId.TELEPORTSCROLL_IORWERTH_CAMP, Integer.parseInt(provider.client.getWidget(597, 39).getText()));
                storage.put(ItemId.TELEPORTSCROLL_MOS_LEHARMLESS, Integer.parseInt(provider.client.getWidget(597, 43).getText()));
                storage.put(ItemId.TELEPORTSCROLL_LUMBERYARD, Integer.parseInt(provider.client.getWidget(597, 47).getText()));
                storage.put(ItemId.TELEPORTSCROLL_ZULANDRA, Integer.parseInt(provider.client.getWidget(597, 51).getText()));
                storage.put(ItemId.TELEPORTSCROLL_KEY_MASTER, Integer.parseInt(provider.client.getWidget(597, 54).getText()));
                storage.put(ItemId.TELEPORTSCROLL_REVENANTS_CAVE, Integer.parseInt(provider.client.getWidget(597, 60).getText()));
                storage.put(ItemId.TELEPORTSCROLL_WATSON, Integer.parseInt(provider.client.getWidget(597, 65).getText()));
                storage.put(ItemId.TELEPORTSCROLL_GUTHIXIAN_TEMPLE, Integer.parseInt(provider.client.getWidget(597, 68).getText()));
                storage.put(ItemId.TELEPORTSCROLL_SPIDER_CAVE, Integer.parseInt(provider.client.getWidget(597, 71).getText()));
                storage.put(ItemId.TELEPORTSCROLL_COLOSSAL_WYRM, Integer.parseInt(provider.client.getWidget(597, 76).getText()));
                storage.put(ItemId.TELEPORTSCROLL_CHASM_OF_FIRE, Integer.parseInt(provider.client.getWidget(597, 79).getText()));
            }),
        };
    }

    private int getDefaultTeleportsOrTotal() {
        // First varbit goes from 0 - 14, where 0 means default scroll is not set.
        // Second varbit is set to 1, if the selected scroll is after 14th scroll.
        final int varbit10966 = provider.client.getVarbitValue(10966);
        final int varbit10968 = provider.client.getVarbitValue(10968);

        // Default teleport not set, show all scrolls.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return super.getTotalCharges();
        }

        final int selectedScrollIndex = varbit10968 * 15 + varbit10966 - 1;

        // Unsupported scroll selected.
        if (selectedScrollIndex >= storage.getStorableItems().length) {
            return ChargeId.UNKNOWN;
        }

        final Optional<StorageItem> selectedScroll = storage.getStorage().getItem(storage.getStorableItems()[selectedScrollIndex].getId());

        if (!selectedScroll.isPresent()) {
            return 0;
        }

        return selectedScroll.get().getQuantity();
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
    public Color getTextColor(int itemId) {
        return getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        return getTotalCharges() == 0 ? Color.red : super.getTotalTextColor();
    }

    @Override
    public String getTooltip() {
        final int varbit10966 = provider.client.getVarbitValue(10966);
        final int varbit10968 = provider.client.getVarbitValue(10968);

        // Default teleport not set, show all scrolls.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return super.getTooltip();
        }

        final int selectedScrollIndex = varbit10968 * 15 + varbit10966 - 1;

        // Unsupported scroll selected.
        if (selectedScrollIndex >= storage.getStorableItems().length) {
            return "?";
        }

        // Default teleport set, but no teleports.
        if (!storage.getStorage().hasItem(storage.getStorableItems()[selectedScrollIndex].getId())) {
            return super.getTooltip().replaceAll(getDefaultTeleportLocation() + ": <col=" + JagexColors.MENU_TARGET + ">.+?</col>", getDefaultTeleportLocation() + ": " + ColorUtil.wrapWithColorTag("0", provider.config.getColorEmpty()));
        }

        final StorageItem defaultTeleportScrollStoreableItem = storage.getStorableItems()[selectedScrollIndex];
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
