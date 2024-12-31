package tictac7x.charges.item.triggers;

import java.util.Optional;

public class OnItemContainerChanged extends TriggerBase {
    public final int itemContainerId;

    public Optional<Boolean> updateStorage = Optional.empty();
    public Optional<Boolean> fillStorageFromInventory = Optional.empty();
    public Optional<Boolean> emptyStorageToInventory = Optional.empty();
    public Optional<Boolean> emptyStorageToBank = Optional.empty();

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
}
