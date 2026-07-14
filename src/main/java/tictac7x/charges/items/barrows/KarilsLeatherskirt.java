package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class KarilsLeatherskirt extends _BarrowsItem {
    public KarilsLeatherskirt(Provider provider) {
        super("Karil's skirt", ItemID.BARROWS_KARIL_LEGS, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_KARIL_LEGS).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_KARIL_LEGS_100),
            new TriggerItem(ItemID.BARROWS_KARIL_LEGS_75),
            new TriggerItem(ItemID.BARROWS_KARIL_LEGS_50),
            new TriggerItem(ItemID.BARROWS_KARIL_LEGS_25),
            new TriggerItem(ItemID.BARROWS_KARIL_LEGS_BROKEN).fixedCharges(0)
        };
    }
}
