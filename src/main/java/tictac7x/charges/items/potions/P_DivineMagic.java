package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_DivineMagic extends _Potion {
    public P_DivineMagic(Provider provider) {
        super("divine_magic", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEDIVINEMAGIC).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEDIVINEMAGIC).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEDIVINEMAGIC).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEDIVINEMAGIC).fixedCharges(4),
        }, provider);
    }
}
