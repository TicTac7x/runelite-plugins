package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ToragsPlatebody extends _BarrowsItem {
    public ToragsPlatebody(Provider provider) {
        super("Torag's body", ItemID.BARROWS_TORAG_BODY, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_TORAG_BODY).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_TORAG_BODY_100),
            new TriggerItem(ItemID.BARROWS_TORAG_BODY_75),
            new TriggerItem(ItemID.BARROWS_TORAG_BODY_50),
            new TriggerItem(ItemID.BARROWS_TORAG_BODY_25),
            new TriggerItem(ItemID.BARROWS_TORAG_BODY_BROKEN).fixedCharges(0)
        };
    }
}