package tictac7x.charges.items.shields;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.*;

public class S_TomeOfEarth extends ChargedItem {
    public S_TomeOfEarth(Provider provider) {
        super(TicTac7xChargesImprovedConfig.tome_of_earth, ItemID.TOME_OF_EARTH, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.TOME_OF_EARTH_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.TOME_OF_EARTH).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your tome currently holds (?<charges>.+) charges?.").setDynamicallyCharges().onItemClick(),

            // Attack with regular spellbook earth spells.
            new OnGraphicChanged(96, 123, 138, 164, 1461).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Tome of Earth", "Soiled page", 20, this)
        ));
    }
}
