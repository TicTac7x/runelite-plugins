package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class J_CastleWarsBracelet extends ChargedItem {
    public J_CastleWarsBracelet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.castle_wars_bracelet, ItemId.CASTLE_WARS_BRACELET_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CASTLE_WARS_BRACELET_1).fixedCharges(1).needsToBeEquipped(),
            new TriggerItem(ItemId.CASTLE_WARS_BRACELET_2).fixedCharges(2).needsToBeEquipped(),
            new TriggerItem(ItemId.CASTLE_WARS_BRACELET_3).fixedCharges(3).needsToBeEquipped(),
        };
    }
}
