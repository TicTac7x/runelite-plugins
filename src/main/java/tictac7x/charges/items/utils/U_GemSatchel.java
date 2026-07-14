package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.store.Provider;

public class U_GemSatchel extends U_AbstractGemContainer {
    public U_GemSatchel(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.gem_satchel,
            ItemID.GEM_SATCHEL,
            ItemID.GEM_SATCHEL_OPEN,
            10,
            "gem satchel",
            false,
            true,
            provider
        );
    }
}
