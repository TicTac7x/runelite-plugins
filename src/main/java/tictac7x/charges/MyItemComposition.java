package tictac7x.charges;

public class MyItemComposition {
    public final String name;
    public final boolean isStackable;
    public final boolean isPlaceholder;
    public final int itemId;

    public MyItemComposition(int itemId, String name, boolean isStackable, boolean isPlaceholder) {
        this.itemId = itemId;
        this.name = name;
        this.isStackable = isStackable;
        this.isPlaceholder = isPlaceholder;
    }
}
