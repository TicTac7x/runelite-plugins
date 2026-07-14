package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperRanging extends _Potion {
    public P_SuperRanging(Provider provider) {
        super("super_ranging", new TriggerItem[]{
            new TriggerItem(ItemID.NZONE1DOSE2RANGERSPOTION).fixedCharges(1),
            new TriggerItem(ItemID.NZONE2DOSE2RANGERSPOTION).fixedCharges(2),
            new TriggerItem(ItemID.NZONE3DOSE2RANGERSPOTION).fixedCharges(3),
            new TriggerItem(ItemID.NZONE4DOSE2RANGERSPOTION).fixedCharges(4),
        }, provider);
    }
}
