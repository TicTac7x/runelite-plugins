package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class DharoksPlatelegs extends _BarrowsItem {
    public DharoksPlatelegs(Provider provider) {
        super("Dharok's legs", ItemID.BARROWS_DHAROK_LEGS, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_DHAROK_LEGS).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_DHAROK_LEGS_100),
            new TriggerItem(ItemID.BARROWS_DHAROK_LEGS_75),
            new TriggerItem(ItemID.BARROWS_DHAROK_LEGS_50),
            new TriggerItem(ItemID.BARROWS_DHAROK_LEGS_25),
            new TriggerItem(ItemID.BARROWS_DHAROK_LEGS_BROKEN).fixedCharges(0),
        };
    }
}