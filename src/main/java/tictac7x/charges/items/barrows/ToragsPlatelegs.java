package tictac7x.charges.items.barrows;

import net.runelite.api.gameval.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class ToragsPlatelegs extends _BarrowsItem {
    public ToragsPlatelegs(Provider provider) {
        super("Torag's legs", ItemID.BARROWS_TORAG_LEGS, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.BARROWS_TORAG_LEGS).fixedCharges(1000),
            new TriggerItem(ItemID.BARROWS_TORAG_LEGS_100),
            new TriggerItem(ItemID.BARROWS_TORAG_LEGS_75),
            new TriggerItem(ItemID.BARROWS_TORAG_LEGS_50),
            new TriggerItem(ItemID.BARROWS_TORAG_LEGS_25),
            new TriggerItem(ItemID.BARROWS_TORAG_LEGS_BROKEN).fixedCharges(0)
        };
    }
}