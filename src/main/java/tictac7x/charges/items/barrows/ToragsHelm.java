package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ToragsHelm extends _BarrowsItem {
    public ToragsHelm(Provider provider) {
        super("Torag's helmet", ItemID.BARROWS_TORAG_HEAD, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_TORAG_HEAD).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_TORAG_HEAD_100),
            new TriggerItem(ItemID.BARROWS_TORAG_HEAD_75),
            new TriggerItem(ItemID.BARROWS_TORAG_HEAD_50),
            new TriggerItem(ItemID.BARROWS_TORAG_HEAD_25),
            new TriggerItem(ItemID.BARROWS_TORAG_HEAD_BROKEN).fixedCharges(0)
        };
    }
}