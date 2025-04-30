package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

public class J_RingOfDueling extends ChargedItem {
    public J_RingOfDueling(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_dueling, ItemId.RING_OF_DUELING_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.RING_OF_DUELING_1).fixedCharges(1),
            new TriggerItem(ItemId.RING_OF_DUELING_2).fixedCharges(2),
            new TriggerItem(ItemId.RING_OF_DUELING_3).fixedCharges(3),
            new TriggerItem(ItemId.RING_OF_DUELING_4).fixedCharges(4),
            new TriggerItem(ItemId.RING_OF_DUELING_5).fixedCharges(5),
            new TriggerItem(ItemId.RING_OF_DUELING_6).fixedCharges(6),
            new TriggerItem(ItemId.RING_OF_DUELING_7).fixedCharges(7),
            new TriggerItem(ItemId.RING_OF_DUELING_8).fixedCharges(8),
        };
    }
}
