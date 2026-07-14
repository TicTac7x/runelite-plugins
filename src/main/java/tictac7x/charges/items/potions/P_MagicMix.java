package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_MagicMix extends _Potion {
    public P_MagicMix(Provider provider) {
        super("magic_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE1MAGIC).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE1MAGIC).fixedCharges(2),
        }, provider);
    }
}
