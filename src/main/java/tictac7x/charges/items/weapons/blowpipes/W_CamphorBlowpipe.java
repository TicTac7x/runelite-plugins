package tictac7x.charges.items.weapons.blowpipes;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class W_CamphorBlowpipe extends _Blowpipe {
    public W_CamphorBlowpipe(final Provider provider) {
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
