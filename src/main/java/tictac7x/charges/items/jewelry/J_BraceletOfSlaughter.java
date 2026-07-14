package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_BraceletOfSlaughter extends ChargedItem {
    public J_BraceletOfSlaughter(Provider provider) {
        super(TicTac7xChargesImprovedConfig.bracelet_of_slaughter, ItemId.BRACELET_OF_SLAUGHTER, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.BRACELET_OF_SLAUGHTER).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your bracelet of slaughter has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Charge used.
            new OnChatMessage("Your bracelet of slaughter prevents your slayer count from decreasing. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Bracelet fully used.
            new OnChatMessage("Your bracelet of slaughter prevents your slayer count from decreasing. It then crumbles to dust.").setFixedCharges(30),

            // Break.
            new OnChatMessage("The bracelet shatters. Your next bracelet of slaughter will start afresh from (?<charges>.+) charges.").setDynamicallyCharges()
        ));
    }
}
