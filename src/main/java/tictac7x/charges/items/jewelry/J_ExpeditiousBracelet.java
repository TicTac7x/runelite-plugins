package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_ExpeditiousBracelet extends ChargedItem {
    public J_ExpeditiousBracelet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.expeditious_bracelet, ItemId.EXPEDITIOUS_BRACELET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.EXPEDITIOUS_BRACELET).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your expeditious bracelet has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Charge used.
            new OnChatMessage("Your expeditious bracelet helps you progress your slayer( task)? faster. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Bracelet fully used.
            new OnChatMessage("Your expeditious bracelet helps you progress your slayer task faster. It then crumbles to dust.").setFixedCharges(30),

            // Break.
            new OnChatMessage("The bracelet shatters. Your next expeditious bracelet will start afresh from (?<charges>.+) charges.").setDynamicallyCharges()
        ));
    }
}
