package tictac7x.charges.items.moons;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class EclipseMoonHelm extends _MoonItem {
    public EclipseMoonHelm(
        Provider provider
    ) {
        super("Eclipse moon helm", ItemId.ECLIPSE_MOON_HELM, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ECLIPSE_MOON_HELM).fixedCharges(3000),
            new TriggerItem(ItemId.ECLIPSE_MOON_HELM_DEGRADED),
            new TriggerItem(ItemId.ECLIPSE_MOON_HELM_BROKEN).fixedCharges(0),
        };
    }
}