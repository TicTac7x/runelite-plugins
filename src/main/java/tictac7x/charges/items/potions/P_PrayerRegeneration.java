package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_PrayerRegeneration extends _Potion {
    public P_PrayerRegeneration(final Provider provider) {
        super("prayer_regeneration", new TriggerItem[]{
            new TriggerItem(ItemId.PRAYER_REGENERATION_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.PRAYER_REGENERATION_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.PRAYER_REGENERATION_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.PRAYER_REGENERATION_POTION_4).fixedCharges(4),
        }, provider);
    }
}
