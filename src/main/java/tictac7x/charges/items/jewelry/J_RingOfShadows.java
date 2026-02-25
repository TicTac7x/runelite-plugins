package tictac7x.charges.items.jewelry;

import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.store.ids.AnimationId;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class J_RingOfShadows extends ChargedItem {
    public J_RingOfShadows(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ring_of_shadows, ItemId.RING_OF_SHADOWS, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.RING_OF_SHADOWS_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.RING_OF_SHADOWS)
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your ring of shadows has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add (?<charges>.+) charges? to the ring of shadows.$").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You add .+ charges? to the ring of shadows. It now has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Teleport.
            new OnAnimationChanged(AnimationId.RING_OF_SHADOWS_TELEPORT).decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Ring of shadows", "Blood rune", 1, this)
        ));
    }
}
