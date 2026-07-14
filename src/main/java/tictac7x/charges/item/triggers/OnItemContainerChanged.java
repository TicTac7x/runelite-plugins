package tictac7x.charges.item.triggers;

import tictac7x.charges.item.storage.*;

import java.util.*;
import java.util.function.*;

public class OnItemContainerChanged extends TriggerBase {
    public int itemContainerId;

    public Optional<Boolean> updateStorage = Optional.empty();
    public Optional<Consumer<StorageItems>> itemsConsumer = Optional.empty();

    public OnItemContainerChanged(int itemContainerId) {
        this.itemContainerId = itemContainerId;
    }

    public OnItemContainerChanged updateStorage() {
        this.updateStorage = Optional.of(true);
        return this;
    }

    public TriggerBase itemsConsumer(Consumer<StorageItems> event) {
        this.itemsConsumer = Optional.of(event);
        return this;
    }
}
