package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.store.ids.*;
import tictac7x.charges.store.Provider;

public class U_GemSatchel extends U_AbstractGemContainer {
    public U_GemSatchel(Provider provider) {
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
