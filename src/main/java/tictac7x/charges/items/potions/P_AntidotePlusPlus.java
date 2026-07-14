package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_AntidotePlusPlus extends _Potion {
    public P_AntidotePlusPlus(Provider provider) {
        super("antidote_plus_plus", new TriggerItem[]{
            new TriggerItem(ItemID.ANTIDOTE__1).fixedCharges(1),
            new TriggerItem(ItemID.ANTIDOTE__2).fixedCharges(2),
            new TriggerItem(ItemID.ANTIDOTE__3).fixedCharges(3),
            new TriggerItem(ItemID.ANTIDOTE__4).fixedCharges(4),
        }, provider);
    }
}
