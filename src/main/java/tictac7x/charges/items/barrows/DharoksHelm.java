package tictac7x.charges.items.barrows;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class DharoksHelm extends _BarrowsItem {
    public DharoksHelm(Provider provider) {
        super("Dharok's helmet", ItemId.DHAROKS_HELM, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.DHAROKS_HELM).fixedCharges(1000),
            new TriggerItem(ItemId.DHAROKS_HELM_100),
            new TriggerItem(ItemId.DHAROKS_HELM_75),
            new TriggerItem(ItemId.DHAROKS_HELM_50),
            new TriggerItem(ItemId.DHAROKS_HELM_25),
            new TriggerItem(ItemId.DHAROKS_HELM_0).fixedCharges(0),
        };
    }
}