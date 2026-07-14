package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_RingOfForging extends ChargedItem {
    public J_RingOfForging(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_forging, ItemID.RING_OF_FORGING, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_FORGING).needsToBeEquipped()
        };

        this.triggers.addAll(List.of(
            // Break full.
            new OnChatMessage("The ring is fully charged. There would be no point in breaking it.").onMenuOption("Break").onMenuTarget("Ring of forging").setFixedCharges(140),

            // Check.
            new OnChatMessage("You can smelt (?<charges>.+) more pieces of iron ore before a ring melts.").setDynamicallyCharges(),

            // Smelt.
            new OnChatMessage("You retrieve a bar of iron.").decreaseCharges(1),

            // Break.
            new OnChatMessage("The ring shatters. Your next ring of forging will start afresh from (?<charges>.+) charges.").setDynamicallyCharges()
        ));
    }
}
