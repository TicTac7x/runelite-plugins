package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_MoonlightMothMix extends _Potion {
    public P_MoonlightMothMix(Provider provider) {
        super("moonlight_moth_mix", new TriggerItem[]{
            new TriggerItem(ItemId.MOONLIGHT_MOTH_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.MOONLIGHT_MOTH_MIX_2).fixedCharges(2),
        }, provider);
    }
}
