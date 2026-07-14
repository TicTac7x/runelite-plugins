package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

public class U_GemSack extends U_AbstractGemContainer {
    public U_GemSack(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.gem_sack,
            ItemId.GEM_SACK,
            ItemId.GEM_SACK_OPEN,
            60,
            "gem sack",
            true,
            true,
            provider
        );
    }
}