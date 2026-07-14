package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_PrayerRegeneration extends _Potion {
    public P_PrayerRegeneration(Provider provider) {
        super("prayer_regeneration", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE1PRAYER_REGENERATION).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE1PRAYER_REGENERATION).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE1PRAYER_REGENERATION).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE1PRAYER_REGENERATION).fixedCharges(4),
        }, provider);
    }
}
