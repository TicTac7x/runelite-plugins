package tictac7x.charges.item.listeners;

import net.runelite.api.widgets.Widget;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.events.CustomMenuOptionClicked;
import tictac7x.charges.store.Provider;

import java.util.Optional;

public abstract class ListenerBase {
    protected final Provider provider;
    protected final ChargedItemBase chargedItem;

    public ListenerBase(final Provider provider, final ChargedItemBase chargedItem) {
        this.provider = provider;
        this.chargedItem = chargedItem;
    }

    boolean trigger(final TriggerBase trigger) {
        boolean triggerUsed = false;

        // Fixed charges.
        if (trigger.fixedCharges.isPresent() && (chargedItem instanceof ChargedItem)) {
            ((ChargedItem) chargedItem).setCharges(trigger.fixedCharges.get());
            triggerUsed = true;
        }

        // Increase charges.
        if (trigger.increaseCharges.isPresent() && (chargedItem instanceof ChargedItem)) {
            ((ChargedItem) chargedItem).increaseCharges(trigger.increaseCharges.get());
            triggerUsed = true;
        }

        // Decrease charges.
        if (trigger.decreaseCharges.isPresent() && (chargedItem instanceof ChargedItem)) {
            ((ChargedItem) chargedItem).decreaseCharges(trigger.decreaseCharges.get());
            triggerUsed = true;
        }

        // Empty storage.
        if (trigger.emptyStorage.isPresent() && (chargedItem instanceof ChargedItemWithStorage)) {
            ((ChargedItemWithStorage) chargedItem).storage.clear();
            triggerUsed = true;
        }

        // Empty storage to bank.
        if (trigger.emptyStorageToBank.isPresent() && (chargedItem instanceof ChargedItemWithStorage)) {
            ((ChargedItemWithStorage) chargedItem).storage.emptyToBank();
            triggerUsed = true;
        }

        // Fill storage from inventory.
        if (trigger.fillStorageFromInventory.isPresent() && (chargedItem instanceof ChargedItemWithStorage)) {
            ((ChargedItemWithStorage) chargedItem).storage.fillFromInventory();
            triggerUsed = true;
        }

        // Empty storage to inventory.
        if (trigger.emptyStorageToInventory.isPresent() && (chargedItem instanceof ChargedItemWithStorage)) {
            ((ChargedItemWithStorage) chargedItem).storage.emptyToInventory();
            triggerUsed = true;
        }

        // Add to storage.
        if (trigger.addToStorage.isPresent() && (chargedItem instanceof ChargedItemWithStorage)) {
            ((ChargedItemWithStorage) chargedItem).storage.add(trigger.addToStorage.get()[0], trigger.addToStorage.get()[1]);
            triggerUsed = true;
        }

        // Consumer.
        if (trigger.consumer.isPresent()) {
            if (trigger.runConsumerOnNextGameTick.isPresent() && trigger.runConsumerOnNextGameTick.get()) {
                chargedItem.provider.store.nextTickQueue.add(trigger.consumer.get());
            } else {
                trigger.consumer.get().run();
            }
            triggerUsed = true;
        }

        // Activate.
        if (trigger.activate.isPresent() && (chargedItem instanceof ChargedItemWithStatus)) {
            ((ChargedItemWithStatus) chargedItem).activate();
            triggerUsed = true;
        }

        // Deactivate.
        if (trigger.deactivate.isPresent() && (chargedItem instanceof ChargedItemWithStatus)) {
            ((ChargedItemWithStatus) chargedItem).deactivate();
            triggerUsed = true;
        }

        return triggerUsed;
    }

