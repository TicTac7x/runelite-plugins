package tictac7x.storage.storage;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import tictac7x.storage.utils.WidgetId;

import java.util.*;

public class DepositBox {
    private final Client client;
    private final Storage inventory;
    private final Storage bank;

    private final List<StorageItem> inventoryItemsBefore = new ArrayList<>();

    public DepositBox(final Client client, final Storage inventory, final Storage bank) {
        this.client = client;
        this.inventory = inventory;
        this.bank = bank;

        inventory.addOnChangeListener(this::onInventoryChanged);
    }

    private void onInventoryChanged() {
        final Optional<Widget> depositBoxWidget = Optional.ofNullable(client.getWidget(WidgetId.DEPOSIT_BOX[0], WidgetId.DEPOSIT_BOX[1]));
        if (depositBoxWidget.isPresent() && !depositBoxWidget.get().isHidden()) {
            depositItemsToBank();
        }

        updateInventoryItemsBefore();
    }

    private void depositItemsToBank() {
        for (final StorageItem itemBefore : inventoryItemsBefore) {
            final Optional<StorageItem> itemAfter = inventory.getItem(itemBefore.id);

            if (itemAfter.isPresent() && itemBefore.getQuantity() - itemAfter.get().getQuantity() != 0) {
                bank.addItem(new StorageItem(itemBefore.id, itemBefore.getQuantity() - itemAfter.get().getQuantity(), itemBefore.name), false);
            } else {
                bank.addItem(new StorageItem(itemBefore.id, itemBefore.getQuantity(), itemBefore.name), false);
            }
        }

        bank.updateConfig();
    }

    private void updateInventoryItemsBefore() {
        inventoryItemsBefore.clear();
        inventoryItemsBefore.addAll(inventory.getItems());
    }
}
