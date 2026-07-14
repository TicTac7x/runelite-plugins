package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.*;

public class P_ZamorakMix extends _Potion {
    public P_ZamorakMix(Provider provider) {
        super("zamorak_mix", new TriggerItem[]{
            new TriggerItem(ItemID.BRUTAL_1DOSEPOTIONOFZAMORAK).fixedCharges(1),
            new TriggerItem(ItemID.BRUTAL_2DOSEPOTIONOFZAMORAK).fixedCharges(2),
        }, provider);
    }
}
