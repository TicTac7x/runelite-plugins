package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class J_AmuletOfGlory extends ChargedItem {
    public J_AmuletOfGlory(Provider provider) {
        super(TicTac7xChargesImprovedConfig.amulet_of_glory, ItemId.AMULET_OF_GLORY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.AMULET_OF_GLORY).fixedCharges(0),
            new TriggerItem(ItemId.AMULET_OF_GLORY_1).fixedCharges(1),
            new TriggerItem(ItemId.AMULET_OF_GLORY_2).fixedCharges(2),
            new TriggerItem(ItemId.AMULET_OF_GLORY_3).fixedCharges(3),
            new TriggerItem(ItemId.AMULET_OF_GLORY_4).fixedCharges(4),
            new TriggerItem(ItemId.AMULET_OF_GLORY_5).fixedCharges(5),
            new TriggerItem(ItemId.AMULET_OF_GLORY_6).fixedCharges(6),
            new TriggerItem(ItemId.AMULET_OF_GLORY_ETERNAL).unlimitedCharges(),
            new TriggerItem(ItemId.AMULET_OF_GLORY_TRIMMED).fixedCharges(0),
            new TriggerItem(ItemId.AMULET_OF_GLORY_TRIMMED_1).fixedCharges(1),
            new TriggerItem(ItemId.AMULET_OF_GLORY_TRIMMED_2).fixedCharges(2),
            new TriggerItem(ItemId.AMULET_OF_GLORY_TRIMMED_3).fixedCharges(3),
            new TriggerItem(ItemId.AMULET_OF_GLORY_TRIMMED_4).fixedCharges(4),
            new TriggerItem(ItemId.AMULET_OF_GLORY_TRIMMED_5).fixedCharges(5),
            new TriggerItem(ItemId.AMULET_OF_GLORY_TRIMMED_6).fixedCharges(6),
        };
    }
}
