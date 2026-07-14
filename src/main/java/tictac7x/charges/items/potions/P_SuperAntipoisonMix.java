package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SuperAntipoisonMix extends _Potion {
    public P_SuperAntipoisonMix(Provider provider) {
        super("super_antipoison_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE2ANTIPOISON).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE2ANTIPOISON).fixedCharges(2),
        }, provider);
    }
}
