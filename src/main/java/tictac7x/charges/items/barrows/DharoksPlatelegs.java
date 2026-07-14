package tictac7x.charges.items.barrows;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class DharoksPlatelegs extends _BarrowsItem {
    public DharoksPlatelegs(Provider provider) {
        super("Dharok's legs", ItemId.DHAROKS_PLATELEGS, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.DHAROKS_PLATELEGS).fixedCharges(1000),
            new TriggerItem(ItemId.DHAROKS_PLATELEGS_100),
            new TriggerItem(ItemId.DHAROKS_PLATELEGS_75),
            new TriggerItem(ItemId.DHAROKS_PLATELEGS_50),
            new TriggerItem(ItemId.DHAROKS_PLATELEGS_25),
            new TriggerItem(ItemId.DHAROKS_PLATELEGS_0).fixedCharges(0),
        };
    }
}