package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_HuntingMix extends _Potion {
    public P_HuntingMix(Provider provider) {
        super("hunting_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE1HUNTING).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE1HUNTING).fixedCharges(2),
        }, provider);
    }
}
