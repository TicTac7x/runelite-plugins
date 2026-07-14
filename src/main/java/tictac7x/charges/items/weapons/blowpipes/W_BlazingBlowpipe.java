package tictac7x.charges.items.weapons.blowpipes;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

public class W_BlazingBlowpipe extends W_ToxicBlowpipe {
    public W_BlazingBlowpipe(Provider provider) {
        super(provider, TicTac7xChargesImprovedConfig.blazing_blowpipe, ItemID.TOXIC_BLOWPIPE_ORNAMENT, new TriggerItem[]{
            new TriggerItem(ItemID.TOXIC_BLOWPIPE_ORNAMENT).fixedCharges(0),
            new TriggerItem(ItemID.TOXIC_BLOWPIPE_LOADED_ORNAMENT),
        });
    }
}
