package tictac7x.charges.items.barrows;

import tictac7x.charges.store.ids.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class KarilsCrossbow extends _BarrowsItem {
    public KarilsCrossbow(Provider provider) {
        super("Karil's weapon", ItemId.KARILS_CROSSBOW, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.KARILS_CROSSBOW).fixedCharges(1000),
            new TriggerItem(ItemId.KARILS_CROSSBOW_100),
            new TriggerItem(ItemId.KARILS_CROSSBOW_75),
            new TriggerItem(ItemId.KARILS_CROSSBOW_50),
            new TriggerItem(ItemId.KARILS_CROSSBOW_25),
            new TriggerItem(ItemId.KARILS_CROSSBOW_0).fixedCharges(0)
        };
    }
}