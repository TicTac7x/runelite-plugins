package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class GuthansHelm extends _BarrowsItem {
    public GuthansHelm(final Provider provider) {
        super("Guthan's helmet", ItemId.GUTHANS_HELM, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.GUTHANS_HELM).fixedCharges(1000),
            new TriggerItem(ItemId.GUTHANS_HELM_100),
            new TriggerItem(ItemId.GUTHANS_HELM_75),
            new TriggerItem(ItemId.GUTHANS_HELM_50),
            new TriggerItem(ItemId.GUTHANS_HELM_25),
            new TriggerItem(ItemId.GUTHANS_HELM_0).fixedCharges(0),
        };
    }
}