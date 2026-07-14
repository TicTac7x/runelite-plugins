package tictac7x.charges.items.moons;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class BloodMoonTassets extends _MoonItem {
    public BloodMoonTassets(
        Provider provider
    ) {
        super("Blood moon tassets", ItemID.BLOOD_MOON_TASSETS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BLOOD_MOON_TASSETS).fixedCharges(3000),
            new TriggerItem(ItemID.BLOOD_MOON_TASSETS_DEGRADED),
            new TriggerItem(ItemID.BLOOD_MOON_TASSETS_BROKEN).fixedCharges(0),
        };
    }
}