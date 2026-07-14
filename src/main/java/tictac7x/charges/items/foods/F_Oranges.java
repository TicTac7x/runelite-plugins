package tictac7x.charges.items.foods;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class F_Oranges extends _Basket {
    public F_Oranges(Provider provider) {
        super("oranges", new TriggerItem[]{
            new TriggerItem(ItemID.BASKET_ORANGE_1).fixedCharges(1),
            new TriggerItem(ItemID.BASKET_ORANGE_2).fixedCharges(2),
            new TriggerItem(ItemID.BASKET_ORANGE_3).fixedCharges(3),
            new TriggerItem(ItemID.BASKET_ORANGE_4).fixedCharges(4),
            new TriggerItem(ItemID.BASKET_ORANGE_5).fixedCharges(5),
        }, provider);
    }   
}
