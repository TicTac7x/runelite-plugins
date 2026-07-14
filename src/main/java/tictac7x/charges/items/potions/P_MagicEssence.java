package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_MagicEssence extends _Potion {
    public P_MagicEssence(Provider provider) {
        super("magic_essence", new TriggerItem[]{
            new TriggerItem(ItemId.MAGIC_ESSENCE_1).fixedCharges(1),
            new TriggerItem(ItemId.MAGIC_ESSENCE_2).fixedCharges(2),
            new TriggerItem(ItemId.MAGIC_ESSENCE_3).fixedCharges(3),
            new TriggerItem(ItemId.MAGIC_ESSENCE_4).fixedCharges(4),
        }, provider);
    }
}
