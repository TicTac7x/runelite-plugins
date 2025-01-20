package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.api.events.ItemDespawned;
import net.runelite.client.Notifier;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.OnItemPickup;
import tictac7x.charges.item.triggers.TriggerBase;

public class ListenerOnItemPickup extends ListenerBase {
    public ListenerOnItemPickup(final Client client, final ItemManager itemManager, final ChargedItemBase chargedItem, final Notifier notifier, final TicTac7xChargesImprovedConfig config) {
        super(client, itemManager, chargedItem, notifier, config);
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
            if (event.getItem().getId() == storageItem.itemId) {
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
        if (!chargedItem.store.inMenuOptions("Take")) {
            return false;
        }

        // Menu target check.
        if (!chargedItem.store.inMenuTargets(event.getItem().getId())) {
            return false;
        }

        // Player location check.
        if (client.getLocalPlayer().getWorldLocation().distanceTo(event.getTile().getWorldLocation()) > 1) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
