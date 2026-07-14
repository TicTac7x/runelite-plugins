package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_GuthixRest extends _Potion {
    public P_GuthixRest(Provider provider) {
        super("guthix_rest", new TriggerItem[]{
            new TriggerItem(ItemID.CUP_GUTHIX_REST_1).fixedCharges(1),
            new TriggerItem(ItemID.CUP_GUTHIX_REST_2).fixedCharges(2),
            new TriggerItem(ItemID.CUP_GUTHIX_REST_3).fixedCharges(3),
            new TriggerItem(ItemID.CUP_GUTHIX_REST_4).fixedCharges(4),
        }, provider);
    }
}
