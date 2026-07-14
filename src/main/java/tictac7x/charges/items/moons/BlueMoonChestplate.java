package tictac7x.charges.items.moons;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class BlueMoonChestplate extends _MoonItem {
    public BlueMoonChestplate(
        Provider provider
    ) {
        super("Blue moon chestplate", ItemID.FROST_MOON_CHESTPLATE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FROST_MOON_CHESTPLATE).fixedCharges(3000),
            new TriggerItem(ItemID.FROST_MOON_CHESTPLATE_DEGRADED),
            new TriggerItem(ItemID.FROST_MOON_CHESTPLATE_BROKEN).fixedCharges(0),
        };
    }
}