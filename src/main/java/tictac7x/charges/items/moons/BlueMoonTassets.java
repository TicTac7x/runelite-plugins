package tictac7x.charges.items.moons;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class BlueMoonTassets extends _MoonItem {
    public BlueMoonTassets(
        Provider provider
    ) {
        super("Blue moon tassets", ItemID.FROST_MOON_TASSETS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FROST_MOON_TASSETS).fixedCharges(3000),
            new TriggerItem(ItemID.FROST_MOON_TASSETS_DEGRADED),
            new TriggerItem(ItemID.FROST_MOON_TASSETS_BROKEN).fixedCharges(0),
        };
    }
}