package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_MagicMix extends _Potion {
    public P_MagicMix(Provider provider) {
        super("magic_mix", new TriggerItem[]{
            new TriggerItem(ItemId.MAGIC_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.MAGIC_MIX_2).fixedCharges(2),
        }, provider);
    }
}
