package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_PrayerEnhance extends _Potion {
    public P_PrayerEnhance(Provider provider) {
        super("cox_prayer_enhance", new TriggerItem[]{
            new TriggerItem(ItemID.RAIDS_VIAL_PRAYER_1).fixedCharges(1),
            new TriggerItem(ItemID.RAIDS_VIAL_PRAYER_2).fixedCharges(2),
            new TriggerItem(ItemID.RAIDS_VIAL_PRAYER_3).fixedCharges(3),
            new TriggerItem(ItemID.RAIDS_VIAL_PRAYER_4).fixedCharges(4),
        }, provider);
    }
}
