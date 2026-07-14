package tictac7x.charges.items.moons;

import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class BloodMoonHelm extends _MoonItem {
    public BloodMoonHelm(
        Provider provider
    ) {
        super("Blood moon helm", ItemId.BLOOD_MOON_HELM, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLOOD_MOON_HELM).fixedCharges(3000),
            new TriggerItem(ItemId.BLOOD_MOON_HELM_DEGRADED),
            new TriggerItem(ItemId.BLOOD_MOON_HELM_BROKEN).fixedCharges(0),
        };
    }
}