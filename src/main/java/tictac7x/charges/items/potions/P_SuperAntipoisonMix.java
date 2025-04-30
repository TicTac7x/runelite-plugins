package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_SuperAntipoisonMix extends _Potion {
    public P_SuperAntipoisonMix(final Provider provider) {
        super("super_antipoison_mix", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_ANTIPOISON_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_ANTIPOISON_MIX_2).fixedCharges(2),
        }, provider);
    }
}
