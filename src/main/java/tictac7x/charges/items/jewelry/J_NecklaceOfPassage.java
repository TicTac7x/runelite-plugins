package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_NecklaceOfPassage extends ChargedItem {
    public J_NecklaceOfPassage(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.necklace_of_passage, ItemId.NECKLACE_OF_PASSAGE_1, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.NECKLACE_OF_PASSAGE_1).fixedCharges(1),
            new TriggerItem(ItemId.NECKLACE_OF_PASSAGE_2).fixedCharges(2),
            new TriggerItem(ItemId.NECKLACE_OF_PASSAGE_3).fixedCharges(3),
            new TriggerItem(ItemId.NECKLACE_OF_PASSAGE_4).fixedCharges(4),
            new TriggerItem(ItemId.NECKLACE_OF_PASSAGE_5).fixedCharges(5),
        };
    }
}
