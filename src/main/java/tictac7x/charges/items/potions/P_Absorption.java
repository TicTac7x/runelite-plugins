package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Absorption extends _Potion {
    public P_Absorption(Provider provider) {
        super("absorption", new TriggerItem[]{
            new TriggerItem(ItemId.ABSORPTION_1).fixedCharges(1),
            new TriggerItem(ItemId.ABSORPTION_2).fixedCharges(2),
            new TriggerItem(ItemId.ABSORPTION_3).fixedCharges(3),
            new TriggerItem(ItemId.ABSORPTION_4).fixedCharges(4),
        }, provider);
    }
}
