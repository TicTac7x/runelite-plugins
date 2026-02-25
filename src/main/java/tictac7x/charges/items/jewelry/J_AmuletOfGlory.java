package tictac7x.charges.items.jewelry;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ids.ChargeId;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.Provider;

public class J_AmuletOfGlory extends ChargedItem {
    public J_AmuletOfGlory(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.amulet_of_glory, ItemId.AMULET_OF_GLORY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.AMULET_OF_GLORY).fixedCharges(0),
            new TriggerItem(ItemId.AMULET_OF_GLORY_1).fixedCharges(1),
            new TriggerItem(ItemId.AMULET_OF_GLORY_2).fixedCharges(2),
            new TriggerItem(ItemId.AMULET_OF_GLORY_3).fixedCharges(3),
            new TriggerItem(ItemId.AMULET_OF_GLORY_4).fixedCharges(4),
            new TriggerItem(ItemId.AMULET_OF_GLORY_5).fixedCharges(5),
            new TriggerItem(ItemId.AMULET_OF_GLORY_6).fixedCharges(6),
            new TriggerItem(ItemId.AMULET_OF_GLORY_ETERNAL).fixedCharges(ChargeId.UNLIMITED),
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
