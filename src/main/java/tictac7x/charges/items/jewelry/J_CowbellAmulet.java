package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_CowbellAmulet extends ChargedItem {
    public J_CowbellAmulet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.cowbell_amulet, ItemID.COWBELL_AMULET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.COWBELL_AMULET_EMPTY).fixedCharges(0),
            new TriggerItem(ItemID.COWBELL_AMULET)
        };

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("The amulet has (?<charges>.+) charges?.").onItemClick().setDynamicallyCharges(),

            // Auto message
            new OnChatMessage("Your amulet has (?<charges>.+) charges? left.").onItemClick().setDynamicallyCharges(),

            // Charge
            new OnChatMessage("You add .* air runes? to your amulet. It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Teleport
            new OnAnimationChanged(13811).onMenuOption("Teleport").decreaseCharges(1),

            // Auto-charge
            new OnAutoChargeMessage("Cowbell amulet", "Air rune", 1, this)
        ));
    }
}
