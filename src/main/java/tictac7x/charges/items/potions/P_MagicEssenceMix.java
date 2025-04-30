package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_MagicEssenceMix extends _Potion {
    public P_MagicEssenceMix(final Provider provider) {
        super("magic_essence_mix", new TriggerItem[]{
            new TriggerItem(ItemId.MAGIC_ESSENCE_MIX_1).fixedCharges(1),
            new TriggerItem(ItemId.MAGIC_ESSENCE_MIX_2).fixedCharges(2),
        }, provider);
    }
}
