package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_SuperDefenceMix extends _Potion {
    public P_SuperDefenceMix(Provider provider) {
        super("super_defence_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE2DEFENSE).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE2DEFENSE).fixedCharges(2),
        }, provider);
    }
}
