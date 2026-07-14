package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_MagicEssenceMix extends _Potion {
    public P_MagicEssenceMix(Provider provider) {
        super("magic_essence_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSEMAGICESS).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSEMAGICESS).fixedCharges(2),
        }, provider);
    }
}
