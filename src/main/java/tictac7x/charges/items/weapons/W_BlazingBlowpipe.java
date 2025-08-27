package tictac7x.charges.items.weapons;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class W_BlazingBlowpipe extends W_ToxicBlowpipe {
    public W_BlazingBlowpipe(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.blazing_blowpipe, ItemId.BLAZING_BLOWPIPE_UNCHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BLAZING_BLOWPIPE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.BLAZING_BLOWPIPE),
        };
    }
}
