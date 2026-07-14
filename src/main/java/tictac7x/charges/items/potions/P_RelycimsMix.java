package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_RelycimsMix extends _Potion {
    public P_RelycimsMix(Provider provider) {
        super("relicyms_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_RELICYMS_BALM1).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_RELICYMS_BALM2).fixedCharges(2),
        }, provider);
    }
}
