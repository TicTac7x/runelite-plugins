package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.items.potions.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_LiquidAdrenaline extends _Potion {
    public P_LiquidAdrenaline(Provider provider) {
        super("toa_liquid_adrenaline", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_LIQUID_ADRENALINE_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_LIQUID_ADRENALINE_2).fixedCharges(2),
        }, provider);
    }
}
