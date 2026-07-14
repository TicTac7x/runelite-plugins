package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SnowyKnightMix extends _Potion {
    public P_SnowyKnightMix(Provider provider) {
        super("snowy_knight_mix", new TriggerItem[]{
            new TriggerItem(ItemID.HUNTER_MIX_SNOWY_1DOSE).fixedCharges(1),
            new TriggerItem(ItemID.HUNTER_MIX_SNOWY_2DOSE).fixedCharges(2),
        }, provider);
    }
}
