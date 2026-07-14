package tictac7x.charges.item.storage;

public class StorageItem {
    public int itemId;
    private int quantity;

    public StorageItem(int itemId) {
        this.itemId = itemId;
        this.quantity = 0;
    }

    public StorageItem(int itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity = Math.max(this.quantity - quantity, 0);
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}
