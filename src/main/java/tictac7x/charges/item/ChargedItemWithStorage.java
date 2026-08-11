package tictac7x.charges.item;

import net.runelite.client.ui.*;
import net.runelite.client.util.*;
import tictac7x.charges.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.enums.*;

import java.awt.*;
import java.util.*;

public class ChargedItemWithStorage extends ChargedItemBase {
    public Storage storage;

    public ChargedItemWithStorage(String configKey, int itemId, Provider provider) {
        super(configKey, itemId, provider);
        this.storage = new Storage(this, configKey, provider);
        provider.clientThread.invokeLater(this::loadCharges);
    }

    @Override
    public String getTooltip() {
        if (getQuantities() == 0) {
            return "";
        }

        String tooltip = "";

        for (StorableItem storableItem : storage.getStorableItems()) {
            Optional<StorageItem> storageItem = storage.getStorage().getItem(storableItem.itemId);
            if (storageItem.isPresent() && storageItem.get().getQuantity() > 0) {
                // Name
                tooltip += (storableItem.displayName.isPresent() ? storableItem.displayName.get() : provider.itemManager.getItemComposition(storageItem.get().itemId).getName()) + ": ";
                // Quantity
                tooltip += ColorUtil.wrapWithColorTag(String.valueOf(storageItem.get().getQuantity()), JagexColors.MENU_TARGET) + "</br>";
            }
        }

        return tooltip.replaceAll("</br>$", "");
    }

    public StorageItems getStorage() {
        return this.storage.getStorage();
    }

    public Optional<StorageItem> getStorageItemFromName(String name, int quantity) {
        return storage.getStorageItemFromName(name, quantity);
    }

    private boolean isDisplayIndividual() {
        Optional<String> display = Optional.ofNullable(provider.configManager.getConfiguration(TicTac7xChargesImprovedConfig.group, getConfigKey() + TicTac7xChargesImprovedConfig._display));
        return display.isPresent() && display.get().equals(StorageDisplay.INDIVIDUAL);
    }

    private int getQuantities() {
        for (TriggerItem item : items) {
            if (item.itemId == itemId && item.fixedCharges.isPresent()) {
                return item.fixedCharges.get();
            }
        }

        int quantity = 0;

        for (StorageItem storageItem : getStorage().getItems()) {
            if (storageItem.getQuantity() > 0) {
                if (isDisplayIndividual()) {
                    if (storageItem.getQuantity() > quantity) {
                        quantity = storageItem.getQuantity();
                    }
                } else {
                    quantity += storageItem.getQuantity();
                }
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
        // Empty storage is negative
        if (storage.emptyIsNegative && storage.isEmpty()) {
            return provider.config.getColorEmpty();
        }

        // Full storage is positive.
        if (storage.emptyIsNegative && storage.isFull()) {
            return provider.config.getColorActivated();
        }

        // Full storage is negative.
        if (
            !storage.emptyIsNegative && storage.getMaximumTotalQuantity().isPresent() && getChargesString(itemId).equals(String.valueOf(storage.getMaximumTotalQuantity().get())) ||
            isDisplayIndividual() && storage.maximumIndividualQuantity.isPresent() && storage.maximumIndividualQuantity.get() == getQuantities()
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
    public Color getTextColor(int itemId) {
        return getStorageTextColor();
    }

    private void loadCharges() {
        storage.loadStorage();
    }
}
