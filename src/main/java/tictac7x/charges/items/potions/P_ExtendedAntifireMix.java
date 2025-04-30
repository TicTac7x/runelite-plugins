package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_ExtendedAntifireMix extends _Potion {
    public P_ExtendedAntifireMix(final Provider provider) {
        super("extended_antifire_mix", new TriggerItem[]{
            new TriggerItem(ItemId.EXTENDED_ANTIFIRE_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.EXTENDED_ANTIFIRE_MIX_2).fixedCharges(2),
        }, provider);
    }
}
