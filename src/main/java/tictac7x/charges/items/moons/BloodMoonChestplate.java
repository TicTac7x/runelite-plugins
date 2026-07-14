package tictac7x.charges.items.moons;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class BloodMoonChestplate extends _MoonItem {
    public BloodMoonChestplate(
        Provider provider
    ) {
        super("Blood moon chestplate", ItemId.BLOOD_MOON_CHESTPLATE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLOOD_MOON_CHESTPLATE).fixedCharges(3000),
            new TriggerItem(ItemId.BLOOD_MOON_CHESTPLATE_DEGRADED),
            new TriggerItem(ItemId.BLOOD_MOON_CHESTPLATE_BROKEN).fixedCharges(0),
        };
    }
}