package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_PhoenixNecklace extends ChargedItem {
    public J_PhoenixNecklace(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.phoenix_necklace, ItemId.PHOENIX_NECKLACE, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.PHOENIX_NECKLACE).fixedCharges(1).needsToBeEquipped(),
        };
    }
}
