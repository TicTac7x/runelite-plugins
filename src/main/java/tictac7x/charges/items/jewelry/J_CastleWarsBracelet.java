package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_CastleWarsBracelet extends ChargedItem {
    public J_CastleWarsBracelet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.castle_wars_bracelet, ItemId.CASTLE_WARS_BRACELET_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CASTLE_WARS_BRACELET_1).fixedCharges(1).needsToBeEquipped(),
            new TriggerItem(ItemId.CASTLE_WARS_BRACELET_2).fixedCharges(2).needsToBeEquipped(),
            new TriggerItem(ItemId.CASTLE_WARS_BRACELET_3).fixedCharges(3).needsToBeEquipped(),
        };
    }
}
