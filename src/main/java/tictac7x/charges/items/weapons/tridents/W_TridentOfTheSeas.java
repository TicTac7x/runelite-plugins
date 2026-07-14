package tictac7x.charges.items.weapons.tridents;

import tictac7x.charges.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_TridentOfTheSeas extends _Trident {
    public W_TridentOfTheSeas(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_seas,
            ItemId.TRIDENT_OF_THE_SEAS,
            ItemId.TRIDENT_OF_THE_SEAS_UNCHARGED,
            Optional.of(ItemId.TRIDENT_OF_THE_SEAS_FULL),
            "Trident of the seas",
            1251,
            provider
        );
    }
}