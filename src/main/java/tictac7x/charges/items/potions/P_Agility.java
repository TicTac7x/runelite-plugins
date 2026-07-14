package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Agility extends _Potion {
    public P_Agility(Provider provider) {
        super("agility", new TriggerItem[]{
            new TriggerItem(ItemId.AGILITY_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.AGILITY_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.AGILITY_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.AGILITY_POTION_4).fixedCharges(4),
        }, provider);
    }
}
