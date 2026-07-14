package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_StaminaMix extends _Potion {
    public P_StaminaMix(Provider provider) {
        super("stamina_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSESTAMINA).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSESTAMINA).fixedCharges(2),
        }, provider);
    }
}
