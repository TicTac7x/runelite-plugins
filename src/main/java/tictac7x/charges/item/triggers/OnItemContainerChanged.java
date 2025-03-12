package tictac7x.charges.item.triggers;

import net.runelite.api.events.ItemContainerChanged;

import java.util.Optional;
import java.util.function.Consumer;

public class OnItemContainerChanged extends TriggerBase {
    public final int itemContainerId;

    public Optional<Boolean> updateStorage = Optional.empty();
    public Optional<Boolean> fillStorageFromInventory = Optional.empty();
    public Optional<Boolean> emptyStorageToInventory = Optional.empty();
    public Optional<Boolean> emptyStorageToBank = Optional.empty();
    public Optional<Consumer<ItemContainerChanged>> itemContainerConsumer = Optional.empty();

    public OnItemContainerChanged(final int itemContainerId) {
        this.itemContainerId = itemContainerId;
    }

    public OnItemContainerChanged fillStorageFromInventory() {
        this.fillStorageFromInventory = Optional.of(true);
        return this;
    }

    public OnItemContainerChanged emptyStorageToInventory() {
        this.emptyStorageToInventory = Optional.of(true);
        return this;
    }

    public OnItemContainerChanged updateStorage() {
        this.updateStorage = Optional.of(true);
        return this;
    }

    public OnItemContainerChanged emptyStorageToBank() {
        this.emptyStorageToBank = Optional.of(true);
        return this;
    }

    public TriggerBase itemContainerConsumer(final Consumer<ItemContainerChanged> event) {
        this.itemContainerConsumer = Optional.of(event);
        return this;
    }
}
