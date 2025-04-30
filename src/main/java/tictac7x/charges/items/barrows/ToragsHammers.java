package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class ToragsHammers extends _BarrowsItem {
    public ToragsHammers(final Provider provider) {
        super("Torag's weapon", ItemId.TORAGS_HAMMERS, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TORAGS_HAMMERS).fixedCharges(1000),
            new TriggerItem(ItemId.TORAGS_HAMMERS_100),
            new TriggerItem(ItemId.TORAGS_HAMMERS_75),
            new TriggerItem(ItemId.TORAGS_HAMMERS_50),
            new TriggerItem(ItemId.TORAGS_HAMMERS_25),
            new TriggerItem(ItemId.TORAGS_HAMMERS_0).fixedCharges(0)
        };
    }
}