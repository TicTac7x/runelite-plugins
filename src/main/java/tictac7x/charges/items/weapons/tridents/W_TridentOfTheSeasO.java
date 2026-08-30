package tictac7x.charges.items.weapons.tridents;

import net.runelite.api.gameval.ItemID;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.store.Provider;

import java.util.Optional;

public class W_TridentOfTheSeasO extends _Trident {
    public W_TridentOfTheSeasO(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_seas_o,
            ItemID.TOTS_CHARGED_ORN,
            ItemID.TOTS_UNCHARGED_ORN,
            Optional.of(ItemID.TOTS_ORN),
            "Trident of the Seas (o)",
            3721,
            provider
        );
    }
}
