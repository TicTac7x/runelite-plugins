package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Agility extends _Potion {
    public P_Agility(Provider provider) {
        super("agility", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE1AGILITY).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE1AGILITY).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE1AGILITY).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE1AGILITY).fixedCharges(4),
        }, provider);
    }
}
