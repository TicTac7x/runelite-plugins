package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_PrayerMix extends _Potion {
    public P_PrayerMix(Provider provider) {
        super("prayer", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSEPRAYERRESTORE).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSEPRAYERRESTORE).fixedCharges(2),
        }, provider);
    }
}
