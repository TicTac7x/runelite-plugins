package tictac7x.charges.items.weapons.blowpipes;

import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class W_CamphorBlowpipe extends _Blowpipe {
    public W_CamphorBlowpipe(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.camphor_blowpipe,
            ItemId.CAMPHOR_BLOWPIPE,
            provider,
            new TriggerItem[]{
                new TriggerItem(ItemId.CAMPHOR_BLOWPIPE),
                new TriggerItem(ItemId.CAMPHOR_BLOWPIPE_UNCHARGED).fixedCharges(0)
            },
            false,
            13142
        );
    }
}
