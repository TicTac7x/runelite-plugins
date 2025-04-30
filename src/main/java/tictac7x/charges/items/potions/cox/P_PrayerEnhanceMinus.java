package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.items.potions._Potion;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_PrayerEnhanceMinus extends _Potion {
    public P_PrayerEnhanceMinus(final Provider provider) {
        super("cox_prayer_enhance_minus", new TriggerItem[]{
            new TriggerItem(ItemId.COX_PRAYER_ENHANCE_MINUS_1).fixedCharges(1),
            new TriggerItem(ItemId.COX_PRAYER_ENHANCE_MINUS_2).fixedCharges(2),
            new TriggerItem(ItemId.COX_PRAYER_ENHANCE_MINUS_3).fixedCharges(3),
            new TriggerItem(ItemId.COX_PRAYER_ENHANCE_MINUS_4).fixedCharges(4),
        }, provider);
    }
}
