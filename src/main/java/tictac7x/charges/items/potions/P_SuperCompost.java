package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_SuperCompost extends _Potion {
    public P_SuperCompost(Provider provider) {
        super("super_compost", new TriggerItem[]{
            new TriggerItem(ItemID.SUPERCOMPOST_POTION_1).fixedCharges(1),
            new TriggerItem(ItemID.SUPERCOMPOST_POTION_2).fixedCharges(2),
            new TriggerItem(ItemID.SUPERCOMPOST_POTION_3).fixedCharges(3),
            new TriggerItem(ItemID.SUPERCOMPOST_POTION_4).fixedCharges(4),
        }, provider);
    }
}
