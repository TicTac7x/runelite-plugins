package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class KarilsLeathertop extends _BarrowsItem {
    public KarilsLeathertop(Provider provider) {
        super("Karil's body", ItemID.BARROWS_KARIL_BODY, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_KARIL_BODY).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_KARIL_BODY_100),
            new TriggerItem(ItemID.BARROWS_KARIL_BODY_75),
            new TriggerItem(ItemID.BARROWS_KARIL_BODY_50),
            new TriggerItem(ItemID.BARROWS_KARIL_BODY_25),
            new TriggerItem(ItemID.BARROWS_KARIL_BODY_BROKEN).fixedCharges(0)
        };
    }
}
