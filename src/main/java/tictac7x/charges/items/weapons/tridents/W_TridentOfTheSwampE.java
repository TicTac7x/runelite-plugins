package tictac7x.charges.items.weapons.tridents;

import tictac7x.charges.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_TridentOfTheSwampE extends _Trident {
    public W_TridentOfTheSwampE(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_swamp_e,
            ItemId.TRIDENT_OF_THE_SWAMP_ENCHANTED,
            ItemId.TRIDENT_OF_THE_SWAMP_ENCHANTED_UNCHARGED,
            Optional.empty(),
            "Trident of the swamp (e)",
            665,
            provider
        );
    }
}
