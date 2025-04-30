package tictac7x.charges.items.utils;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class U_JarGenerator extends ChargedItem {
    public U_JarGenerator(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.jar_generator, ItemId.JAR_GENERATOR, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.JAR_GENERATOR)
        };

        this.triggers = new TriggerBase[] {
            // Check or use.
            new OnChatMessage("You have (?<charges>.+) charges left in your jar generator.").setDynamicallyCharges(),

            // Crumbles.
            new OnChatMessage("Your jar generator runs out of charges and disappears.").setFixedCharges(100),
        };
    }
}
