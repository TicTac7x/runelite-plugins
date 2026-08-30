package tictac7x.charges.items.weapons.tridents;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_TridentOfTheSwamp extends _Trident {
    public W_TridentOfTheSwamp(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_swamp,
            ItemID.TOXIC_TOTS_CHARGED,
            ItemID.TOXIC_TOTS_UNCHARGED,
            Optional.empty(),
            "Trident of the Swamp",
            665,
            provider
        );
    }
}
