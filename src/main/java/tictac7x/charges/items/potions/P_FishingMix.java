package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_FishingMix extends _Potion {
    public P_FishingMix(Provider provider) {
        super("fishing_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSEFISHERSPOTION).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSEFISHERSPOTION).fixedCharges(2),
        }, provider);
    }
}
