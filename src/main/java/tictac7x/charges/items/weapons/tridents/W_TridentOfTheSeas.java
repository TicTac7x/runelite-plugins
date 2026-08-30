package tictac7x.charges.items.weapons.tridents;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_TridentOfTheSeas extends _Trident {
    public W_TridentOfTheSeas(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_seas,
            ItemID.TOTS_CHARGED,
            ItemID.TOTS_UNCHARGED,
            Optional.of(ItemID.TOTS),
            "Trident of the Seas",
            1251,
            provider
        );
    }
}