    boolean isValidTrigger(final TriggerBase trigger) {
        // Specific item check.
        specificItemCheck: if (trigger.requiredItem.isPresent()) {
            for (final int itemId : trigger.requiredItem.get()) {
                if (chargedItem.provider.store.inventoryContainsItem(itemId) || chargedItem.provider.store.equipmentContainsItem(itemId)) {
                    break specificItemCheck;
                }
            }
            return false;
        }

        // Unallowed items check.
        if (trigger.unallowedItem.isPresent()) {
            for (final int itemId : trigger.unallowedItem.get()) {
                if (chargedItem.provider.store.inventoryContainsItem(itemId) || chargedItem.provider.store.equipmentContainsItem(itemId)) {
                    return false;
                }
            }
        }

        // On item click check.
        if (trigger.onItemClick.isPresent() && chargedItem.provider.store.notInMenuTargets(chargedItem.itemId)) {
            return false;
        }

        // Menu option check.
        if (trigger.onMenuOption.isPresent() && chargedItem.provider.store.notInMenuOptions(trigger.onMenuOption.get())) {
            return false;
        }

        // Menu option ids check.
        if (trigger.onMenuOptionEventId.isPresent() && chargedItem.provider.store.notInMenuOptionIds(trigger.onMenuOptionEventId.get())) {
            return false;
        }

        // Menu target check.
        if (trigger.onMenuTarget.isPresent() && !trigger.onHover && chargedItem.provider.store.notInMenuTargets(trigger.onMenuTarget.get())) {
            return false;
        }

        // Menu impostor id check.
        if (trigger.onMenuImpostor.isPresent() && chargedItem.provider.store.notInMenuImpostors(trigger.onMenuImpostor.get())) {
            return false;
        }

        // Equipped check.
        if (trigger.isEquipped.isPresent() && !chargedItem.provider.store.equipmentContainsItem(chargedItem.itemId)) {
            return false;
        }

        // Use storage item on charged item check.
        if (trigger.onUseStorageItemOnChargedItem.isPresent() && chargedItem instanceof ChargedItemWithStorage) {
            boolean isValid = false;
            loopChecker: for (final CustomMenuOptionClicked menuEntry : chargedItem.provider.store.menuOptionsClicked) {
                if (!menuEntry.target.contains(" -> ")) {
                    continue;
                };

                final String itemOne = menuEntry.target.split(" -> ")[0];
                final String itemTwo = menuEntry.target.split(" -> ")[1];

                if (!itemOne.equals(chargedItem.getItemName()) && !itemTwo.equals(chargedItem.getItemName())) {
                    continue;
                }
                for (final StorageItem storeableItem : ((ChargedItemWithStorage) chargedItem).storage.getStorableItems()) {
                    if (
                        itemOne.equals(provider.itemManager.getItemComposition(storeableItem.getId()).getName()) ||
                        itemTwo.equals(provider.itemManager.getItemComposition(storeableItem.getId()).getName())
                    ) {
                        isValid = true;
                        break loopChecker;
                    }
                }
            }

            if (!isValid) {
                return false;
            }
        }

        // Use charged item on storage item check.
        if (trigger.onUseChargedItemOnStorageItem.isPresent() && chargedItem instanceof ChargedItemWithStorage) {
            boolean useCheck = false;
            useCheckLooper: for (final CustomMenuOptionClicked menuEntry : chargedItem.provider.store.menuOptionsClicked) {
                if (!menuEntry.option.equals("Use") || !menuEntry.target.contains(" -> ") || !menuEntry.target.split(" -> ")[0].equals(provider.itemManager.getItemComposition(chargedItem.itemId).getName())) continue;

                for (final StorageItem storageItem : ((ChargedItemWithStorage) chargedItem).getStorage().getItems()) {
                    if (menuEntry.target.split(" -> ")[1].equals(provider.itemManager.getItemComposition(storageItem.getId()).getName())) {
                        useCheck = true;
                        break useCheckLooper;
                    }
                }
            }
            if (!useCheck) {
                return false;
            }
        }

        // Activated check.
        if ((chargedItem instanceof ChargedItemWithStatus) && trigger.isActivated.isPresent() && trigger.isActivated.get() && !((ChargedItemWithStatus) chargedItem).isActivated()) {
            return false;
        }

        // Chat message check.
        if (trigger.hasChatMessage.isPresent()) {
            boolean matches = false;

            for (final String message : chargedItem.provider.store.getLastChatMessages()) {
                if (trigger.hasChatMessage.get().matcher(message).find()) {
                    matches = true;
                    break;
                }
            }

            if (!matches) {
                return false;
            }
        }

        // Varbit check.
        if (trigger.varbitCheck.isPresent()) {
            if (provider.client.getVarbitValue(trigger.varbitCheck.get()[0]) != trigger.varbitCheck.get()[1]) {
                return false;
            }
        }

        // Visible widget check.
        if (trigger.isWidgetVisible.isPresent()) {
            boolean widgetVisible = false;
            for (final int[] widgetIds : trigger.isWidgetVisible.get()) {
                final Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(provider.client, widgetIds[0], widgetIds[1]);
                if (widget.isPresent() && !widget.get().isHidden()) {
                    widgetVisible = true;
                    break;
                }
            }

            if (!widgetVisible) {
                return false;
            }
        }

        // Empty storage to inventory check.
        if (trigger.emptyStorageToInventory.isPresent() && !(chargedItem instanceof ChargedItemWithStorage)) {
            return false;
        }

        // Empty storage to bank check.
        if (trigger.emptyStorageToBank.isPresent() && !(chargedItem instanceof ChargedItemWithStorage)) {
            return false;
        }

        // Fill storage from inventory check.
        if (trigger.fillStorageFromInventory.isPresent() && !(chargedItem instanceof ChargedItemWithStorage)) {
            return false;
        }

        // Specific item equipped check.
        if (trigger.itemEquipped.isPresent() && !chargedItem.provider.store.equipmentContainsItem(trigger.itemEquipped.get())) {
            return false;
        }

        return true;
    }
}
