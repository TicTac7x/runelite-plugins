package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class ToragsPlatelegs extends _BarrowsItem {
    public ToragsPlatelegs(final Provider provider) {
        super("Torag's legs", ItemId.TORAGS_PLATELEGS, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TORAGS_PLATELEGS).fixedCharges(1000),
            new TriggerItem(ItemId.TORAGS_PLATELEGS_100),
            new TriggerItem(ItemId.TORAGS_PLATELEGS_75),
            new TriggerItem(ItemId.TORAGS_PLATELEGS_50),
            new TriggerItem(ItemId.TORAGS_PLATELEGS_25),
            new TriggerItem(ItemId.TORAGS_PLATELEGS_0).fixedCharges(0)
        };
    }
}