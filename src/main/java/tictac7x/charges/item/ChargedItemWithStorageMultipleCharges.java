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
import tictac7x.charges.store.Store;

public class ChargedItemWithStorageMultipleCharges extends ChargedItemWithStorage {
    public ChargedItemWithStorageMultipleCharges(String configKey, int itemId, Client client, ClientThread clientThread, ConfigManager configManager, ItemManager itemManager, InfoBoxManager infoBoxManager, ChatMessageManager chatMessageManager, Notifier notifier, TicTac7xChargesImprovedConfig config, Store store, final Gson gson) {
        super(configKey, itemId, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store, gson);
    }

    @Override
    public String getCharges() {
        String individualCharges = "";

        int validItems = 0;
        for (final StorageItem storageItem : getStorage().values()) {
            if (storageItem.quantity >= 0) {
                individualCharges += storageItem.quantity + "/";
                validItems++;
            }
        }

        return validItems > 0 ? individualCharges.replaceAll("/$", "") : "?";
    }
}
