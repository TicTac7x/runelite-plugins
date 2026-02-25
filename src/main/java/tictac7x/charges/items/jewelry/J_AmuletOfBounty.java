package tictac7x.charges.items.jewelry;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

import java.util.List;

public class J_AmuletOfBounty extends ChargedItem {
    public J_AmuletOfBounty(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.amulet_of_bounty, ItemId.AMULET_OF_BOUNTY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.AMULET_OF_BOUNTY)
        };

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("Your amulet of bounty has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use
            new OnChatMessage("Your amulet of bounty saves some seeds for you. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Crumbles.
            new OnChatMessage("Your amulet of bounty saves some seeds for you. It then crumbles to dust.").setFixedCharges(10),

            // Destroy
            new OnChatMessage("The amulet shatters. Your next amulet of bounty will start afresh from 10 charges.").setFixedCharges(10)
        ));
    }
}
