package tictac7x.charges.items.jewelry;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

import java.util.List;

public class J_CowbellAmulet extends ChargedItem {
    public J_CowbellAmulet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.cowbell_amulet, ItemId.COWBELL_AMULET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.COWBELL_AMULET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.COWBELL_AMULET)
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
