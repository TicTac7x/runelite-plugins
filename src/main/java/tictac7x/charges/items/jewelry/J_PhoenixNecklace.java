package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class J_PhoenixNecklace extends ChargedItem {
    public J_PhoenixNecklace(Provider provider) {
        super(TicTac7xChargesImprovedConfig.phoenix_necklace, ItemId.PHOENIX_NECKLACE, provider);
        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.PHOENIX_NECKLACE).fixedCharges(1).needsToBeEquipped(),
        };
    }
}
