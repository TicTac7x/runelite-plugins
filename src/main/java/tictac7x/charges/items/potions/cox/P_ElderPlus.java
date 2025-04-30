package tictac7x.charges.items.potions.cox;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.items.potions._Potion;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_ElderPlus extends _Potion {
    public P_ElderPlus(final Provider provider) {
        super("cox_elder_plus", new TriggerItem[]{
            new TriggerItem(ItemId.COX_ELDER_PLUS_1).fixedCharges(1),
            new TriggerItem(ItemId.COX_ELDER_PLUS_2).fixedCharges(2),
            new TriggerItem(ItemId.COX_ELDER_PLUS_3).fixedCharges(3),
            new TriggerItem(ItemId.COX_ELDER_PLUS_4).fixedCharges(4),
        }, provider);
    }
}
