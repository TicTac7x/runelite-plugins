package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_AncientBrewMix extends _Potion {
    public P_AncientBrewMix(Provider provider) {
        super("ancient_brew_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSEANCIENTBREW).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSEANCIENTBREW).fixedCharges(2),
        }, provider);
    }
}
