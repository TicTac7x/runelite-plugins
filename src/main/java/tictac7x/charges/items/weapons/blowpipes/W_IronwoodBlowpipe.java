package tictac7x.charges.items.weapons.blowpipes;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class W_IronwoodBlowpipe extends _Blowpipe {
    public W_IronwoodBlowpipe(final Provider provider) {
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
