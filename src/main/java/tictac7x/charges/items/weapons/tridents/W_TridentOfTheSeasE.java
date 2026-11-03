package tictac7x.charges.items.weapons.tridents;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_TridentOfTheSeasE extends _Trident {
    public W_TridentOfTheSeasE(Provider provider) {
        super(
            TicTac7xChargesImprovedConfig.trident_of_the_seas_e,
            ItemID.TOTS_I_CHARGED,
            ItemID.TOTS_I_UNCHARGED,
            Optional.empty(),
            "Trident of the Seas (e)",
            1251,
            provider
        );

        // Charges tracked by the game.
        this.triggers.add(new OnVarbitChanged(VarbitID.CHARGES_TRIDENT_OF_THE_SEAS_E_QUANTITY).setDynamically());
    }
}
