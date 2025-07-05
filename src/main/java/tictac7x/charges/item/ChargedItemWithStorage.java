package tictac7x.charges.item;

import net.runelite.client.ui.JagexColors;
import net.runelite.client.util.ColorUtil;
import tictac7x.charges.item.storage.StorableItem;
import tictac7x.charges.item.storage.Storage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.storage.StorageItems;
import tictac7x.charges.store.Provider;

import java.awt.Color;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChargedItemWithStorage extends ChargedItemBase {
    public Storage storage;

    public ChargedItemWithStorage(final String configKey, final int itemId, final Provider provider) {
        super(configKey, itemId, provider);
        this.storage = new Storage(this, configKey, provider);
        provider.clientThread.invokeLater(this::loadCharges);
    }

    @Override
    public String getTooltip() {
        String tooltip = "";

        for (final StorableItem storableItem : storage.getStorableItems()) {
            final Optional<StorageItem> storageItem = storage.getStorage().getItem(storableItem.getId());
            if (storageItem.isPresent() && storageItem.get().getQuantity() > 0) {
                // Name
                tooltip += (storableItem.displayName.isPresent() ? storableItem.displayName.get() : provider.itemManager.getItemComposition(storageItem.get().getId()).getName()) + ": ";
                // Quantity
                tooltip += ColorUtil.wrapWithColorTag(String.valueOf(storageItem.get().getQuantity()), JagexColors.MENU_TARGET) + "</br>";
            }
        }

        return tooltip.replaceAll("</br>$", "");
    }

    public StorageItems getStorage() {
        return this.storage.getStorage();
    }

    public Optional<StorageItem> getStorageItemFromName(final String name, final int quantity) {
        return storage.getStorageItemFromName(name, quantity);
    }

    private int getQuantities() {
        int quantity = 0;

        for (final StorageItem storageItem : getStorage().getItems()) {
            if (storageItem.getQuantity() > 0) {
                quantity += storageItem.getQuantity();
            }
        }

        return quantity;
    }

    @Override
    public int getCharges(int itemId) {
        return getQuantities();
    }

    @Override
    public int getTotalCharges() {
        return getQuantities();
    }

    private Color getStorageTextColor() {
        // Full storage is positive.
        if (storage.emptyIsNegative && storage.isFull()) {
            return provider.config.getColorActivated();
        }

        // Full storage is negative.
        if (
            storage.emptyIsNegative && storage.isEmpty() ||
            !storage.emptyIsNegative && storage.getMaximumTotalQuantity().isPresent() && getChargesString(itemId).equals(String.valueOf(storage.getMaximumTotalQuantity().get()))
        ) {
            return provider.config.getColorEmpty();
        }

        // Storage is empty.
        if (getTotalCharges() == 0) {
            return provider.config.getColorDefault();
        }

        return super.getTotalTextColor();
    }

    @Override
    public Color getTotalTextColor() {
        return getStorageTextColor();
    }

    @Override
    public Color getTextColor(final int itemId) {
        return getStorageTextColor();
    }

    private void loadCharges() {
        storage.loadStorage();
    }
}
