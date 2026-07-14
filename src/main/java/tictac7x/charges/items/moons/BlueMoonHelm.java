package tictac7x.charges.items.moons;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class BlueMoonHelm extends _MoonItem {
    public BlueMoonHelm(
        Provider provider
    ) {
        super("Blue moon helm", ItemId.BLUE_MOON_HELM, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLUE_MOON_HELM).fixedCharges(3000),
            new TriggerItem(ItemId.BLUE_MOON_HELM_DEGRADED),
            new TriggerItem(ItemId.BLUE_MOON_HELM_BROKEN).fixedCharges(0),
        };
    }
}