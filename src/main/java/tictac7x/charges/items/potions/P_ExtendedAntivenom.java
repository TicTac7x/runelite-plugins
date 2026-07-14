package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_ExtendedAntivenom extends _Potion {
    public P_ExtendedAntivenom(Provider provider) {
        super("extended_antivenom", new TriggerItem[]{
            new TriggerItem(ItemId.EXTENDED_ANTIVENOM_1).fixedCharges(1),
            new TriggerItem(ItemId.EXTENDED_ANTIVENOM_2).fixedCharges(2),
            new TriggerItem(ItemId.EXTENDED_ANTIVENOM_3).fixedCharges(3),
            new TriggerItem(ItemId.EXTENDED_ANTIVENOM_4).fixedCharges(4),
        }, provider);
    }
}
