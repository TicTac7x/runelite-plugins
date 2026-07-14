package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_StrengthMix extends _Potion {
    public P_StrengthMix(Provider provider) {
        super("strength_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE1STRENGTH).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE1STRENGTH).fixedCharges(2),
        }, provider);
    }
}
