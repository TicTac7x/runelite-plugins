package tictac7x.charges.items.weapons.blowpipes;

import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class W_IronwoodBlowpipe extends _Blowpipe {
    public W_IronwoodBlowpipe(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.ironwood_blowpipe,
            ItemId.IRONWOOD_BLOWPIPE,
            provider,
            new TriggerItem[]{
                new TriggerItem(ItemId.IRONWOOD_BLOWPIPE),
                new TriggerItem(ItemId.IRONWOOD_BLOWPIPE_UNCHARGED).fixedCharges(0)
            },
            true,
            13143
        );
    }
}
