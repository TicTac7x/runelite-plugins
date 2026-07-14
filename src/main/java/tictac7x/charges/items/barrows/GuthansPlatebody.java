package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class GuthansPlatebody extends _BarrowsItem {
    public GuthansPlatebody(Provider provider) {
        super("Guthan's body", ItemId.GUTHANS_PLATEBODY, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.GUTHANS_PLATEBODY).fixedCharges(1000),
            new TriggerItem(ItemId.GUTHANS_PLATEBODY_100),
            new TriggerItem(ItemId.GUTHANS_PLATEBODY_75),
            new TriggerItem(ItemId.GUTHANS_PLATEBODY_50),
            new TriggerItem(ItemId.GUTHANS_PLATEBODY_25),
            new TriggerItem(ItemId.GUTHANS_PLATEBODY_0).fixedCharges(0),
        };
    }
}