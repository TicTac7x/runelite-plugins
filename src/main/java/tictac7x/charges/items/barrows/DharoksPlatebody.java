package tictac7x.charges.items.barrows;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class DharoksPlatebody extends _BarrowsItem {
    public DharoksPlatebody(Provider provider) {
        super("Dharok's body", ItemId.DHAROKS_PLATEBODY, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.DHAROKS_PLATEBODY).fixedCharges(1000),
            new TriggerItem(ItemId.DHAROKS_PLATEBODY_100),
            new TriggerItem(ItemId.DHAROKS_PLATEBODY_75),
            new TriggerItem(ItemId.DHAROKS_PLATEBODY_50),
            new TriggerItem(ItemId.DHAROKS_PLATEBODY_25),
            new TriggerItem(ItemId.DHAROKS_PLATEBODY_0).fixedCharges(0),
        };
    }
}