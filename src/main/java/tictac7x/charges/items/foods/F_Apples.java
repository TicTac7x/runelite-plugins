package tictac7x.charges.items.foods;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class F_Apples extends _Basket {
    public F_Apples(Provider provider) {
        super("apples", new TriggerItem[]{
            new TriggerItem(ItemID.BASKET_APPLE_1).fixedCharges(1),
            new TriggerItem(ItemID.BASKET_APPLE_2).fixedCharges(2),
            new TriggerItem(ItemID.BASKET_APPLE_3).fixedCharges(3),
            new TriggerItem(ItemID.BASKET_APPLE_4).fixedCharges(4),
            new TriggerItem(ItemID.BASKET_APPLE_5).fixedCharges(5),
        }, provider);
    }
}
