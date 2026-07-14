package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_ExtendedSuperAntifireMix extends _Potion {
    public P_ExtendedSuperAntifireMix(Provider provider) {
        super("extended_super_antifire_mix", new TriggerItem[]{
            new TriggerItem(ItemId.EXTENDED_SUPER_ANTIFIRE_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.EXTENDED_SUPER_ANTIFIRE_MIX_2).fixedCharges(2),
        }, provider);
    }
}
