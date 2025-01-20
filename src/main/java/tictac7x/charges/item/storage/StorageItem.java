package tictac7x.charges.item.storage;

public class StorageItem {
    public final int itemId;
    public int quantity = 0;

    public StorageItem(final int itemId) {
        this.itemId = itemId;
    }

    public StorageItem(final int itemId, final int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
