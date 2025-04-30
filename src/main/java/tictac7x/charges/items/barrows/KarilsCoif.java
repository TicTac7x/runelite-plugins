package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class KarilsCoif extends _BarrowsItem {
    public KarilsCoif(final Provider provider) {
        super("Karil's coif", ItemId.KARILS_COIF, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.KARILS_COIF).fixedCharges(1000),
            new TriggerItem(ItemId.KARILS_COIF_100),
            new TriggerItem(ItemId.KARILS_COIF_75),
            new TriggerItem(ItemId.KARILS_COIF_50),
            new TriggerItem(ItemId.KARILS_COIF_25),
            new TriggerItem(ItemId.KARILS_COIF_0).fixedCharges(0)
        };
    }
}