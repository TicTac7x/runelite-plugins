package tictac7x.charges.items.weapons.blowpipes;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class W_CamphorBlowpipe extends _Blowpipe {
    public W_CamphorBlowpipe(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.camphor_blowpipe,
            ItemID.CAMPHOR_BLOWPIPE,
            provider,
            new TriggerItem[]{
                new TriggerItem(ItemID.CAMPHOR_BLOWPIPE),
                new TriggerItem(ItemID.CAMPHOR_BLOWPIPE_EMPTY).fixedCharges(0)
            },
            false,
            false,
            13142
        );
    }
}
