package tictac7x.charges.items.moons;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class EclipseMoonHelm extends _MoonItem {
    public EclipseMoonHelm(
        Provider provider
    ) {
        super("Eclipse moon helm", ItemID.ECLIPSE_MOON_HELM, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ECLIPSE_MOON_HELM).fixedCharges(3000),
            new TriggerItem(ItemID.ECLIPSE_MOON_HELM_DEGRADED),
            new TriggerItem(ItemID.ECLIPSE_MOON_HELM_BROKEN).fixedCharges(0),
        };
    }
}