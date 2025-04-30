package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_RingOfForging extends ChargedItem {
    public J_RingOfForging(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_forging, ItemId.RING_OF_FORGING, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.RING_OF_FORGING).needsToBeEquipped()
        };

        this.triggers = new TriggerBase[] {
            // Break full.
            new OnChatMessage("The ring is fully charged. There would be no point in breaking it.").onMenuOption("Break").onMenuTarget("Ring of forging").setFixedCharges(140),

            // Check.
            new OnChatMessage("You can smelt (?<charges>.+) more pieces of iron ore before a ring melts.").setDynamicallyCharges(),

            // Smelt.
            new OnChatMessage("You retrieve a bar of iron.").decreaseCharges(1),

            // Break.
            new OnChatMessage("The ring shatters. Your next ring of forging will start afresh from (?<charges>.+) charges.").setDynamicallyCharges(),
        };
    }
}
