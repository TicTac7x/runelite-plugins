package tictac7x.charges.items.moons;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class BloodMoonTassets extends _MoonItem {
    public BloodMoonTassets(
        Provider provider
    ) {
        super("Blood moon tassets", ItemId.BLOOD_MOON_TASSETS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLOOD_MOON_TASSETS).fixedCharges(3000),
            new TriggerItem(ItemId.BLOOD_MOON_TASSETS_DEGRADED),
            new TriggerItem(ItemId.BLOOD_MOON_TASSETS_BROKEN).fixedCharges(0),
        };
    }
}