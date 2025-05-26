package tictac7x.storage.overlays;

import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;
import tictac7x.storage.utils.Provider;

public class BankOverlay extends StorageOverlay {
    public BankOverlay(final String configKey, final Storage storage, final int[] widgetIds, final Provider provider) {
        super(configKey, storage, widgetIds, provider);
    }

    @Override
    boolean showItem(final StorageItem item) {
        if (provider.config.hideBankZeroQuantityItems() && item.getQuantity() == 0) {
            return false;
        } else {
            return super.showItem(item);
        }
    }
}
