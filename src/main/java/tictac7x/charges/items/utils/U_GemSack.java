package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.store.Provider;

public class U_GemSack extends U_AbstractGemContainer {
    public U_GemSack(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.gem_sack,
            ItemID.GEM_SACK,
            ItemID.GEM_SACK_OPEN,
            60,
            "gem sack",
            true,
            true,
            provider
        );
    }
}