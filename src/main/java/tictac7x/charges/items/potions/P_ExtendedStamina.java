package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_ExtendedStamina extends _Potion {
    public P_ExtendedStamina(Provider provider) {
        super("extended_stamina", new TriggerItem[]{
            new TriggerItem(ItemId.EXTENDED_STAMINA_POTION_1).fixedCharges(1),
            new TriggerItem(ItemId.EXTENDED_STAMINA_POTION_2).fixedCharges(2),
            new TriggerItem(ItemId.EXTENDED_STAMINA_POTION_3).fixedCharges(3),
            new TriggerItem(ItemId.EXTENDED_STAMINA_POTION_4).fixedCharges(4),
        }, provider);
    }
}
