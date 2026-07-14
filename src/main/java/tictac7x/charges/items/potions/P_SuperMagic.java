package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.*;

public class P_SuperMagic extends _Potion {
    public P_SuperMagic(Provider provider) {
        super("super_magic", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_MAGIC_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_MAGIC_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.SUPER_MAGIC_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.SUPER_MAGIC_POTION_4).fixedCharges(4),
        }, provider);
    }
}
