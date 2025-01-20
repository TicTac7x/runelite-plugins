package tictac7x.charges.item;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.JagexColors;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ColorUtil;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.storage.Storage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.Store;

import java.awt.Color;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChargedItemWithStorage extends ChargedItemBase {
    public Storage storage;

    public ChargedItemWithStorage(String configKey, int itemId, Client client, ClientThread clientThread, ConfigManager configManager, ItemManager itemManager, InfoBoxManager infoBoxManager, ChatMessageManager chatMessageManager, Notifier notifier, TicTac7xChargesImprovedConfig config, Store store, final Gson gson) {
        super(configKey, itemId, client, clientThread, configManager, itemManager, infoBoxManager, chatMessageManager, notifier, config, store);
        this.storage = new Storage(this, configKey, itemManager, configManager, store, gson);

        clientThread.invokeLater(() -> {
            loadCharges();
        });
    }

    @Override
    public String getTooltip() {
        String tooltip = "";
        for (final StorageItem storageItem : storage.getStorage().values().stream()
            .sorted(Comparator.comparing(storageItem -> storage.getStorageItemOrder(storageItem)))
            .collect(Collectors.toList())
        ) {
            if (storageItem.quantity > 0) {
                tooltip += storage.getStorageItemName(storageItem) + ": ";
                tooltip += ColorUtil.wrapWithColorTag(String.valueOf(storageItem.quantity), JagexColors.MENU_TARGET) + "</br>";
            }
        }

        return tooltip.replaceAll("</br>$", "");
    }

    public Map<Integer, StorageItem> getStorage() {
        return this.storage.getStorage();
    }

    public Optional<StorageItem> getStorageItemFromName(final String name) {
        return storage.getStorageItemFromName(name);
    }

    public int getQuantity() {
        int quantity = 0;

        for (final StorageItem storageItem : getStorage().values()) {
            if (storageItem.quantity >= 0) {
                quantity += storageItem.quantity;
            }
        }

        return quantity;
    }

    @Override
    public String getCharges() {
        int quantity = getQuantity();

        if (quantity == Charges.UNKNOWN) {
            return "?";
        }

        return getChargesMinified(quantity);
    }

    @Override
    public String getTotalCharges() {
        return getCharges();
    }

    private void loadCharges() {
        storage.loadStorage();
    }

    @Override
    public Color getTextColor() {
        // Full storage is positive.
        if (storage.emptyIsNegative && storage.isFull()) {
            return config.getColorActivated();
        }

        // Full storage is negative.
        if (
            storage.emptyIsNegative && storage.isEmpty() ||
            !storage.emptyIsNegative && storage.getMaximumTotalQuantity().isPresent() && getCharges().equals(String.valueOf(storage.getMaximumTotalQuantity().get()))
        ) {
            return config.getColorEmpty();
        }

        // Storage is empty.
        if (getQuantity() == 0) {
            return config.getColorDefault();
        }

        return super.getTextColor();
    }
}
