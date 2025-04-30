package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class ToragsPlatebody extends _BarrowsItem {
    public ToragsPlatebody(final Provider provider) {
        super("Torag's body", ItemId.TORAGS_PLATEBODY, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TORAGS_PLATEBODY).fixedCharges(1000),
            new TriggerItem(ItemId.TORAGS_PLATEBODY_100),
            new TriggerItem(ItemId.TORAGS_PLATEBODY_75),
            new TriggerItem(ItemId.TORAGS_PLATEBODY_50),
            new TriggerItem(ItemId.TORAGS_PLATEBODY_25),
            new TriggerItem(ItemId.TORAGS_PLATEBODY_0).fixedCharges(0)
        };
    }
}