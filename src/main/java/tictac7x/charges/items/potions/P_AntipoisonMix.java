package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;
public class P_AntipoisonMix extends _Potion {
    public P_AntipoisonMix(Provider provider) {
        super("antipoison_mix", new TriggerItem[]{
            new TriggerItem(ItemId.ANTIPOISON_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.ANTIPOISON_MIX_2).fixedCharges(2),
        }, provider);
    }
}
