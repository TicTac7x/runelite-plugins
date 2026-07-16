package tictac7x.charges;


import java.util.function.*;

public class MyItemManager {
    private Function<Integer, MyItemComposition> getItemComposition;

    public MyItemManager(Function<Integer, MyItemComposition> getItemComposition) {
        this.getItemComposition = getItemComposition;
    }

    public MyItemComposition getItemComposition(int itemId) {
        return getItemComposition.apply(itemId);
    }
}
