package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Prayer extends _Potion {
    public P_Prayer(Provider provider) {
        super("prayer", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEPRAYERRESTORE).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEPRAYERRESTORE).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEPRAYERRESTORE).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEPRAYERRESTORE).fixedCharges(4),
        }, provider);
    }
}
