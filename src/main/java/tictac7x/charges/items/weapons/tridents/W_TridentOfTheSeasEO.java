package tictac7x.charges.items.weapons.tridents;

import net.runelite.api.gameval.ItemID;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.store.Provider;

import java.util.Optional;

public class W_TridentOfTheSeasEO extends _Trident {
    public W_TridentOfTheSeasEO(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_seas_e_o,
            ItemID.TOTS_I_CHARGED_ORN,
            ItemID.TOTS_I_UNCHARGED_ORN,
            Optional.empty(),
            "Trident of the Seas (e) (o)",
            3721,
            provider
        );
    }
}
