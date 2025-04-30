package tictac7x.charges.item;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.Store;

public class ChargedItemWithStorageMultipleCharges extends ChargedItemWithStorage {
    public ChargedItemWithStorageMultipleCharges(final String configKey, final int itemId, final Provider provider) {
        super(configKey, itemId, provider);
    }

    @Override
    public String getCharges(final int itemId) {
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
