package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class GuthansHelm extends _BarrowsItem {
    public GuthansHelm(Provider provider) {
        super("Guthan's helmet", ItemID.BARROWS_GUTHAN_HEAD, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_GUTHAN_HEAD).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_GUTHAN_HEAD_100),
            new TriggerItem(ItemID.BARROWS_GUTHAN_HEAD_75),
            new TriggerItem(ItemID.BARROWS_GUTHAN_HEAD_50),
            new TriggerItem(ItemID.BARROWS_GUTHAN_HEAD_25),
            new TriggerItem(ItemID.BARROWS_GUTHAN_HEAD_BROKEN).fixedCharges(0),
        };
    }
}