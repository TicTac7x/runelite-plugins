package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class KarilsLeatherskirt extends _BarrowsItem {
    public KarilsLeatherskirt(final Provider provider) {
        super("Karil's skirt", ItemId.KARILS_LEATHERSKIRT, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT).fixedCharges(1000),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_100),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_75),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_50),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_25),
            new TriggerItem(ItemId.KARILS_LEATHERSKIRT_0).fixedCharges(0)
        };
    }
}
