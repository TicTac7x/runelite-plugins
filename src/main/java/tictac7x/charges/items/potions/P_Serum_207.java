package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class P_Serum_207 extends _Potion {
    public P_Serum_207(Provider provider) {
        super("serum_207", new TriggerItem[]{
            new TriggerItem(ItemId.SERUM_207_1).fixedCharges(1),
            new TriggerItem(ItemId.SERUM_207_2).fixedCharges(2),
            new TriggerItem(ItemId.SERUM_207_3).fixedCharges(3),
            new TriggerItem(ItemId.SERUM_207_4).fixedCharges(4),
        }, provider);
    }
}
