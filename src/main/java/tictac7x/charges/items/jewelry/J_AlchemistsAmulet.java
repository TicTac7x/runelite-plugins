package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_AlchemistsAmulet extends ChargedItem {
    public J_AlchemistsAmulet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.alchemists_amulet, ItemId.ALCHEMISTS_AMULET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ALCHEMISTS_AMULET).needsToBeEquipped(),
            new TriggerItem(ItemId.ALCHEMISTS_AMULET_UNCHARGED).fixedCharges(0),
        };

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("Your Alchemist's amulet has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Charge
            new OnChatMessage("You apply an additional .+ charges to your Alchemist's amulet. It now has (?<charges>.+) charges in total.").setDynamicallyCharges(),
            new OnChatMessage("You apply (?<charges>.+) charges to your Alchemist's amulet.").setDynamicallyCharges(),

            // Uncharge
            new OnChatMessage("You uncharge your Alchemist's amulet, regaining .+ amulets of chemistry in the process.").setFixedCharges(0),

            // Use charge
            new OnChatMessage("Your Alchemist's amulet helps you create a .-dose potion. It no longer has any charges.").setFixedCharges(0),
            new OnChatMessage("Your Alchemist's amulet helps you create a .-dose potion. It has one charge left.").setFixedCharges(1),
            new OnChatMessage("Your Alchemist's amulet helps you create a .-dose potion. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Auto-charge
            new OnAutoChargeMessage("Alchemist's amulet", "Amulet of chemistry", 10, this)
        ));
    }
}
