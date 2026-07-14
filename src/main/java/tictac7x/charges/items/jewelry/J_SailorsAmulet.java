package tictac7x.charges.items.jewelry;

import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class J_SailorsAmulet extends ChargedItem {
    public J_SailorsAmulet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.sailors_amulet, ItemId.SAILORS_AMULET, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SAILORS_AMULET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.SAILORS_AMULET)
        };

        this.triggers.addAll(List.of(
            // Check
            new OnChatMessage("(The|Your) amulet has (?<charges>.+) charges( left)?.")
                .onItemClick()
                .setDynamicallyCharges(),

            // Charge
            new OnChatMessage("You add .+ charges? to your amulet. It now has (?<charges>.+) charges?.")
                .setDynamicallyCharges(),

            // Teleport
            new OnMenuOptionClicked("The Pandemonium", "Port Roberts", "Deepfin Point")
                .onMenuOptionEventId(65540, 131076, 327684)
                .decreaseCharges(1)
                .multiTrigger(),

            // Teleport while equipped
            new OnMenuOptionClicked("The Pandemonium", "Port Roberts", "Deepfin Point")
                .onItemClick()
                .decreaseCharges(1)
                .multiTrigger(),

            // Teleport location not unlocked
            new OnChatMessage("You must find a sailors' marker at that location before teleporting there.")
                .increaseCharges(1),

            // Auto-charge
            new OnAutoChargeMessage("Sailors' amulet", "Law rune", 10, this)
        ));
    }
}
