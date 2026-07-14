package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_DefenceMix extends _Potion {
    public P_DefenceMix(Provider provider) {
        super("defence_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE1DEFENSE).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE1DEFENSE).fixedCharges(2),
        }, provider);
    }
}
