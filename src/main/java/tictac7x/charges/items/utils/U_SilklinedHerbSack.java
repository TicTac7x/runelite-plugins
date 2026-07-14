package tictac7x.charges.items.utils;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.store.*;

public class U_SilklinedHerbSack extends U_HerbSack {
    public U_SilklinedHerbSack(Provider provider) {
        super(TicTac7xChargesImprovedConfig.silklined_herb_sack, ItemID.SLAYER_HERB_SACK_SILK, ItemID.SLAYER_HERB_SACK_SILK_OPEN, 100, provider);
    }
}