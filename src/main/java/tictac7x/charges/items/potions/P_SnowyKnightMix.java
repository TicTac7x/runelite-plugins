package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_SnowyKnightMix extends _Potion {
    public P_SnowyKnightMix(Provider provider) {
        super("snowy_knight_mix", new TriggerItem[]{
            new TriggerItem(ItemId.SNOWY_KNIGHT_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.SNOWY_KNIGHT_MIX_2).fixedCharges(2),
        }, provider);
    }
}
