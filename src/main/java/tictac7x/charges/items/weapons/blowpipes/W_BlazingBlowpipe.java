package tictac7x.charges.items.weapons.blowpipes;

import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class W_BlazingBlowpipe extends W_ToxicBlowpipe {
    public W_BlazingBlowpipe(Provider provider) {
        super(provider, TicTac7xChargesImprovedConfig.blazing_blowpipe, ItemId.BLAZING_BLOWPIPE_UNCHARGED, new TriggerItem[]{
            new TriggerItem(ItemId.BLAZING_BLOWPIPE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.BLAZING_BLOWPIPE),
        });
    }
}
