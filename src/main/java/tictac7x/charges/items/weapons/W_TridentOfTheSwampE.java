package tictac7x.charges.items.weapons;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_TridentOfTheSwampE extends ChargedItem {
    public W_TridentOfTheSwampE(Provider provider) {
        super(TicTac7xChargesImprovedConfig.trident_of_the_swamp_e, ItemId.TRIDENT_OF_THE_SWAMP_ENCHANTED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TRIDENT_OF_THE_SWAMP_ENCHANTED_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TRIDENT_OF_THE_SWAMP_ENCHANTED),
        };

        this.triggers.addAll(List.of(
            // Ran out of charges.
            new OnChatMessage("Your Trident of the swamp \\(e\\) has run out of charges.").setFixedCharges(0),

            // Check, one charge left.
            new OnChatMessage("Your Trident of the swamp \\(e\\) has one charge.").setFixedCharges(1),

            // Check for charges and warning when low.
            new OnChatMessage("Your Trident of the swamp \\(e\\)( only)? has (?<charges>.+) charges( left)?.").setDynamicallyCharges(),

            // Charge to full.
            new OnChatMessage("You add .* charges? to the Trident of the swamp \\(e\\). New total: (?<charges>.+)").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(665).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Trident of the swamp \\(e\\)", "Death rune", 1, this)
        ));
    }
}
