package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class U_GemSack extends U_AbstractGemContainer {
    public U_GemSack(final Provider provider) {
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