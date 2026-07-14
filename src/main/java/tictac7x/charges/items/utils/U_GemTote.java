package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

public class U_GemTote extends U_AbstractGemContainer {
    public U_GemTote(Provider provider) {
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

