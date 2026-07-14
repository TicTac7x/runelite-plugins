package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Twisted extends _Potion {
    public P_Twisted(Provider provider) {
        super("cox_twisted", new TriggerItem[]{
            new TriggerItem(ItemId.COX_TWISTED_1).fixedCharges(1),
            new TriggerItem(ItemId.COX_TWISTED_2).fixedCharges(2),
            new TriggerItem(ItemId.COX_TWISTED_3).fixedCharges(3),
            new TriggerItem(ItemId.COX_TWISTED_4).fixedCharges(4),
        }, provider);
    }
}
