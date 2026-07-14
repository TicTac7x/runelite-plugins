package tictac7x.charges.items.weapons.tridents;

import tictac7x.charges.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_TridentOfTheSwamp extends _Trident {
    public W_TridentOfTheSwamp(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_swamp,
            ItemId.TRIDENT_OF_THE_SWAMP,
            ItemId.TRIDENT_OF_THE_SWAMP_UNCHARGED,
            Optional.empty(),
            "Trident of the swamp",
            665,
            provider
        );
    }
}
