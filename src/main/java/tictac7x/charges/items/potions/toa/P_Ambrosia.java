package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

public class P_Ambrosia extends _Potion {
    public P_Ambrosia(Provider provider) {
        super("toa_ambrosia", new TriggerItem[]{
            new TriggerItem(ItemID.TOA_SUPPLY_PANICHEAL_1).fixedCharges(1),
            new TriggerItem(ItemID.TOA_SUPPLY_PANICHEAL_2).fixedCharges(2),
        }, provider);
    }
}
