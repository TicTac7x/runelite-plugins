package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_HaemostaticDressing extends _Potion{
    public P_HaemostaticDressing(Provider provider){
        super("haemostatic_dressing", new TriggerItem[]{
                new TriggerItem(ItemId.HAEMOSTATIC_DRESSING_1).fixedCharges(1),
                new TriggerItem(ItemId.HAEMOSTATIC_DRESSING_2).fixedCharges(2),
                new TriggerItem(ItemId.HAEMOSTATIC_DRESSING_3).fixedCharges(3),
                new TriggerItem(ItemId.HAEMOSTATIC_DRESSING_4).fixedCharges(4),
        }, provider);
    }
}
