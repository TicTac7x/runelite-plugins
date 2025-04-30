package tictac7x.charges.item.listeners;

import net.runelite.api.events.ItemDespawned;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnItemPickup;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.Provider;

public class ListenerOnItemPickup extends ListenerBase {
    public ListenerOnItemPickup(final Provider provider, final ChargedItemBase chargedItem) {
        super(provider, chargedItem);
    }

    public void trigger(final ItemDespawned event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;

            final OnItemPickup trigger = (OnItemPickup) triggerBase;
            boolean triggerUsed = false;

            if (trigger.pickUpToStorage.isPresent()) {
                ((ChargedItemWithStorage) chargedItem).storage.add(event.getItem().getId(), event.getItem().getQuantity());
                triggerUsed = true;
            }

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final ItemDespawned event) {
        if (!(triggerBase instanceof OnItemPickup)) return false;
        if (!(chargedItem instanceof ChargedItemWithStorage)) return false;
        final OnItemPickup trigger = (OnItemPickup) triggerBase;
        final ChargedItemWithStorage chargedItem = (ChargedItemWithStorage) this.chargedItem;

        // Correct item check.
        boolean correctItem = false;
        for (final StorageItem storageItem : chargedItem.storage.getStorableItems()) {
            if (event.getItem().getId() == storageItem.getId()) {
                correctItem = true;
                break;
            }
        }
        if (!correctItem) {
            return false;
        }

        // By one check.
        if (trigger.isByOne.isPresent() && trigger.isByOne.get() && event.getItem().getQuantity() > 1) {
            return false;
        }

        // Menu option check.
        if (!provider.store.inMenuOptions("Take")) {
            return false;
        }

        // Menu target check.
        if (!provider.store.inMenuTargets(event.getItem().getId())) {
            return false;
        }

        // Player location check.
        if (provider.client.getLocalPlayer().getWorldLocation().distanceTo(event.getTile().getWorldLocation()) > 1) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
