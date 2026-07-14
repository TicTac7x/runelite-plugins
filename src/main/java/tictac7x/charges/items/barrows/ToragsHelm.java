package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ToragsHelm extends _BarrowsItem {
    public ToragsHelm(Provider provider) {
        super("Torag's helmet", ItemId.TORAGS_HELM, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TORAGS_HELM).fixedCharges(1000),
            new TriggerItem(ItemId.TORAGS_HELM_100),
            new TriggerItem(ItemId.TORAGS_HELM_75),
            new TriggerItem(ItemId.TORAGS_HELM_50),
            new TriggerItem(ItemId.TORAGS_HELM_25),
            new TriggerItem(ItemId.TORAGS_HELM_0).fixedCharges(0)
        };
    }
}