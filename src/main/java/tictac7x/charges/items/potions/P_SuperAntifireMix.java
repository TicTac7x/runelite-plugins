package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SuperAntifireMix extends _Potion {
    public P_SuperAntifireMix(Provider provider) {
        super("super_antifire_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE3ANTIDRAGON).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE3ANTIDRAGON).fixedCharges(2),
        }, provider);
    }
}
