package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class GuthansWarspear extends _BarrowsItem {
    public GuthansWarspear(Provider provider) {
        super("Guthan's weapon", ItemId.GUTHANS_WARSPEAR, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.GUTHANS_WARSPEAR).fixedCharges(1000),
            new TriggerItem(ItemId.GUTHANS_WARSPEAR_100),
            new TriggerItem(ItemId.GUTHANS_WARSPEAR_75),
            new TriggerItem(ItemId.GUTHANS_WARSPEAR_50),
            new TriggerItem(ItemId.GUTHANS_WARSPEAR_25),
            new TriggerItem(ItemId.GUTHANS_WARSPEAR_0).fixedCharges(0),
        };
    }
}