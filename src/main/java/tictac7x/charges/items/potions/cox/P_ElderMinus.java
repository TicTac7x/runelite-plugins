package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_ElderMinus extends _Potion {
    public P_ElderMinus(Provider provider) {
        super("cox_elder_minus", new TriggerItem[]{
            new TriggerItem(ItemId.COX_ELDER_MINUS_1).fixedCharges(1),
            new TriggerItem(ItemId.COX_ELDER_MINUS_2).fixedCharges(2),
            new TriggerItem(ItemId.COX_ELDER_MINUS_3).fixedCharges(3),
            new TriggerItem(ItemId.COX_ELDER_MINUS_4).fixedCharges(4),
        }, provider);
    }
}
