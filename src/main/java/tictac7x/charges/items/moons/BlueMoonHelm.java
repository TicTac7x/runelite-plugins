package tictac7x.charges.items.moons;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class BlueMoonHelm extends _MoonItem {
    public BlueMoonHelm(
        Provider provider
    ) {
        super("Blue moon helm", ItemID.FROST_MOON_HELM, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.FROST_MOON_HELM).fixedCharges(3000),
            new TriggerItem(ItemID.FROST_MOON_HELM_DEGRADED),
            new TriggerItem(ItemID.FROST_MOON_HELM_BROKEN).fixedCharges(0),
        };
    }
}