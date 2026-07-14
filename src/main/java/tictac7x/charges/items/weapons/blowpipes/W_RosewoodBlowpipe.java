package tictac7x.charges.items.weapons.blowpipes;

import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class W_RosewoodBlowpipe extends _Blowpipe {
    public W_RosewoodBlowpipe(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.rosewood_blowpipe,
            ItemId.ROSEWOOD_BLOWPIPE,
            provider,
            new TriggerItem[]{
                new TriggerItem(ItemId.ROSEWOOD_BLOWPIPE),
                new TriggerItem(ItemId.ROSEWOOD_BLOWPIPE_UNCHARGED).fixedCharges(0)
            },
            true,
            13144
        );
    }
}
