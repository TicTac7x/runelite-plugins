package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class AhrimsHood extends _BarrowsItem {
    public AhrimsHood(Provider provider) {
        super("Ahrim's hood", ItemID.BARROWS_AHRIM_HEAD, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_HEAD_BROKEN).fixedCharges(0),
        };
    }
}