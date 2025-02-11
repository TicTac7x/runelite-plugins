package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.game.ItemManager;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.TicTac7xChargesImprovedPlugin;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.ChargedItemWithStatus;
import tictac7x.charges.item.ChargedItemWithStorage;
import tictac7x.charges.item.storage.StorageItem;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.AdvancedMenuEntry;

import java.util.Optional;
import java.util.regex.Matcher;

public abstract class ListenerBase {
    protected final Client client;
    protected final ItemManager itemManager;
    protected final ChargedItemBase chargedItem;
    protected final Notifier notifier;
    protected final TicTac7xChargesImprovedConfig config;

    public ListenerBase(final Client client, final ItemManager itemManager, final ChargedItemBase chargedItem, final Notifier notifier, final TicTac7xChargesImprovedConfig config) {
        this.client = client;
        this.itemManager = itemManager;
        this.chargedItem = chargedItem;
        this.notifier = notifier;
        this.config = config;
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

        // Add to storage.
        if (trigger.removeFromStorage.isPresent() && (chargedItem instanceof ChargedItemWithStorage)) {
            ((ChargedItemWithStorage) chargedItem).storage.remove(trigger.removeFromStorage.get().itemId, trigger.removeFromStorage.get().quantity);
            triggerUsed = true;
        }

        // Consumer.
        if (trigger.consumer.isPresent()) {
            if (trigger.runConsumerOnNextGameTick.isPresent() && trigger.runConsumerOnNextGameTick.get()) {
                chargedItem.store.nextTickQueue.add(trigger.consumer.get());
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

        // Notification
        if (trigger.notificationCustom.isPresent()) {
            notifier.notify(trigger.notificationCustom.get());
            triggerUsed = true;
        }

        return triggerUsed;
    }

    boolean isValidTrigger(final TriggerBase trigger) {
        // Specific item check.
        specificItemCheck: if (trigger.requiredItem.isPresent()) {
            for (final int itemId : trigger.requiredItem.get()) {
                if (chargedItem.store.inventoryContainsItem(itemId) || chargedItem.store.equipmentContainsItem(itemId)) {
                    break specificItemCheck;
                }
            }
            return false;
        }

        // Unallowed items check.
        if (trigger.unallowedItem.isPresent()) {
            for (final int itemId : trigger.unallowedItem.get()) {
                if (chargedItem.store.inventoryContainsItem(itemId) || chargedItem.store.equipmentContainsItem(itemId)) {
                    return false;
                }
            }
        }

        // On item click check.
        if (trigger.onItemClick.isPresent() && chargedItem.store.notInMenuTargets(chargedItem.itemId)) {
            return false;
        }

        // Menu option check.
        if (trigger.onMenuOption.isPresent() && chargedItem.store.notInMenuOptions(trigger.onMenuOption.get())) {
            return false;
        }

        // Menu option ids check.
        if (trigger.onMenuOptionId.isPresent() && chargedItem.store.notInMenuOptionIds(trigger.onMenuOptionId.get())) {
            return false;
        }

        // Menu target check.
        if (trigger.onMenuTarget.isPresent() && chargedItem.store.notInMenuTargets(trigger.onMenuTarget.get())) {
            return false;
        }

        // Menu impostor id check.
        if (trigger.onMenuImpostor.isPresent() && chargedItem.store.notInMenuImpostors(trigger.onMenuImpostor.get())) {
            return false;
        }

        // Equipped check.
        if (trigger.isEquipped.isPresent() && !chargedItem.store.equipmentContainsItem(chargedItem.itemId)) {
            return false;
        }

        // Use storage item on charged item check.
        if (trigger.onUseStorageItemOnChargedItem.isPresent() && chargedItem instanceof ChargedItemWithStorage) {
            boolean isValid = false;
            loopChecker: for (final AdvancedMenuEntry menuEntry : chargedItem.store.menuOptionsClicked) {
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
                        itemOne.equals(itemManager.getItemComposition(storeableItem.itemId).getName()) ||
                        itemTwo.equals(itemManager.getItemComposition(storeableItem.itemId).getName())
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
            useCheckLooper: for (final AdvancedMenuEntry menuEntry : chargedItem.store.menuOptionsClicked) {
                if (!menuEntry.option.equals("Use") || !menuEntry.target.contains(" -> ") || !menuEntry.target.split(" -> ")[0].equals(itemManager.getItemComposition(chargedItem.itemId).getName())) continue;

                for (final StorageItem storageItem : ((ChargedItemWithStorage) chargedItem).getStorage().values()) {
                    if (menuEntry.target.split(" -> ")[1].equals(itemManager.getItemComposition(storageItem.itemId).getName())) {
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
            if (!chargedItem.store.getLastChatMessage().isPresent()) {
                return false;
            }

            final Matcher matcher = trigger.hasChatMessage.get().matcher(chargedItem.store.getLastChatMessage().get());
            if (!matcher.find()) {
                return false;
            }
        }

        // Varbit check.
        if (trigger.varbitCheck.isPresent()) {
            if (client.getVarbitValue(trigger.varbitCheck.get()[0]) != trigger.varbitCheck.get()[1]) {
                return false;
            }
        }

        // Visible widget check.
        if (trigger.isWidgetVisible.isPresent()) {
            final Optional<Widget> widget = TicTac7xChargesImprovedPlugin.getWidget(client, trigger.isWidgetVisible.get()[0], trigger.isWidgetVisible.get()[1]);
            if (!widget.isPresent()) return false;
            if (widget.get().isHidden()) return false;
        }

        if (trigger.emptyStorageToInventory.isPresent() && !(chargedItem instanceof ChargedItemWithStorage)) {
            return false;
        }

        return true;
    }
}
