package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_HaemostaticDressing extends _Potion{
    public P_HaemostaticDressing(Provider provider){
        super("haemostatic_dressing", new TriggerItem[]{
                new TriggerItem(ItemID._1DOSEHAEMOSTATICDRESSING).fixedCharges(1),
                new TriggerItem(ItemID._2DOSEHAEMOSTATICDRESSING).fixedCharges(2),
                new TriggerItem(ItemID._3DOSEHAEMOSTATICDRESSING).fixedCharges(3),
                new TriggerItem(ItemID._4DOSEHAEMOSTATICDRESSING).fixedCharges(4),
        }, provider);
    }
}
