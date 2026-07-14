package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_TridentOfTheSwamp extends ChargedItem {
    public W_TridentOfTheSwamp(Provider provider) {
        super(TicTac7xChargesImprovedConfig.trident_of_the_swamp, ItemId.TRIDENT_OF_THE_SWAMP, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TRIDENT_OF_THE_SWAMP_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TRIDENT_OF_THE_SWAMP),
        };

        this.triggers.addAll(List.of(
            // Ran out of charges.
            new OnChatMessage("Your Trident of the swamp has run out of charges.").setFixedCharges(0),

            // Check, one charge left.
            new OnChatMessage("Your Trident of the swamp has one charge.").setFixedCharges(1),

            // Check for charges and warning when low.
            new OnChatMessage("Your Trident of the swamp( only)? has (?<charges>.+) charges( left)?.").setDynamicallyCharges(),

            // Charge to full.
            new OnChatMessage("You add .* charges? to the Trident of the swamp. New total: (?<charges>.+)").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(665).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Trident of the swamp", "Death rune", 1, this)
        ));
    }
}
