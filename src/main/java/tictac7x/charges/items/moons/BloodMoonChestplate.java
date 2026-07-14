package tictac7x.charges.items.moons;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class BloodMoonChestplate extends _MoonItem {
    public BloodMoonChestplate(
        Provider provider
    ) {
        super("Blood moon chestplate", ItemID.BLOOD_MOON_CHESTPLATE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BLOOD_MOON_CHESTPLATE).fixedCharges(3000),
            new TriggerItem(ItemID.BLOOD_MOON_CHESTPLATE_DEGRADED),
            new TriggerItem(ItemID.BLOOD_MOON_CHESTPLATE_BROKEN).fixedCharges(0),
        };
    }
}