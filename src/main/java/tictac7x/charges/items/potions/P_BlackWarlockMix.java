package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_BlackWarlockMix extends _Potion {
    public P_BlackWarlockMix(Provider provider) {
        super("black_warlock_mix", new TriggerItem[]{
            new TriggerItem(ItemID.HUNTER_MIX_WARLOCK_1DOSE).fixedCharges(1),
            new TriggerItem(ItemID.HUNTER_MIX_WARLOCK_2DOSE).fixedCharges(2),
        }, provider);
    }
}
