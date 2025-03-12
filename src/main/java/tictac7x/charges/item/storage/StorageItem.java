package tictac7x.charges.item.storage;

public class StorageItem {
    public final int itemId;
    private int quantity;

    public StorageItem(final int itemId) {
        this.itemId = itemId;
        this.quantity = 0;
    }

    public StorageItem(final int itemId, final int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity(final int quantity) {
        this.quantity = Math.max(this.quantity - quantity, 0);
    }
}
