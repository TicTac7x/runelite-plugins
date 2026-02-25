package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class W_WarpedSceptre extends ChargedItem {
    public W_WarpedSceptre(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.warped_sceptre, ItemId.WARPED_SCEPTRE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.WARPED_SCEPTRE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.WARPED_SCEPTRE)
        };

        this.triggers.addAll(List.of(
            // Charge additional.
            new OnChatMessage("You add an additional .+ charges? to your warped sceptre. It now has (?<charges>.+) charges in total.").setDynamicallyCharges(),

            // Charge empty.
            new OnChatMessage("You add (?<charges>.+) charges? to your warped sceptre.").setDynamicallyCharges(),

            // Check.
            new OnChatMessage("Your warped sceptre( only)? has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(2567).decreaseCharges(1),

            // Uncharge.
            new OnChatMessage("You uncharge your warped sceptre").setFixedCharges(0),

            // Ran out of charges.
            new OnChatMessage("Your warped sceptre has run out of charges!").setFixedCharges(0),

            // Auto-charge.
            new OnAutoChargeMessage("Warped sceptre", "Chaos rune", 0.5, this)
        ));
    }
}
