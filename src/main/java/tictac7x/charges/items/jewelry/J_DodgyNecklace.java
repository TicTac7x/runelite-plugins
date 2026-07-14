package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_DodgyNecklace extends ChargedItem {
    public J_DodgyNecklace(Provider provider) {
        super(TicTac7xChargesImprovedConfig.dodgy_necklace, ItemID.DODGY_NECKLACE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.DODGY_NECKLACE).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your dodgy necklace has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Protects.
            new OnChatMessage("Your dodgy necklace protects you. It has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Breaks.
            new OnChatMessage("Your dodgy necklace protects you. It then crumbles to dust.").setFixedCharges(10),

            // Break.
            new OnChatMessage("The necklace shatters. Your next dodgy necklace will start afresh from (?<charges>.+) charges.").setDynamicallyCharges(),

            new OnWidgetLoaded(219, 1, 0).text("Status: (?<charges>.+) charges? left.").setDynamically().onItemClick()
        ));
    }
}
