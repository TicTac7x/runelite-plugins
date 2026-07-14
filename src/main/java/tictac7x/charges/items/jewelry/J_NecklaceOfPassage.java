package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class J_NecklaceOfPassage extends ChargedItem {
    public J_NecklaceOfPassage(Provider provider) {
        super(TicTac7xChargesImprovedConfig.necklace_of_passage, ItemID.NECKLACE_OF_PASSAGE_1, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.NECKLACE_OF_PASSAGE_1).fixedCharges(1),
            new TriggerItem(ItemID.NECKLACE_OF_PASSAGE_2).fixedCharges(2),
            new TriggerItem(ItemID.NECKLACE_OF_PASSAGE_3).fixedCharges(3),
            new TriggerItem(ItemID.NECKLACE_OF_PASSAGE_4).fixedCharges(4),
            new TriggerItem(ItemID.NECKLACE_OF_PASSAGE_5).fixedCharges(5),
        };
    }
}
