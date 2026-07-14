package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SapphireGlacialisMix extends _Potion {
    public P_SapphireGlacialisMix(Provider provider) {
        super("sapphire_glacialis_mix", new TriggerItem[]{
            new TriggerItem(ItemID.HUNTER_MIX_GLACIALIS_1DOSE).fixedCharges(1),
            new TriggerItem(ItemID.HUNTER_MIX_GLACIALIS_2DOSE).fixedCharges(2),
        }, provider);
    }
}
