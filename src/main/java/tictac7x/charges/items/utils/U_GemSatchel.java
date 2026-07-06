package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class U_GemSatchel extends U_AbstractGemContainer {
    public U_GemSatchel(final Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.gem_satchel,
            ItemId.GEM_SATCHEL,
            ItemId.GEM_SATCHEL_OPEN,
            10,
            "gem satchel",
            false,
            true,
            provider
        );
    }
}
