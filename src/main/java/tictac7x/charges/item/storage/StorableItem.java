package tictac7x.charges.item.storage;

import java.util.Optional;

public class StorableItem extends StorageItem {
    public Optional<String[]> checkName = Optional.empty();
    public Optional<String> displayName = Optional.empty();
    public Optional<Integer> order = Optional.empty();

    public StorableItem(int itemId) {
        super(itemId);
    }

    public StorableItem(int itemId, int quantity) {
        super(itemId, quantity);
    }

    public StorableItem checkName(final String ...checkName) {
        this.checkName = Optional.of(checkName);
        return this;
    }

    public StorableItem specificOrder(final int order) {
        this.order = Optional.of(order);
        return this;
    }

    public StorableItem displayName(final String displayName) {
        this.displayName = Optional.of(displayName);
        return this;
    }

    public StorableItem quantity(final int quantity) {
        this.quantity = quantity;
        return this;
    }
}
