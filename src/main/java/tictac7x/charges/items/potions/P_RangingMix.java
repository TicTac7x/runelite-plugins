package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_RangingMix extends _Potion {
    public P_RangingMix(Provider provider) {
        super("ranging_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSERANGERSPOTION).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSERANGERSPOTION).fixedCharges(2),
        }, provider);
    }
}
