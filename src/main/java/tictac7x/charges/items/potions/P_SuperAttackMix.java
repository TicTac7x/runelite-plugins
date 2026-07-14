package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SuperAttackMix extends _Potion {
    public P_SuperAttackMix(Provider provider) {
        super("super_attack_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE2ATTACK).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE2ATTACK).fixedCharges(2),
        }, provider);
    }
}
