package tictac7x.storage.storageManagers;

import net.runelite.api.widgets.Widget;
import tictac7x.storage.storage.BankStorage;
import tictac7x.storage.storage.Storage;
import tictac7x.storage.storage.StorageItem;
import tictac7x.storage.utils.Provider;
import tictac7x.storage.utils.WidgetId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static tictac7x.storage.TicTac7xStoragePlugin.getWidget;

public class DepositBox {
    private final Provider provider;
    private final Storage inventory;
    private final BankStorage bank;

    private final List<StorageItem> inventoryItemsBefore = new ArrayList<>();

    public DepositBox(final Storage inventory, final BankStorage bank, final Provider provider) {
        this.provider = provider;
        this.inventory = inventory;
        this.bank = bank;

        inventory.addOnChangeListener(this::onInventoryChanged);
    }

    private void onInventoryChanged() {
        final Optional<Widget> depositBoxWidget = getWidget(WidgetId.DEPOSIT_BOX, provider.client);
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
            } else if (itemAfter.isEmpty()) {
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
