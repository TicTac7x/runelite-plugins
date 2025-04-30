package tictac7x.charges.items.jewelry;

import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

public class J_FlamtaerBracelet extends ChargedItem {
    public J_FlamtaerBracelet(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.flamtaer_bracelet, ItemId.FLAMTAER_BRACELET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FLAMTAER_BRACELET).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[]{
            new OnChatMessage("Your Flamtaer bracelet helps you build the temple quicker. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),
            new OnChatMessage("Your flamtaer bracelet has (?<charges>.+) charges? left.").setDynamicallyCharges(),
            new OnChatMessage("Your Flamtaer bracelet helps you build the temple quicker. It then crumbles to dust.").setFixedCharges(80).notification("Your flamtaer bracelet crumbles to dust."),
            new OnChatMessage("The bracelet shatters. Your next Flamtaer bracelet will star afresh from 80 charges.").setFixedCharges(80)
        };
    }
}
