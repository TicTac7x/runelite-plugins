package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Antidote extends _Potion {
    public P_Antidote(Provider provider) {
        super("antidote", new TriggerItem[]{
            new TriggerItem(ItemID.ANTIDOTE_1).fixedCharges(1),
            new TriggerItem(ItemID.ANTIDOTE_2).fixedCharges(2),
            new TriggerItem(ItemID.ANTIDOTE_3).fixedCharges(3),
            new TriggerItem(ItemID.ANTIDOTE_4).fixedCharges(4),
        }, provider);
    }
}
