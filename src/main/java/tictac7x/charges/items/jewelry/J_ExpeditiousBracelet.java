package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_ExpeditiousBracelet extends ChargedItem {
    public J_ExpeditiousBracelet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.expeditious_bracelet, ItemId.EXPEDITIOUS_BRACELET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.EXPEDITIOUS_BRACELET).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your expeditious bracelet has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Charge used.
            new OnChatMessage("Your expeditious bracelet helps you progress your slayer( task)? faster. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Bracelet fully used.
            new OnChatMessage("Your expeditious bracelet helps you progress your slayer faster. It then crumbles to dust.").setFixedCharges(30).notification("Your expeditious bracelet crumbles to dust."),

            // Break.
            new OnChatMessage("The bracelet shatters. Your next expeditious bracelet will start afresh from (?<charges>.+) charges.").setDynamicallyCharges(),
        };
    }
}
