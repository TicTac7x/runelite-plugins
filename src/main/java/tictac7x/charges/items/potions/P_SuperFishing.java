package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperFishing extends _Potion {
    public P_SuperFishing(Provider provider) {
        super("super_fishing", new TriggerItem[]{
            new TriggerItem(ItemID._1DOSE2FISHERSPOTION).fixedCharges(1),
            new TriggerItem(ItemID._2DOSE2FISHERSPOTION).fixedCharges(2),
            new TriggerItem(ItemID._3DOSE2FISHERSPOTION).fixedCharges(3),
            new TriggerItem(ItemID._4DOSE2FISHERSPOTION).fixedCharges(4),
        }, provider);
    }
}
