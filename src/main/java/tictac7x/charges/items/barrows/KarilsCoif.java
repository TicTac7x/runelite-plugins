package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class KarilsCoif extends _BarrowsItem {
    public KarilsCoif(Provider provider) {
        super("Karil's coif", ItemID.BARROWS_KARIL_HEAD, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_KARIL_HEAD).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_KARIL_HEAD_100),
            new TriggerItem(ItemID.BARROWS_KARIL_HEAD_75),
            new TriggerItem(ItemID.BARROWS_KARIL_HEAD_50),
            new TriggerItem(ItemID.BARROWS_KARIL_HEAD_25),
            new TriggerItem(ItemID.BARROWS_KARIL_HEAD_BROKEN).fixedCharges(0)
        };
    }
}