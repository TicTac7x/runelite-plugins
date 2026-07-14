package tictac7x.charges.items.barrows;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class AhrimsHood extends _BarrowsItem {
    public AhrimsHood(Provider provider) {
        super("Ahrim's hood", ItemId.AHRIMS_HOOD, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.AHRIMS_HOOD).fixedCharges(1000),
            new TriggerItem(ItemId.AHRIMS_HOOD_100),
            new TriggerItem(ItemId.AHRIMS_HOOD_75),
            new TriggerItem(ItemId.AHRIMS_HOOD_50),
            new TriggerItem(ItemId.AHRIMS_HOOD_25),
            new TriggerItem(ItemId.AHRIMS_HOOD_0).fixedCharges(0),
        };
    }
}