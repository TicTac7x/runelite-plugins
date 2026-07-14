package tictac7x.charges.items.utils;

import net.runelite.client.ui.*;
import net.runelite.client.util.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class U_MasterScrollBook extends ChargedItemWithStorage {
    public U_MasterScrollBook(Provider provider) {
        super(TicTac7xChargesImprovedConfig.master_scroll_book, ItemID.BOOKOFSCROLLS_CHARGED, provider);
        storage = storage.setMaximumIndividualQuantity(1000).storableItems(
            new StorableItem (ItemID.TELEPORTSCROLL_NARDAH).displayName("Nardah"),
            new StorableItem (ItemID.TELEPORTSCROLL_DIGSITE).displayName("Digsite"),
            new StorableItem (ItemID.TELEPORTSCROLL_FELDIP).displayName("Feldip Hills"),
            new StorableItem (ItemID.TELEPORTSCROLL_LUNARISLE).displayName("Lunar Isle"),
            new StorableItem (ItemID.TELEPORTSCROLL_MORTTON).displayName("Mort'ton"),
            new StorableItem (ItemID.TELEPORTSCROLL_PESTCONTROL).displayName("Pest Control"),
            new StorableItem (ItemID.TELEPORTSCROLL_PISCATORIS).displayName("Piscatoris"),
            new StorableItem (ItemID.TELEPORTSCROLL_TAIBWO).displayName("Tai Bwo Wannai"),
            new StorableItem (ItemID.TELEPORTSCROLL_ELF).displayName("Iorwerth Camp"),
            new StorableItem (ItemID.TELEPORTSCROLL_MOSLES).displayName("Mos Le' Harmless"),
            new StorableItem (ItemID.TELEPORTSCROLL_LUMBERYARD).displayName("Lumberyard"),
            new StorableItem (ItemID.TELEPORTSCROLL_ZULANDRA).displayName("Zul-Andra"),
            new StorableItem (ItemID.TELEPORTSCROLL_CERBERUS).displayName("Key Master"),
            new StorableItem (ItemID.TELEPORTSCROLL_REVENANTS).displayName("Revenant Caves"),
            new StorableItem (ItemID.TELEPORTSCROLL_WATSON).displayName("Watson"),
            new StorableItem (ItemID.TELEPORTSCROLL_GUTHIXIAN_TEMPLE).displayName("Guthixian Temple"),
            new StorableItem (ItemID.TELEPORTSCROLL_SPIDERCAVE).displayName("Spider Cave"),
            new StorableItem (ItemID.TELEPORTSCROLL_COLOSSAL_WYRM).displayName("Colossal Wyrm"),
            new StorableItem (ItemID.TELEPORTSCROLL_CHASMOFFIRE).displayName("Chasm of Fire")
        );

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BOOKOFSCROLLS_EMPTY),
            new TriggerItem(ItemID.BOOKOFSCROLLS_CHARGED),
        };

        this.triggers.addAll(List.of(
            new OnVarbitChanged(5672).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_NARDAH, charges)),
            new OnVarbitChanged(5673).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_DIGSITE, charges)),
            new OnVarbitChanged(5674).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_FELDIP, charges)),
            new OnVarbitChanged(5675).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_LUNARISLE, charges)),
            new OnVarbitChanged(5676).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_MORTTON, charges)),
            new OnVarbitChanged(5677).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_PESTCONTROL, charges)),
            new OnVarbitChanged(5678).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_PISCATORIS, charges)),
            new OnVarbitChanged(5679).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_TAIBWO, charges)),
            new OnVarbitChanged(5680).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_ELF, charges)),
            new OnVarbitChanged(5681).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_MOSLES, charges)),
            new OnVarbitChanged(5682).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_LUMBERYARD, charges)),
            new OnVarbitChanged(5683).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_ZULANDRA, charges)),
            new OnVarbitChanged(5684).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_CERBERUS, charges)),
            new OnVarbitChanged(6056).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_REVENANTS, charges)),
            new OnVarbitChanged(8253).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_WATSON, charges)),
            new OnVarbitChanged(10967).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_GUTHIXIAN_TEMPLE, charges)),
            new OnVarbitChanged(10995).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_SPIDERCAVE, charges)),
            new OnVarbitChanged(11029).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_COLOSSAL_WYRM, charges)),
            new OnVarbitChanged(16238).varbitValueConsumer(charges -> storage.put (ItemID.TELEPORTSCROLL_CHASMOFFIRE, charges)),

            // Replace default teleport option.
            new OnMenuEntryAdded("Teleport").replaceTargetDynamically("Master scroll book", this::getDefaultTeleportLocation),

            // Widget
            new OnWidgetLoaded(597, 2).consumer(() -> {
                storage.put (ItemID.TELEPORTSCROLL_NARDAH, Integer.parseInt(provider.client.getWidget(597, 7).getText()));
                storage.put (ItemID.TELEPORTSCROLL_DIGSITE, Integer.parseInt(provider.client.getWidget(597, 11).getText()));
                storage.put (ItemID.TELEPORTSCROLL_FELDIP, Integer.parseInt(provider.client.getWidget(597, 15).getText()));
                storage.put (ItemID.TELEPORTSCROLL_LUNARISLE, Integer.parseInt(provider.client.getWidget(597, 19).getText()));
                storage.put (ItemID.TELEPORTSCROLL_MORTTON, Integer.parseInt(provider.client.getWidget(597, 23).getText()));
                storage.put (ItemID.TELEPORTSCROLL_PESTCONTROL, Integer.parseInt(provider.client.getWidget(597, 27).getText()));
                storage.put (ItemID.TELEPORTSCROLL_PISCATORIS, Integer.parseInt(provider.client.getWidget(597, 31).getText()));
                storage.put (ItemID.TELEPORTSCROLL_TAIBWO, Integer.parseInt(provider.client.getWidget(597, 35).getText()));
                storage.put (ItemID.TELEPORTSCROLL_ELF, Integer.parseInt(provider.client.getWidget(597, 39).getText()));
                storage.put (ItemID.TELEPORTSCROLL_MOSLES, Integer.parseInt(provider.client.getWidget(597, 43).getText()));
                storage.put (ItemID.TELEPORTSCROLL_LUMBERYARD, Integer.parseInt(provider.client.getWidget(597, 47).getText()));
                storage.put (ItemID.TELEPORTSCROLL_ZULANDRA, Integer.parseInt(provider.client.getWidget(597, 51).getText()));
                storage.put (ItemID.TELEPORTSCROLL_CERBERUS, Integer.parseInt(provider.client.getWidget(597, 54).getText()));
                storage.put (ItemID.TELEPORTSCROLL_REVENANTS, Integer.parseInt(provider.client.getWidget(597, 60).getText()));
                storage.put (ItemID.TELEPORTSCROLL_WATSON, Integer.parseInt(provider.client.getWidget(597, 65).getText()));
                storage.put (ItemID.TELEPORTSCROLL_GUTHIXIAN_TEMPLE, Integer.parseInt(provider.client.getWidget(597, 68).getText()));
                storage.put (ItemID.TELEPORTSCROLL_SPIDERCAVE, Integer.parseInt(provider.client.getWidget(597, 71).getText()));
                storage.put (ItemID.TELEPORTSCROLL_COLOSSAL_WYRM, Integer.parseInt(provider.client.getWidget(597, 76).getText()));
                storage.put (ItemID.TELEPORTSCROLL_CHASMOFFIRE, Integer.parseInt(provider.client.getWidget(597, 79).getText()));
            })
        ));
    }

    private int getDefaultTeleportsOrTotal() {
        // First varbit goes from 0 - 14, where 0 means default scroll is not set.
        // Second varbit is set to 1, if the selected scroll is after 14th scroll.
        int varbit10966 = provider.client.getVarbitValue(10966);
        int varbit10968 = provider.client.getVarbitValue(10968);

        // Default teleport not set, show all scrolls.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return super.getTotalCharges();
        }

        int selectedScrollIndex = varbit10968 * 15 + varbit10966 - 1;

        // Unsupported scroll selected.
        if (selectedScrollIndex >= storage.getStorableItems().length) {
            return ChargeId.UNKNOWN;
        }

        Optional<StorageItem> selectedScroll = storage.getStorage().getItem(storage.getStorableItems()[selectedScrollIndex].itemId);

        if (!selectedScroll.isPresent()) {
            return 0;
        }

        return selectedScroll.get().getQuantity();
    }

    @Override
    public int getCharges(int itemId) {
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
        return getTotalCharges() == 0 ? provider.config.getColorEmpty() : super.getTotalTextColor();
    }

    @Override
    public String getTooltip() {
        int varbit10966 = provider.client.getVarbitValue(10966);
        int varbit10968 = provider.client.getVarbitValue(10968);

        // Default teleport not set, show all scrolls.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return super.getTooltip();
        }

        int selectedScrollIndex = varbit10968 * 15 + varbit10966 - 1;

        // Unsupported scroll selected.
        if (selectedScrollIndex >= storage.getStorableItems().length) {
            return "?";
        }

        // Default teleport set, but no teleports.
        if (!storage.getStorage().hasItem(storage.getStorableItems()[selectedScrollIndex].itemId)) {
            return super.getTooltip().replaceAll(getDefaultTeleportLocation() + ": <col=" + JagexColors.MENU_TARGET + ">.+?</col>", getDefaultTeleportLocation() + ": " + ColorUtil.wrapWithColorTag("0", provider.config.getColorEmpty()));
        }

        StorageItem defaultTeleportScrollStoreableItem = storage.getStorableItems()[selectedScrollIndex];
        Optional<StorageItem> defaultTeleportScrollStorageItem = storage.getStorage().getItem(defaultTeleportScrollStoreableItem.itemId);

        if (!defaultTeleportScrollStorageItem.isPresent()) {
            return "?";
        }

        return super.getTooltip().replaceAll(getDefaultTeleportLocation() + ": <col=ff9040>.+?</col>", getDefaultTeleportLocation() + ": <col=00ff00>" + defaultTeleportScrollStorageItem.get().getQuantity() + "</col>");
    }

    private String getDefaultTeleportLocation() {
        int varbit10966 = provider.client.getVarbitValue(10966);
        int varbit10968 = provider.client.getVarbitValue(10968);

        // Default teleport not set, show default.
        if (varbit10966 == 0 && varbit10968 == 0) {
            return provider.itemManager.getItemComposition(itemId).getName();

        // Default teleport set, show correct location display name.
        } else {
            return storage.getStorableItems()[varbit10968 * 15 + varbit10966 - 1].displayName.get();
        }
    }
}
