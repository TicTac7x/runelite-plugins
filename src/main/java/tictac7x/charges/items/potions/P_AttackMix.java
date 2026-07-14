package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_AttackMix extends _Potion {
    public P_AttackMix(Provider provider) {
        super("attack_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE1ATTACK).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE1ATTACK).fixedCharges(2),
        }, provider);
    }
}
