package tictac7x.charges.items.moons;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class EclipseMoonTassets extends _MoonItem {
    public EclipseMoonTassets(
        Provider provider
    ) {
        super("Eclipse moon tassets", ItemID.ECLIPSE_MOON_TASSETS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ECLIPSE_MOON_TASSETS).fixedCharges(3000),
            new TriggerItem(ItemID.ECLIPSE_MOON_TASSETS_DEGRADED),
            new TriggerItem(ItemID.ECLIPSE_MOON_TASSETS_BROKEN).fixedCharges(0),
        };
    }
}