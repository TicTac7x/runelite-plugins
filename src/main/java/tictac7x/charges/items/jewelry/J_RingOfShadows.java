package tictac7x.charges.items.jewelry;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import net.runelite.api.gameval.*;

import java.util.*;

public class J_RingOfShadows extends ChargedItem {
    public J_RingOfShadows(Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_shadows, ItemID.RING_OF_SHADOWS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.RING_OF_SHADOWS_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.RING_OF_SHADOWS)
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your ring of shadows has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add (?<charges>.+) charges? to the ring of shadows.$").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add .+ charges? to the ring of shadows. It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Teleport.
            new OnAnimationChanged(AnimationID.MAHJARRAT_HUMAN_DISAPPEAR_01_NOHELD_QUICK).decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Ring of shadows", "Blood rune", 1, this)
        ));
    }
}
