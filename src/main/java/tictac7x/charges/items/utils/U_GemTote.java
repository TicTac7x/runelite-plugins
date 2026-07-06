package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class U_GemTote extends U_AbstractGemContainer {
    public U_GemTote(final Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.gem_tote,
            ItemId.GEM_TOTE,
            ItemId.GEM_TOTE_OPEN,
            20,
            "gem tote",
            false,
            true,
            provider
        );
    }
}

