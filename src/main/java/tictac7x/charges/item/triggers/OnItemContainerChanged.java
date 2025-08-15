package tictac7x.charges.item.triggers;

import tictac7x.charges.item.storage.StorageItems;

import java.util.Optional;
import java.util.function.Consumer;

public class OnItemContainerChanged extends TriggerBase {
    public final int itemContainerId;

    public Optional<Boolean> updateStorage = Optional.empty();
    public Optional<Consumer<StorageItems>> itemsConsumer = Optional.empty();

    public OnItemContainerChanged(final int itemContainerId) {
        this.itemContainerId = itemContainerId;
    }

    public OnItemContainerChanged updateStorage() {
        this.updateStorage = Optional.of(true);
        return this;
    }

    public TriggerBase itemsConsumer(final Consumer<StorageItems> event) {
        this.itemsConsumer = Optional.of(event);
        return this;
    }
}
