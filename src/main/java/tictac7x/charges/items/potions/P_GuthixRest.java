package tictac7x.charges.items.potions;

import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class P_GuthixRest extends _Potion {
    public P_GuthixRest(final Provider provider) {
        super("guthix_rest", new TriggerItem[]{
            new TriggerItem(ItemId.GUTHIX_REST_1).fixedCharges(1),
            new TriggerItem(ItemId.GUTHIX_REST_2).fixedCharges(2),
            new TriggerItem(ItemId.GUTHIX_REST_3).fixedCharges(3),
            new TriggerItem(ItemId.GUTHIX_REST_4).fixedCharges(4),
        }, provider);
    }
}
