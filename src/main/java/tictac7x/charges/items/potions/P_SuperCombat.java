package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_SuperCombat extends _Potion {
    public P_SuperCombat(Provider provider) {
        super("super_combat", new TriggerItem[]{
            new TriggerItem(ItemId.SUPER_COMBAT_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.SUPER_COMBAT_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.SUPER_COMBAT_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.SUPER_COMBAT_POTION_4).fixedCharges(4),
        }, provider);
    }
}
