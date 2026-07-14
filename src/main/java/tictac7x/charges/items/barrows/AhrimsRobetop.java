package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class AhrimsRobetop extends _BarrowsItem {
    public AhrimsRobetop(Provider provider) {
        super("Ahrim's body", ItemID.BARROWS_AHRIM_BODY, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_100),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_75),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_50),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_25),
            new TriggerItem(ItemID.BARROWS_AHRIM_BODY_BROKEN).fixedCharges(0),
        };
    }
}