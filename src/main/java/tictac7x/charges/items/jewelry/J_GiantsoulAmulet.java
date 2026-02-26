package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

import java.util.List;

public class J_GiantsoulAmulet extends ChargedItem {
    public J_GiantsoulAmulet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.giantsoul_amulet, ItemId.GIANTSOUL_AMULET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.GIANTSOUL_AMULET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.GIANTSOUL_AMULET),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your Giantsoul amulet has (?<charges>.+) charges? left powering it.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add .+ charges? to your Giantsoul amulet, giving it a total of (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Teleport.
            new OnGraphicChanged(3226).decreaseCharges(1),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),

            // Auto-charge.
            new OnAutoChargeMessage("Giantsoul amulet", "Big bones", 1, this)
        ));
    }
}
