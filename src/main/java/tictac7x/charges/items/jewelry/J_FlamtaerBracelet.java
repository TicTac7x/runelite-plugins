package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_FlamtaerBracelet extends ChargedItem {
    public J_FlamtaerBracelet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.flamtaer_bracelet, ItemId.FLAMTAER_BRACELET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.FLAMTAER_BRACELET).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            new OnChatMessage("Your Flamtaer bracelet helps you build the temple quicker. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),
            new OnChatMessage("Your flamtaer bracelet has (?<charges>.+) charges? left.").setDynamicallyCharges(),
            new OnChatMessage("Your Flamtaer bracelet helps you build the temple quicker. It then crumbles to dust.").setFixedCharges(80),
            new OnChatMessage("The bracelet shatters. Your next Flamtaer bracelet will star afresh from 80 charges.").setFixedCharges(80)
        ));
    }
}
