package tictac7x.charges.items.potions.toa;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.items.potions._Potion;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_LiquidAdrenaline extends _Potion {
    public P_LiquidAdrenaline(final Provider provider) {
        super("toa_liquid_adrenaline", new TriggerItem[]{
            new TriggerItem(ItemId.TOA_LIQUID_ADRENALINE_1).fixedCharges(1),
            new TriggerItem(ItemId.TOA_LIQUID_ADRENALINE_2).fixedCharges(2),
        }, provider);
    }
}
