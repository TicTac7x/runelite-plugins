package tictac7x.charges.item.triggers;

import tictac7x.charges.item.storage.*;
import tictac7x.charges.store.utils.*;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;

public abstract class TriggerBase {
    // Checks.
    public Optional<int[]> requiredItem = Optional.empty();
    public Optional<int[]> unallowedItem = Optional.empty();
    public Optional<String[]> onMenuOption = Optional.empty();
    public Optional<int[]> onMenuOptionEventId = Optional.empty();
    public Optional<String[]> onMenuTarget = Optional.empty();
    public Optional<int[]> onMenuImpostor = Optional.empty();
    public Optional<Boolean> onItemClick = Optional.empty();
    public Optional<WidgetMenuAction> onWidgetMenuAction = Optional.empty();
    public boolean onHover = false;
    public Optional<StorageItem[]> onUseStorageItemOnChargedItem = Optional.empty();
    public Optional<StorageItem[]> onUseChargedItemOnStorageItem = Optional.empty();
    public Optional<Boolean> isEquipped = Optional.empty();
    public Optional<Pattern> hasChatMessage = Optional.empty();
    public Optional<Boolean> runConsumerOnNextGameTick = Optional.empty();
    public Optional<int[]> varbitCheck = Optional.empty();
    public Optional<int[][]> isWidgetVisible = Optional.empty();
    public Optional<int[]> itemEquipped = Optional.empty();
    public Optional<int[]> hasAnimationId = Optional.empty();
    public boolean multiTrigger = false;

    // Actions.
    public Optional<Integer> fixedCharges = Optional.empty();
    public Optional<Integer> increaseCharges = Optional.empty();
    public Optional<Integer> decreaseCharges = Optional.empty();
    public Optional<Runnable> consumer = Optional.empty();

    // Storage.
    public Optional<Boolean> emptyStorage = Optional.empty();
    public Optional<Boolean> emptyStorageToInventory = Optional.empty();
    public Optional<Boolean> fillStorageFromInventory = Optional.empty();
    public Optional<Boolean> emptyStorageToBank = Optional.empty();
    public Optional<Boolean> fillStorageFromBank = Optional.empty();
    public Optional<Boolean> pickUpToStorage = Optional.empty();
    public Optional<int[]> addToStorage = Optional.empty();

    // Activity.
    public Optional<Boolean> isActivated = Optional.empty();
    public Optional<Boolean> activate = Optional.empty();
    public Optional<Boolean> deactivate = Optional.empty();

    public Optional<Consumer<StorageItems>> onInventoryDifference = Optional.empty();
    public Optional<Consumer<StorageItems>> onBankDifference = Optional.empty();

    public TriggerBase setFixedCharges(int charges) {
        this.fixedCharges = Optional.of(charges);
        return this;
    }

    public TriggerBase increaseCharges(int charges) {
        this.increaseCharges = Optional.of(charges);
        return this;
    }

    public TriggerBase decreaseCharges(int charges) {
        this.decreaseCharges = Optional.of(charges);
        return this;
    }

    public TriggerBase emptyStorage() {
        this.emptyStorage = Optional.of(true);
        return this;
    }

    public TriggerBase emptyStorageToInventory() {
        this.emptyStorageToInventory = Optional.of(true);
        return this;
    }

    public TriggerBase emptyStorageToBank() {
        this.emptyStorageToBank = Optional.of(true);
        return this;
    }

    public TriggerBase fillStorageFromBank() {
        this.fillStorageFromBank = Optional.of(true);
        return this;
    }

    public TriggerBase fillStorageFromInventory() {
        this.fillStorageFromInventory = Optional.of(true);
        return this;
    }

    public TriggerBase requiredItem(int ...itemIds) {
        this.requiredItem = Optional.of(itemIds);
        return this;
    }

    public TriggerBase unallowedItem(int ...itemIds) {
        this.unallowedItem = Optional.of(itemIds);
        return this;
    }

    public TriggerBase onMenuOption(String ...options) {
        this.onMenuOption = Optional.of(options);
        return this;
    }

    public TriggerBase onMenuOptionEventId(int ...menuOptionEventIds) {
        this.onMenuOptionEventId = Optional.of(menuOptionEventIds);
        return this;
    }

    public TriggerBase onMenuTarget(String ...targets) {
        this.onMenuTarget = Optional.of(targets);
        return this;
    }

    public TriggerBase onMenuTarget(List<String> targets) {
        this.onMenuTarget = Optional.of(targets.toArray(String[]::new));
        return this;
    }

    public TriggerBase onItemClick() {
        this.onItemClick = Optional.of(true);
        return this;
    }
    
    public TriggerBase onWidgetMenuAction(WidgetMenuAction widgetMenuAction) {
        this.onWidgetMenuAction = Optional.of(widgetMenuAction);
        return this;
    }

    public TriggerBase onHover() {
        this.onHover = true;
        return this;
    }

    public TriggerBase pickUpToStorage() {
        this.pickUpToStorage = Optional.of(true);
        return this;
    }

    public TriggerBase onUseStorageItemOnChargedItem(StorageItem[] storeableItems) {
        this.onUseStorageItemOnChargedItem = Optional.of(storeableItems);
        return this;
    }

    public TriggerBase onUseChargedItemOnStorageItem(StorageItem[] storeableItems) {
        this.onUseChargedItemOnStorageItem = Optional.of(storeableItems);
        return this;
    }

    public TriggerBase activate() {
        this.activate = Optional.of(true);
        return this;
    }

    public TriggerBase deactivate() {
        this.deactivate = Optional.of(true);
        return this;
    }

    public TriggerBase isEquipped() {
        this.isEquipped = Optional.of(true);
        return this;
    }

    public TriggerBase hasAnimationId(int ...animationId) {
        this.hasAnimationId = Optional.of(animationId);
        return this;
    }

    public TriggerBase consumer(Runnable consumer) {
        this.consumer = Optional.of(consumer);
        return this;
    }

    public TriggerBase runConsumerOnNextGameTick(Runnable consumer) {
        this.runConsumerOnNextGameTick = Optional.of(true);
        this.consumer = Optional.of(consumer);
        return this;
    }

    public TriggerBase isActivated() {
        this.isActivated = Optional.of(true);
        return this;
    }

    public TriggerBase addToStorage(int itemId, int quantity) {
        this.addToStorage = Optional.of(new int[]{itemId, quantity});
        return this;
    }

    public TriggerBase addToStorage(int itemId) {
        this.addToStorage = Optional.of(new int[]{itemId, 1});
        return this;
    }

    public TriggerBase multiTrigger() {
        this.multiTrigger = true;
        return this;
    }

    public TriggerBase onInventoryDifference(Consumer<StorageItems> consumer) {
        this.onInventoryDifference = Optional.of(consumer);
        return this;
    }

    public TriggerBase onBankDifference(Consumer<StorageItems> consumer) {
        this.onBankDifference = Optional.of(consumer);
        return this;
    }

    public TriggerBase hasChatMessage(String message) {
        this.hasChatMessage = Optional.of(Pattern.compile(message));
        return this;
    }

    public TriggerBase varbitCheck(int varbitId, int varbitValue) {
        this.varbitCheck = Optional.of(new int[]{varbitId, varbitValue});
        return this;
    }

    public TriggerBase isWidgetVisible(int[] ...widgetIds) {
        this.isWidgetVisible = Optional.of(widgetIds);
        return this;
    }
}
