package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_ZamorakMix extends _Potion {
    public P_ZamorakMix(final Provider provider) {
        super("zamorak_mix", new TriggerItem[]{
            new TriggerItem(ItemId.ZAMORAK_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.ZAMORAK_MIX_2).fixedCharges(2),
        }, provider);
    }
}
