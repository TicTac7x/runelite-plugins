package tictac7x.charges.items.utils;

import tictac7x.charges.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

public class U_SilklinedHerbSack extends U_HerbSack {
    public U_SilklinedHerbSack(Provider provider) {
        super(TicTac7xChargesImprovedConfig.silklined_herb_sack, ItemId.SILKLINED_HERB_SACK, ItemId.SILKLINED_HERB_SACK_OPEN, 100, provider);
    }
}