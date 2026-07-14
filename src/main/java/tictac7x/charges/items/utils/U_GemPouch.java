package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.store.Provider;

public class U_GemPouch extends U_AbstractGemContainer {
    public U_GemPouch(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.gem_pouch,
            ItemID.GEM_POUCH,
            ItemID.GEM_POUCH_OPEN,
            5,
            "gem pouch",
            false,
            true,
            provider
        );
    }
}
