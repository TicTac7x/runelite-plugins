package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Overload extends _Potion {
    public P_Overload(Provider provider) {
        super("overload", new TriggerItem[]{
            new TriggerItem(ItemID.NZONE1DOSEOVERLOADPOTION).fixedCharges(1),
            new TriggerItem(ItemID.NZONE2DOSEOVERLOADPOTION).fixedCharges(2),
            new TriggerItem(ItemID.NZONE3DOSEOVERLOADPOTION).fixedCharges(3),
            new TriggerItem(ItemID.NZONE4DOSEOVERLOADPOTION).fixedCharges(4),
        }, provider);
    }
}
