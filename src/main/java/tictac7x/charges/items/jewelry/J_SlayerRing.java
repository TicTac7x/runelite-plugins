package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_SlayerRing extends ChargedItem {
    public J_SlayerRing(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.slayer_ring, ItemId.SLAYER_RING_8, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SLAYER_RING_1).fixedCharges(1),
            new TriggerItem(ItemId.SLAYER_RING_2).fixedCharges(2),
            new TriggerItem(ItemId.SLAYER_RING_3).fixedCharges(3),
            new TriggerItem(ItemId.SLAYER_RING_4).fixedCharges(4),
            new TriggerItem(ItemId.SLAYER_RING_5).fixedCharges(5),
            new TriggerItem(ItemId.SLAYER_RING_6).fixedCharges(6),
            new TriggerItem(ItemId.SLAYER_RING_7).fixedCharges(7),
            new TriggerItem(ItemId.SLAYER_RING_8).fixedCharges(8),
        };
    }
}
