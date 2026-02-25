package tictac7x.charges.items.weapons;

import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class W_TridentOfTheSeas extends ChargedItem {
    public W_TridentOfTheSeas(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.trident_of_the_seas, ItemId.TRIDENT_OF_THE_SEAS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TRIDENT_OF_THE_SEAS_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TRIDENT_OF_THE_SEAS),
            new TriggerItem(ItemId.TRIDENT_OF_THE_SEAS_FULL).fixedCharges(2500),
        };

        this.triggers.addAll(List.of(
            // Ran out of charges.
            new OnChatMessage("Your Trident of the seas has run out of charges.").setFixedCharges(0),

            // Check, one charge left.
            new OnChatMessage("Your Trident of the seas has one charge.").setFixedCharges(1),

            // Check for charges and warning when low.
            new OnChatMessage("Your Trident of the seas( only)? has (?<charges>.+) charges( left)?.").setDynamicallyCharges(),

            // Charge to full.
            new OnChatMessage("You add .* charges? to the Trident of the seas. New total: (?<charges>.+)").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(1251).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Trident of the seas", "Death rune", 1, this)
        ));
    }
}
