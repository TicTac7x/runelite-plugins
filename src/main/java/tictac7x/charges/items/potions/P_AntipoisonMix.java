package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;
public class P_AntipoisonMix extends _Potion {
    public P_AntipoisonMix(Provider provider) {
        super("antipoison_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSEANTIPOISON).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSEANTIPOISON).fixedCharges(2),
        }, provider);
    }
}
