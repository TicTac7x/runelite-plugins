package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_SapphireGlacialisMix extends _Potion {
    public P_SapphireGlacialisMix(Provider provider) {
        super("sapphire_glacialis_mix", new TriggerItem[]{
            new TriggerItem(ItemId.SAPPHIRE_GLACIALIS_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.SAPPHIRE_GLACIALIS_MIX_2).fixedCharges(2),
        }, provider);
    }
}
