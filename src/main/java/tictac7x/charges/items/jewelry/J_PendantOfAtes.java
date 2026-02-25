package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class J_PendantOfAtes extends ChargedItem {
    public J_PendantOfAtes(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.pendant_of_ates, ItemId.PENDANT_OF_ATES, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.PENDANT_OF_ATES_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.PENDANT_OF_ATES),
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
            new OnAutoChargeMessage("Pendant of ates", "Frozen tear", 1, this),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport")
        ));
    }
}
