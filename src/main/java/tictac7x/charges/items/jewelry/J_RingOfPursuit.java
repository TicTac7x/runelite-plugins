package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_RingOfPursuit extends ChargedItem {
    public J_RingOfPursuit(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_pursuit, ItemId.RING_OF_PURSUIT, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.RING_OF_PURSUIT).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[]{
            // Check.
            new OnChatMessage("Your ring of pursuit has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use.
            new OnChatMessage("Your ring of pursuit reveals the entire trail to you. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use last charge.
            new OnChatMessage("Your ring of pursuit reveals the entire trail to you. It then crumbles to dust.").setFixedCharges(10),

            // Destroy.
            new OnChatMessage("The ring shatters. Your next ring of pursuit will start afresh from 10 charges.").setFixedCharges(10),
        };
    }
}
