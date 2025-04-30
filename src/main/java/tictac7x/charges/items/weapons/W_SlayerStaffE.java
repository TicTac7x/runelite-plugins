package tictac7x.charges.items.weapons;

import tictac7x.charges.store.ids.AnimationId;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Provider;

public class W_SlayerStaffE extends ChargedItem {
    public W_SlayerStaffE(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.slayer_staff_e, ItemId.SLAYER_STAFF_ENCHANTED, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.SLAYER_STAFF_ENCHANTED)
        };

        this.triggers = new TriggerBase[] {
            // Enchant.
            new OnChatMessage("The spell enchants your staff. The tatty parchment crumbles to dust.").setFixedCharges(2500),

            // Check.
            new OnChatMessage("Your staff has (?<charges>.+) charges?.").setDynamicallyCharges(),

            // Attack.
            new OnAnimationChanged(AnimationId.SLAYER_STAFF_CAST).isEquipped().decreaseCharges(1),
        };
    }
}
