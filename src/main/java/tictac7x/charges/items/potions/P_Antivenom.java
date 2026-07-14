package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Antivenom extends _Potion {
    public P_Antivenom(Provider provider) {
        super("antivenom", new TriggerItem[]{
            new TriggerItem(ItemId.ANTIVENOM_1).fixedCharges(1),
            new TriggerItem(ItemId.ANTIVENOM_2).fixedCharges(2),
            new TriggerItem(ItemId.ANTIVENOM_3).fixedCharges(3),
            new TriggerItem(ItemId.ANTIVENOM_4).fixedCharges(4),
        }, provider);
    }
}
