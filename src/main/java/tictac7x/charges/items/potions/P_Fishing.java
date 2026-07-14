package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Fishing extends _Potion {
    public P_Fishing(Provider provider) {
        super("fishing", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSEFISHERSPOTION).fixedCharges(1),
            new TriggerItem(ItemID._2DOSEFISHERSPOTION).fixedCharges(2),
            new TriggerItem(ItemID._3DOSEFISHERSPOTION).fixedCharges(3),
            new TriggerItem(ItemID._4DOSEFISHERSPOTION).fixedCharges(4),
        }, provider);
    }
}
