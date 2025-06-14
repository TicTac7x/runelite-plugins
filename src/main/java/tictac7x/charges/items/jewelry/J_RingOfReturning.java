package tictac7x.charges.items.jewelry;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class J_RingOfReturning extends ChargedItem {
    public J_RingOfReturning(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_returning, ItemId.RING_OF_RETURNING_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.RING_OF_RETURNING_1).fixedCharges(1),
            new TriggerItem(ItemId.RING_OF_RETURNING_2).fixedCharges(2),
            new TriggerItem(ItemId.RING_OF_RETURNING_3).fixedCharges(3),
            new TriggerItem(ItemId.RING_OF_RETURNING_4).fixedCharges(4),
            new TriggerItem(ItemId.RING_OF_RETURNING_5).fixedCharges(5),
        };
    }
}
