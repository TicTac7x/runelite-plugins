package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_AntivenomPlus extends _Potion {
    public P_AntivenomPlus(Provider provider) {
        super("antivenom_plus", new TriggerItem[]{
            new TriggerItem(ItemID.ANTIVENOM_1).fixedCharges(1),
            new TriggerItem(ItemID.ANTIVENOM_2).fixedCharges(2),
            new TriggerItem(ItemID.ANTIVENOM_3).fixedCharges(3),
            new TriggerItem(ItemID.ANTIVENOM_4).fixedCharges(4),
        }, provider);
    }
}
