package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_CombatMix extends _Potion {
    public P_CombatMix(Provider provider) {
        super("combat_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSECOMBAT).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSECOMBAT).fixedCharges(2),
        }, provider);
    }
}
