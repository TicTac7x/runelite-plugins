package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_AntidoteMix extends _Potion {
    public P_AntidoteMix(Provider provider) {
        super("antidote_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_ANTIDOTE_1).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_ANTIDOTE_2).fixedCharges(2),
        }, provider);
    }
}
