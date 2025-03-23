package tictac7x.storage.storage;

public class StorageItem {
    public final int id;
    private int quantity;

    public StorageItem(final int id, final int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void increaseQuantity(final int quantity) {
        this.quantity += quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
