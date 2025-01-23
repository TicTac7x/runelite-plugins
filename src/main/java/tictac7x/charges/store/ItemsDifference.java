package tictac7x.charges.store;

import java.util.List;

public class ItemsDifference {
    public final List<ItemWithQuantity> items;

    public ItemsDifference(final List<ItemWithQuantity> items) {
        this.items = items;
    }

    public boolean hasItem(final int itemId) {
        for (final ItemWithQuantity item : items) {
            if (item.itemId == itemId) {
                return true;
            }
        }

        return false;
    }

    public int getItemQuantity(final int itemId) {
        for (final ItemWithQuantity item : items) {
            if (item.itemId == itemId) {
                return item.quantity;
            }
        }

        return 0;
    }
}
