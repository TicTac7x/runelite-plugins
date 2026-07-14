package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Ranging extends _Potion {
    public P_Ranging(Provider provider) {
        super("ranging", new TriggerItem[]{
            new TriggerItem(ItemId.RANGING_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.RANGING_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.RANGING_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.RANGING_POTION_4).fixedCharges(4),
        }, provider);
    }
}
