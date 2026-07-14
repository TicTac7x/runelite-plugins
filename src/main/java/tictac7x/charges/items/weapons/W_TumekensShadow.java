package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;

import java.util.*;

public class W_TumekensShadow extends ChargedItem {
    public W_TumekensShadow(Provider provider) {
        super(TicTac7xChargesImprovedConfig.tumekens_shadow, ItemID.TUMEKENS_SHADOW, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.TUMEKENS_SHADOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemID.TUMEKENS_SHADOW),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Tumeken's shadow( only)? has (?<charges>.+) charges? (remaining|left)").setDynamicallyCharges(),

            // Uncharge.
            new OnChatMessage("You uncharge your Tumeken's shadow").setFixedCharges(0),

            // Charge additionally.
            new OnChatMessage("You apply an additional .* charges to your Tumeken's shadow. It now has (?<charges>.+) charges in total.").setDynamicallyCharges(),

            // Charge.
            new OnChatMessage("You apply (?<charges>.+) charges to your Tumeken's shadow.").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(2125).decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Tumeken's shadow", "Soul rune", 0.5, this)
        ));
    }
}
