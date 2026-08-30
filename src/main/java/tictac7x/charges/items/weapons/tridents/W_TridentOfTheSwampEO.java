package tictac7x.charges.items.weapons.tridents;

import net.runelite.api.gameval.ItemID;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.store.Provider;

import java.util.Optional;

public class W_TridentOfTheSwampEO extends _Trident {
    public W_TridentOfTheSwampEO(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_swamp_e_o,
            ItemID.TOXIC_TOTS_I_CHARGED_ORN,
            ItemID.TOXIC_TOTS_I_UNCHARGED_ORN,
            Optional.empty(),
            "Trident of the Swamp (e) (o)",
            3722,
            provider
        );
    }
}
