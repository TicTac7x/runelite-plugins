package tictac7x.charges.item;

import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.store.Provider;

public class ChargedItemWithStorageMultipleCharges extends ChargedItemWithStorage {
    public ChargedItemWithStorageMultipleCharges(final String configKey, final int itemId, final Provider provider) {
        super(configKey, itemId, provider);
    }

    @Override
    public String getChargesString(final int itemId) {
        return getTotalChargesString();
    }

    @Override
    public String getTotalChargesString() {
        String individualCharges = "";

        int validItems = 0;
        for (final StorageItem storageItem : getStorage().getItems()) {
            if (storageItem.getQuantity() >= 0) {
                individualCharges += storageItem.getQuantity() + "/";
                validItems++;
            }
        }

        return validItems > 0 ? individualCharges.replaceAll("/$", "") : "?";
    }
}
