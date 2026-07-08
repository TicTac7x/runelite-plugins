package tictac7x.charges.items.utils;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class U_GemPouch extends U_AbstractGemContainer {
    public U_GemPouch(final Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.gem_pouch,
            ItemId.GEM_POUCH,
            ItemId.GEM_POUCH_OPEN,
            5,
            "gem pouch",
            false,
            true,
            provider
        );
    }
}
