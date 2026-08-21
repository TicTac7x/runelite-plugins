package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_PendantOfAtes extends ChargedItem {
    public J_PendantOfAtes(Provider provider) {
        super(TicTac7xChargesImprovedConfig.pendant_of_ates, ItemID.PENDANT_OF_ATES, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.PENDANT_OF_ATES_EMPTY).fixedCharges(0),
            new TriggerItem(ItemID.PENDANT_OF_ATES),
        };

        this.triggers.addAll(List.of(
            // Check empty.
            new OnChatMessage("The pendant has no charges.").setFixedCharges(0).onItemClick(),

            // Check.
            new OnChatMessage("The pendant has (?<charges>.+) charges?.").setDynamicallyCharges().onItemClick(),

            // Charge.
            new OnChatMessage("You add .+ frozen tears? to your pendant. It now has (?<charges>.+) charges.").setDynamicallyCharges(),

            // Uncharge.
            new OnChatMessage("You uncharge your pendant by removing (?<charges>.+) frozen tears? from it.").decreaseDynamicallyCharges(),

            // Teleport.
            new OnGraphicChanged(2754).decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Pendant of Ates", "Frozen tear", 1, this),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport")
        ));
    }
}
