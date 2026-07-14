package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.store.Provider;

public class U_GemTote extends U_AbstractGemContainer {
    public U_GemTote(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.gem_tote,
            ItemID.GEM_TOTE,
            ItemID.GEM_TOTE_OPEN,
            20,
            "gem tote",
            false,
            true,
            provider
        );
    }
}

