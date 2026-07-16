package tictac7x.charges;


import java.awt.image.*;
import java.util.function.*;

public class MyItemManager {
    private Function<Integer, MyItemComposition> getItemComposition;
    private Function<Integer, BufferedImage> getItemImage;

    public MyItemManager(Function<Integer, MyItemComposition> getItemComposition, Function<Integer, BufferedImage> getItemImage) {
        this.getItemComposition = getItemComposition;
        this.getItemImage = getItemImage;
    }

    public MyItemComposition getItemComposition(int itemId) {
        return getItemComposition.apply(itemId);
    }

    public BufferedImage getImage(int itemId) {
        return getItemImage.apply(itemId);
    }
}
