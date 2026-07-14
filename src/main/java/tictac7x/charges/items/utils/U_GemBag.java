package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import net.runelite.api.gameval.*;
import tictac7x.charges.store.Provider;

public class U_GemBag extends U_AbstractGemContainer {
    public U_GemBag(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.gem_bag,
            ItemID.GEM_BAG,
            ItemID.GEM_BAG_OPEN,
            60,
            "gem bag",
            true,
            false,
            provider
        );
    }
}
