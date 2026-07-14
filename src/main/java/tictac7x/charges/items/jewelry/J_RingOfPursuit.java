package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_RingOfPursuit extends ChargedItem {
    public J_RingOfPursuit(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_pursuit, ItemID.RING_OF_PURSUIT, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_PURSUIT).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your ring of pursuit has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use.
            new OnChatMessage("Your ring of pursuit reveals the entire trail to you. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use last charge.
            new OnChatMessage("Your ring of pursuit reveals the entire trail to you. It then crumbles to dust.").setFixedCharges(10),

            // Destroy.
            new OnChatMessage("The ring shatters. Your next ring of pursuit will start afresh from 10 charges.").setFixedCharges(10)
        ));
    }
}
