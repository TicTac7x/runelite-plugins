package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_AbyssalTentacle extends ChargedItem {
    public W_AbyssalTentacle(Provider provider) {
        super(TicTac7xChargesImprovedConfig.abyssal_tentacle, ItemID.ABYSSAL_TENTACLE, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.ABYSSAL_TENTACLE)
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your abyssal tentacle can perform (?<charges>.+) more attacks?.").setDynamicallyCharges(),

            // Attack.
            new OnAnimationChanged(1658).isEquipped().decreaseCharges(1),

            // Degrade
            new OnChatMessage("Your abyssal tentacle has degraded.").setFixedCharges(0)
        ));
    }
}
