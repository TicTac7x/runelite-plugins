package tictac7x.charges.items.moons;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class EclipseMoonChestplate extends _MoonItem {
    public EclipseMoonChestplate(
        Provider provider
    ) {
        super("Eclipse moon chestplate", ItemId.ECLIPSE_MOON_CHESTPLATE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ECLIPSE_MOON_CHESTPLATE).fixedCharges(3000),
            new TriggerItem(ItemId.ECLIPSE_MOON_CHESTPLATE_DEGRADED),
            new TriggerItem(ItemId.ECLIPSE_MOON_CHESTPLATE_BROKEN).fixedCharges(0),
        };
    }
}