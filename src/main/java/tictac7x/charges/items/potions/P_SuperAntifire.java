package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_SuperAntifire extends _Potion {
    public P_SuperAntifire(Provider provider) {
        super("super_antifire", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_ANTIFIRE_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_ANTIFIRE_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.SUPER_ANTIFIRE_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.SUPER_ANTIFIRE_POTION_4).fixedCharges(4),
        }, provider);
    }
}
