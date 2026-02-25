package tictac7x.charges.items.weapons;

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

public class W_CrystalHalberd extends ChargedItem {
    public W_CrystalHalberd(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_halberd, ItemId.CRYSTAL_HALBERD, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.CRYSTAL_HALBERD_UNCHARGED).fixedCharges(0),
            new TriggerItem(ItemId.CRYSTAL_HALBERD),
            new TriggerItem(ItemId.CRYSTAL_HALBERD_FULL).fixedCharges(2500),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your crystal halberd has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack with stab.
            new OnAnimationChanged(AnimationId.HUMAN_SPEAR_SPIKE).isEquipped().decreaseCharges(1),

            // Attack with slash.
            new OnAnimationChanged(AnimationId.HUMAN_SCYTHE_SWEEP).isEquipped().decreaseCharges(1),

            // Attack with special.
            new OnAnimationChanged(AnimationId.HUMAN_HALBERD_SPECIAL).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Crystal halberd", "Crystal shard", 100, this)
        ));
    }
}
