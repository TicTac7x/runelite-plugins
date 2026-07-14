package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class GuthansChainskirt extends _BarrowsItem {
    public GuthansChainskirt(Provider provider) {
        super("Guthan's skirt", ItemID.BARROWS_GUTHAN_LEGS, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_GUTHAN_LEGS).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_GUTHAN_LEGS_100),
            new TriggerItem(ItemID.BARROWS_GUTHAN_LEGS_75),
            new TriggerItem(ItemID.BARROWS_GUTHAN_LEGS_50),
            new TriggerItem(ItemID.BARROWS_GUTHAN_LEGS_25),
            new TriggerItem(ItemID.BARROWS_GUTHAN_LEGS_BROKEN).fixedCharges(0),
        };
    }
}