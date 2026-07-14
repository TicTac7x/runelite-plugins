package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Serum_208 extends _Potion {
    public P_Serum_208(Provider provider) {
        super("serum_208", new TriggerItem[]{
            new TriggerItem(ItemID.MORT_SERUM_PERM1).fixedCharges(1),
            new TriggerItem(ItemID.MORT_SERUM_PERM2).fixedCharges(2),
            new TriggerItem(ItemID.MORT_SERUM_PERM3).fixedCharges(3),
            new TriggerItem(ItemID.MORT_SERUM_PERM4).fixedCharges(4),
        }, provider);
    }
}
