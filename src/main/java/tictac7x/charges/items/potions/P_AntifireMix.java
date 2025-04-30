package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_AntifireMix extends _Potion {
    public P_AntifireMix(final Provider provider) {
        super("antifire_mix", new TriggerItem[]{
            new TriggerItem(ItemId.ANTIFIRE_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.ANTIFIRE_MIX_2).fixedCharges(2),
        }, provider);
    }
}
