package tictac7x.charges.item.triggers;

import tictac7x.charges.item.storage.*;

import java.util.*;

public class OnItemPickup extends TriggerBase {
    public StorageItem[] items;
    public Optional<Boolean> isByOne = Optional.empty();

    public OnItemPickup(StorageItem[] items) {
        this.items = items;
    }

    public OnItemPickup isByOne() {
        this.isByOne = Optional.of(true);
        return this;
    }
}
