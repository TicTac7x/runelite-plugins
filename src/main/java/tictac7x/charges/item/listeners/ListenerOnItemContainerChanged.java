package tictac7x.charges.item.listeners;

import tictac7x.charges.events.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.storage.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ListenerOnItemContainerChanged extends ListenerBase {
    public ListenerOnItemContainerChanged(Provider provider) {
        super(provider);
    }

    public void trigger(CustomItemContainerChanged itemContainerChanged, ChargedItemBase chargedItem) {
        // Get quantity from amount in item container.
        for (TriggerItem triggerItem : chargedItem.items) {
            if (triggerItem.quantityCharges.isPresent()) {
               for (StorageItem item : itemContainerChanged.getItems()) {
                    if (item.itemId == triggerItem.itemId) {
                        ((ChargedItem) chargedItem).setCharges(item.getQuantity());
                        break;
                    }
                }
            }
        }

        for (TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(chargedItem, triggerBase, itemContainerChanged)) continue;
            boolean triggerUsed = false;
            OnItemContainerChanged trigger = (OnItemContainerChanged) triggerBase;

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

            if (super.trigger(trigger, chargedItem)) {
                triggerUsed = true;
            }

            if (triggerUsed) {
                afterTrigger(trigger);
                return;
            }
        }
    }

    public boolean isValidTrigger(ChargedItemBase chargedItem, TriggerBase triggerBase, CustomItemContainerChanged itemContainerChanged) {
        if (!(triggerBase instanceof OnItemContainerChanged)) return false;
        OnItemContainerChanged trigger = (OnItemContainerChanged) triggerBase;

        // Item container type check.
        if (
            itemContainerChanged.getContainerId() != trigger.itemContainerId) {
            return false;
        }

        return super.isValidTrigger(trigger, chargedItem);
    }
}
