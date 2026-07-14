package tictac7x.charges.items.weapons.tridents;

import tictac7x.charges.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_TridentOfTheSeasE extends _Trident {
    public W_TridentOfTheSeasE(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_seas_e,
            ItemId.TRIDENT_OF_THE_SEAS_ENCHANTED,
            ItemId.TRIDENT_OF_THE_SEAS_ENCHANTED_UNCHARGED,
            Optional.empty(),
            "Trident of the seas (e)",
            1251,
            provider
        );
    }
}
