package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_SuperAntifireMix extends _Potion {
    public P_SuperAntifireMix(Provider provider) {
        super("super_antifire_mix", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_ANTIFIRE_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_ANTIFIRE_MIX_2).fixedCharges(2),
        }, provider);
    }
}
