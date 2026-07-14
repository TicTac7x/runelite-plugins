package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperStrengthMix extends _Potion {
    public P_SuperStrengthMix(Provider provider) {
        super("super_strength_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE2STRENGTH).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE2STRENGTH).fixedCharges(2),
        }, provider);
    }
}
