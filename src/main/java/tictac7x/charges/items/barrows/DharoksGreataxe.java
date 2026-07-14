package tictac7x.charges.items.barrows;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class DharoksGreataxe extends _BarrowsItem {
    public DharoksGreataxe(Provider provider) {
        super("Dharok's weapon", ItemId.DHAROKS_GREATAXE, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.DHAROKS_GREATAXE).fixedCharges(1000),
            new TriggerItem(ItemId.DHAROKS_GREATAXE_100),
            new TriggerItem(ItemId.DHAROKS_GREATAXE_75),
            new TriggerItem(ItemId.DHAROKS_GREATAXE_50),
            new TriggerItem(ItemId.DHAROKS_GREATAXE_25),
            new TriggerItem(ItemId.DHAROKS_GREATAXE_0).fixedCharges(0),
        };
    }
}