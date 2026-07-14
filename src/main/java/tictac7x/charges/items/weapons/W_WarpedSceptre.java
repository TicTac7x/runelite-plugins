package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_WarpedSceptre extends ChargedItem {
    public W_WarpedSceptre(Provider provider) {
        super(TicTac7xChargesImprovedConfig.warped_sceptre, ItemID.WARPED_SCEPTRE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.WARPED_SCEPTRE_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.WARPED_SCEPTRE)
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
