package tictac7x.charges.items.weapons;

import java.util.List;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;

public class W_AbyssalTentacle extends ChargedItem {
    public W_AbyssalTentacle(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.abyssal_tentacle, ItemId.ABYSSAL_TENTACLE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.ABYSSAL_TENTACLE)
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your abyssal tentacle can perform (?<charges>.+) more attacks?").setDynamicallyCharges(),

            // Attack.
            new OnAnimationChanged(1658).itemEquipped().decreaseCharges(1),

            // Degrade
            new OnChatMessage("Your abyssal tentacle has degraded.").setFixedCharges(0)
        ));
    }
}
