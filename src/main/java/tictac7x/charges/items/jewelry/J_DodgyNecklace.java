package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnWidgetLoaded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_DodgyNecklace extends ChargedItem {
    public J_DodgyNecklace(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.dodgy_necklace, ItemId.DODGY_NECKLACE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.DODGY_NECKLACE).needsToBeEquipped(),
        };
        
        this.triggers = new TriggerBase[] {
            // Check.
            new OnChatMessage("Your dodgy necklace has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Protects.
            new OnChatMessage("Your dodgy necklace protects you. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Breaks.
            new OnChatMessage("Your dodgy necklace protects you. It then crumbles to dust.").setFixedCharges(10),

            // Break.
            new OnChatMessage("The necklace shatters. Your next dodgy necklace will start afresh from (?<charges>.+) charges.").setDynamicallyCharges(),

            new OnWidgetLoaded(219, 1, 0).text("Status: (?<charges>.+) charges? left.").setDynamically().onItemClick(),
        };
    }
}
