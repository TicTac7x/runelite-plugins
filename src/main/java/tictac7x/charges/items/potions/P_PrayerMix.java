package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_PrayerMix extends _Potion {
    public P_PrayerMix(final Provider provider) {
        super("prayer", new TriggerItem[]{
            new TriggerItem(ItemId.PRAYER_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.PRAYER_MIX_2).fixedCharges(2),
        }, provider);
    }
}
