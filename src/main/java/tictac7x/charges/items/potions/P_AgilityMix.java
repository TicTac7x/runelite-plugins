package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_AgilityMix extends _Potion {
    public P_AgilityMix(Provider provider) {
        super("agility_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSE1AGILITY).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSE1AGILITY).fixedCharges(2),
        }, provider);
    }
}
