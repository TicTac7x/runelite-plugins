package tictac7x.storage.storageManagers;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import tictac7x.storage.storage.BankStorage;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;
import tictac7x.storage.utils.WidgetId;

import java.util.*;

import static tictac7x.storage.TicTac7xStoragePlugin.getWidget;

public class DepositBox {
    private final Client client;
    private final Storage inventory;
    private final BankStorage bank;

    private final List<StorageItem> inventoryItemsBefore = new ArrayList<>();

    public DepositBox(final Client client, final Storage inventory, final BankStorage bank) {
        this.client = client;
        this.inventory = inventory;
        this.bank = bank;

        inventory.addOnChangeListener(this::onInventoryChanged);
    }

    private void onInventoryChanged() {
        final Optional<Widget> depositBoxWidget = getWidget(WidgetId.DEPOSIT_BOX, client);
        if (depositBoxWidget.isPresent() && !depositBoxWidget.get().isHidden()) {
            depositItemsToBank();
        }

        updateInventoryItemsBefore();
    }

    private void depositItemsToBank() {
        final List<StorageItem> items = new ArrayList<>();

        for (final StorageItem itemBefore : inventoryItemsBefore) {
            final Optional<StorageItem> itemAfter = inventory.getItem(itemBefore.id);

            if (itemAfter.isPresent() && itemBefore.getQuantity() - itemAfter.get().getQuantity() != 0) {
                items.add(new StorageItem(itemBefore.id, itemBefore.getQuantity() - itemAfter.get().getQuantity(), itemBefore.name));
            } else {
                items.add(new StorageItem(itemBefore.id, itemBefore.getQuantity(), itemBefore.name));
            }
        }

        bank.depositItems(items);
    }

    private void updateInventoryItemsBefore() {
        inventoryItemsBefore.clear();
        inventoryItemsBefore.addAll(inventory.getItems());
    }
}
