package tictac7x.charges.items.shields;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.*;

public class S_TomeOfFire extends ChargedItem {
    public S_TomeOfFire(Provider provider) {
        super(TicTac7xChargesImprovedConfig.tome_of_fire, ItemID.TOME_OF_FIRE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.TOME_OF_FIRE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.TOME_OF_FIRE).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your tome has been charged with (Burnt|Searing) Pages. It currently holds (?<charges>.+) charges?.").setDynamicallyCharges().onItemClick(),

            // Attack with regular spellbook fire spells.
            new OnGraphicChanged(99, 126, 129, 155, 1464).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Tome of Fire", "Burnt page", 20, this)
        ));
    }
}
