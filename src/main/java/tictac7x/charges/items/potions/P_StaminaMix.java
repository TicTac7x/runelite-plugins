package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_StaminaMix extends _Potion {
    public P_StaminaMix(Provider provider) {
        super("stamina_mix", new TriggerItem[]{
            new TriggerItem(ItemId.STAMINA_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.STAMINA_MIX_2).fixedCharges(2),
        }, provider);
    }
}
