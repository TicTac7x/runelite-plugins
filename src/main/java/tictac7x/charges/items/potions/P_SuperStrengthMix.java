package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.*;

public class P_SuperStrengthMix extends _Potion {
    public P_SuperStrengthMix(Provider provider) {
        super("super_strength_mix", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_STRENGTH_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_STRENGTH_MIX_2).fixedCharges(2),
        }, provider);
    }
}
