package tictac7x.charges.item.storage;

public class StorageItem {
    private final int id;
    private int quantity;

    public StorageItem(final int id) {
        this.id = id;
        this.quantity = 0;
    }

    public StorageItem(final int id, final int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
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

    public void increaseQuantity(final int quantity) {
        this.quantity += quantity;
    }
}
