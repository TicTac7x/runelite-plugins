package tictac7x.charges.items.weapons.tridents;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_TridentOfTheSwampE extends _Trident {
    public W_TridentOfTheSwampE(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_swamp_e,
            ItemID.TOXIC_TOTS_I_CHARGED,
            ItemID.TOXIC_TOTS_I_UNCHARGED,
            Optional.empty(),
            "Trident of the Swamp (e)",
            665,
            provider
        );
    }
}
