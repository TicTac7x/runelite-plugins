package tictac7x.charges.item.listeners;

import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.customEvents.CustomItemContainerChanged;
import tictac7x.charges.item.storage.StorageItems;
import tictac7x.charges.item.triggers.OnItemContainerChanged;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class ListenerOnItemContainerChanged extends ListenerBase {
    public ListenerOnItemContainerChanged(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);
    }

    public void trigger(final CustomItemContainerChanged itemContainerChanged) {
        // Get quantity from amount in item container.
        for (final TriggerItem triggerItem : chargedItem.items) {
            if (triggerItem.quantityCharges.isPresent()) {
               for (final StorageItem item : itemContainerChanged.getItems()) {
                    if (item.getId() == triggerItem.itemId) {
                        ((ChargedItem) chargedItem).setCharges(item.getQuantity());
                        break;
                    }
                }
            }
        }

        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, itemContainerChanged)) continue;
            boolean triggerUsed = false;
            final OnItemContainerChanged trigger = (OnItemContainerChanged) triggerBase;

            // Fill storage from inventory all items.
            if (trigger.fillStorageFromInventory.isPresent()) {
                ((ChargedItemWithStorage) chargedItem).storage.fillFromInventory();
                triggerUsed = true;
            }

            // Empty storage to bank.
            if (trigger.emptyStorageToBank.isPresent()) {
                ((ChargedItemWithStorage) chargedItem).storage.emptyToBank();
                triggerUsed = true;
            }

            // Empty storage to inventory.
            if (trigger.emptyStorageToInventory.isPresent()) {
                ((ChargedItemWithStorage) chargedItem).storage.emptyToInventory();
                triggerUsed = true;
            }

            // Update storage directly from item container.
            if (trigger.updateStorage.isPresent()) {
                ((ChargedItemWithStorage) chargedItem).storage.updateFromItemContainer(itemContainerChanged);
                triggerUsed = true;
            }

            if (trigger.onInventoryDifference.isPresent()) {
                trigger.onInventoryDifference.get().accept(provider.store.getInventoryItemsDifference());
                triggerUsed = true;
            }

            if (trigger.onBankDifference.isPresent()) {
                trigger.onBankDifference.get().accept(provider.store.getBankItemsDifference());
                triggerUsed = true;
            }

            if (trigger.itemsConsumer.isPresent()) {
                trigger.itemsConsumer.get().accept(new StorageItems(itemContainerChanged));
                triggerUsed = true;
            }

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final CustomItemContainerChanged itemContainerChanged) {
        if (!(triggerBase instanceof OnItemContainerChanged)) return false;
        final OnItemContainerChanged trigger = (OnItemContainerChanged) triggerBase;

        // Item container type check.
        if (
            itemContainerChanged.getContainerId() != trigger.itemContainerId) {
            return false;
        }

        // Fill storage from inventory check.
        if (trigger.fillStorageFromInventory.isPresent() && !(chargedItem instanceof ChargedItemWithStorage)) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
