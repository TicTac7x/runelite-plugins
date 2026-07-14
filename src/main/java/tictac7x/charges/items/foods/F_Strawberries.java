package tictac7x.charges.items.foods;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class F_Strawberries extends _Basket {
    public F_Strawberries(Provider provider) {
        super("strawberries", new TriggerItem[]{
            new TriggerItem(ItemID.BASKET_STRAWBERRY_1).fixedCharges(1),
            new TriggerItem(ItemID.BASKET_STRAWBERRY_2).fixedCharges(2),
            new TriggerItem(ItemID.BASKET_STRAWBERRY_3).fixedCharges(3),
            new TriggerItem(ItemID.BASKET_STRAWBERRY_4).fixedCharges(4),
            new TriggerItem(ItemID.BASKET_STRAWBERRY_5).fixedCharges(5),
        }, provider);
    }   
}
