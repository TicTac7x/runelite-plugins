package tictac7x.storage.storage;

public class StorageItem {
    public final int id;
    public final String name;
    private int quantity;


    public StorageItem(final int id, final int quantity, final String name) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
    }

    public void increaseQuantity(final int quantity) {
        this.quantity += quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
