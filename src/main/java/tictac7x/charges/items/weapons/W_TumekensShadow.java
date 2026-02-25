package tictac7x.charges.items.weapons;

import tictac7x.charges.item.triggers.OnAutoChargeMessage;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnGraphicChanged;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;

import java.util.List;

public class W_TumekensShadow extends ChargedItem {
    public W_TumekensShadow(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.tumekens_shadow, ItemId.TUMEKENS_SHADOW, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.TUMEKENS_SHADOW_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.TUMEKENS_SHADOW),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Tumeken's shadow( only)? has (?<charges>.+) charges? (remaining|left)").setDynamicallyCharges(),

            // Uncharge.
            new OnChatMessage("You uncharge your Tumeken's shadow").setFixedCharges(0),

            // Charge.
            new OnChatMessage("You apply (?<charges>.+) to your Tumeken's shadow.").setDynamicallyCharges(),

            // Charge additionally.
            new OnChatMessage("You apply an additional .* charges to your Tumeken's shadow. It now has (?<charges>.+) charges in total.").setDynamicallyCharges(),

            // Attack.
            new OnGraphicChanged(2125).decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Tumeken's shadow", "Soul rune", 0.5, this)
        ));
    }
}
