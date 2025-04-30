package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_Stamina extends _Potion {
    public P_Stamina(final Provider provider) {
        super("stamina", new TriggerItem[]{
            new TriggerItem(ItemId.STAMINA_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.STAMINA_POTION_1).fixedCharges(2),
            new TriggerItem(ItemId.STAMINA_POTION_1).fixedCharges(3),
            new TriggerItem(ItemId.STAMINA_POTION_1).fixedCharges(4),
        }, provider);
    }
}
