package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_AmuletOfChemistry extends ChargedItem {
    public J_AmuletOfChemistry(Provider provider) {
        super(TicTac7xChargesImprovedConfig.amulet_of_chemistry, ItemID.AMULET_OF_CHEMISTRY, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.AMULET_OF_CHEMISTRY).needsToBeEquipped()
        };

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("Your amulet of chemistry has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Use charge
            new OnChatMessage("Your amulet of chemistry helps you create a .-dose potion. It then crumbles to dust.").setFixedCharges(5),
            new OnChatMessage("Your amulet of chemistry helps you create a .-dose potion. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Status from break dialog
            new OnWidgetLoaded(219, 1, 0).text("Status: (?<charges>.+) charges? left.").setDynamically().onItemClick(),

            // Break
            new OnChatMessage("The amulet shatters. Your next amulet of chemistry will start afresh from (?<charges>.+) charges.").setDynamicallyCharges()
        ));
    }
}
