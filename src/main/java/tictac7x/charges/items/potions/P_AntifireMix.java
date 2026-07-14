package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_AntifireMix extends _Potion {
    public P_AntifireMix(Provider provider) {
        super("antifire_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE1ANTIDRAGON).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE1ANTIDRAGON).fixedCharges(2),
        }, provider);
    }
}
