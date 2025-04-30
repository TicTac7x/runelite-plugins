package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class GuthansChainskirt extends _BarrowsItem {
    public GuthansChainskirt(final Provider provider) {
        super("Guthan's skirt", ItemId.GUTHANS_CHAINSKIRT, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.GUTHANS_CHAINSKIRT).fixedCharges(1000),
            new TriggerItem(ItemId.GUTHANS_CHAINSKIRT_100),
            new TriggerItem(ItemId.GUTHANS_CHAINSKIRT_75),
            new TriggerItem(ItemId.GUTHANS_CHAINSKIRT_50),
            new TriggerItem(ItemId.GUTHANS_CHAINSKIRT_25),
            new TriggerItem(ItemId.GUTHANS_CHAINSKIRT_0).fixedCharges(0),
        };
    }
}