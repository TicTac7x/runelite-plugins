package tictac7x.charges.items.helms;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class H_CircletOfWater extends ChargedItem {
    public H_CircletOfWater(Provider provider) {
        super(TicTac7xChargesImprovedConfig.circlet_of_water, ItemID.WATER_CIRCLET_CHARGED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.WATER_CIRCLET).fixedCharges(0),
            new TriggerItem(ItemID.WATER_CIRCLET_CHARGED).needsToBeEquipped(),
        };

        this.triggers.addAll(List.of(
            // Protect from heat.
            new OnChatMessage("Your circlet protects you from the desert heat.").decreaseCharges(1),

            // Check.
            new OnChatMessage("Your circlet has (?<charges>.+) charges? left.").setDynamicallyCharges(),

            // Charge while empty.
            new OnChatMessage("You add (?<charges>.+) charges? to your circlet.$").setDynamicallyCharges(),

            // Charge while not empty.
            new OnChatMessage("You add .+ charges? to your circlet. It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Auto-charge.
            new OnAutoChargeMessage("Circlet of water", "Water rune", 0.2, this)
        ));
    }
}
