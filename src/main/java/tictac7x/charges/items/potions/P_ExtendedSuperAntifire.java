package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_ExtendedSuperAntifire extends _Potion {
    public P_ExtendedSuperAntifire(Provider provider) {
        super("extended_super_antifire", new TriggerItem[]{
            new TriggerItem(ItemId.EXTENDED_SUPER_ANTIFIRE_1).fixedCharges(1),
            new TriggerItem(ItemId.EXTENDED_SUPER_ANTIFIRE_2).fixedCharges(2),
            new TriggerItem(ItemId.EXTENDED_SUPER_ANTIFIRE_3).fixedCharges(3),
            new TriggerItem(ItemId.EXTENDED_SUPER_ANTIFIRE_4).fixedCharges(4),
        }, provider);
    }
}
