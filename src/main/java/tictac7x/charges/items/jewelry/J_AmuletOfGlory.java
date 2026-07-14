package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class J_AmuletOfGlory extends ChargedItem {
    public J_AmuletOfGlory(Provider provider) {
        super(TicTac7xChargesImprovedConfig.amulet_of_glory, ItemID.AMULET_OF_GLORY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.AMULET_OF_GLORY).fixedCharges(0),
            new TriggerItem(ItemID.AMULET_OF_GLORY_1).fixedCharges(1),
            new TriggerItem(ItemID.AMULET_OF_GLORY_2).fixedCharges(2),
            new TriggerItem(ItemID.AMULET_OF_GLORY_3).fixedCharges(3),
            new TriggerItem(ItemID.AMULET_OF_GLORY_4).fixedCharges(4),
            new TriggerItem(ItemID.AMULET_OF_GLORY_5).fixedCharges(5),
            new TriggerItem(ItemID.AMULET_OF_GLORY_6).fixedCharges(6),
            new TriggerItem(ItemID.AMULET_OF_GLORY_INF).unlimitedCharges(),
            new TriggerItem(ItemID.TRAIL_AMULET_OF_GLORY).fixedCharges(0),
            new TriggerItem(ItemID.TRAIL_AMULET_OF_GLORY_1).fixedCharges(1),
            new TriggerItem(ItemID.TRAIL_AMULET_OF_GLORY_2).fixedCharges(2),
            new TriggerItem(ItemID.TRAIL_AMULET_OF_GLORY_3).fixedCharges(3),
            new TriggerItem(ItemID.TRAIL_AMULET_OF_GLORY_4).fixedCharges(4),
            new TriggerItem(ItemID.TRAIL_AMULET_OF_GLORY_5).fixedCharges(5),
            new TriggerItem(ItemID.TRAIL_AMULET_OF_GLORY_6).fixedCharges(6),
        };
    }
}
