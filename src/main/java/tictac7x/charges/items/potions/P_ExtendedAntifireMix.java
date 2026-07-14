package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_ExtendedAntifireMix extends _Potion {
    public P_ExtendedAntifireMix(Provider provider) {
        super("extended_antifire_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE2ANTIDRAGON).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE2ANTIDRAGON).fixedCharges(2),
        }, provider);
    }
}
