package tictac7x.charges.items.foods;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class F_Tomatoes extends _Basket {
    public F_Tomatoes(Provider provider) {
        super("potatoes", new TriggerItem[]{
            new TriggerItem(ItemID.BASKET_TOMATO_1).fixedCharges(1),
            new TriggerItem(ItemID.BASKET_TOMATO_2).fixedCharges(2),
            new TriggerItem(ItemID.BASKET_TOMATO_3).fixedCharges(3),
            new TriggerItem(ItemID.BASKET_TOMATO_4).fixedCharges(4),
            new TriggerItem(ItemID.BASKET_TOMATO_5).fixedCharges(5),
        }, provider);
    }   
}
