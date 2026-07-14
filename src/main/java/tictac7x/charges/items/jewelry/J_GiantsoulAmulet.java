package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class J_GiantsoulAmulet extends ChargedItem {
    public J_GiantsoulAmulet(Provider provider) {
        super(TicTac7xChargesImprovedConfig.giantsoul_amulet, ItemID.GIANTSOUL_AMULET_CHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.GIANTSOUL_AMULET_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.GIANTSOUL_AMULET_CHARGED),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your Giantsoul amulet has (?<charges>.+) charges? left powering it.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add .+ charges? to your Giantsoul amulet, giving it a total of (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Teleport.
            new OnGraphicChanged(3226).decreaseCharges(1),

            // Unified menu entry.
            new OnMenuEntryAdded("Rub").replaceOption("Teleport"),

            // Auto-charge.
            new OnAutoChargeMessage("Giantsoul amulet", "Big bones", 1, this)
        ));
    }
}
