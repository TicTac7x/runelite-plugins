package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_ExtendedSuperAntifireMix extends _Potion {
    public P_ExtendedSuperAntifireMix(Provider provider) {
        super("extended_super_antifire_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE4ANTIDRAGON).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE4ANTIDRAGON).fixedCharges(2),
        }, provider);
    }
}
