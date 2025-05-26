package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnWidgetLoaded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_AmuletOfChemistry extends ChargedItem {
    public J_AmuletOfChemistry(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.amulet_of_chemistry, ItemId.AMULET_OF_CHEMISTRY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.AMULET_OF_CHEMISTRY).needsToBeEquipped()
        };

        this.triggers = new TriggerBase[] {
            // Check
            new OnChatMessage("Your amulet of chemistry has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use charge
            new OnChatMessage("Your amulet of chemistry helps you create a .-dose potion. It then crumbles to dust.").setFixedCharges(5),
            new OnChatMessage("Your amulet of chemistry helps you create a .-dose potion. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Status from break dialog
            new OnWidgetLoaded(219, 1, 0).text("Status: (?<charges>.+) charges? left.").setDynamically().onItemClick(),

            // Break
            new OnChatMessage("The amulet shatters. Your next amulet of chemistry will start afresh from (?<charges>.+) charges.").setDynamicallyCharges(),
        };
    }
}
