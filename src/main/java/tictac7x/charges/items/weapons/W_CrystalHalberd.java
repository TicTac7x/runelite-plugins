package tictac7x.charges.items.weapons;

import net.runelite.api.gameval.*;
import tictac7x.charges.*;
import tictac7x.charges.item.*;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.*;
import tictac7x.charges.store.ids.*;

import java.util.*;

public class W_CrystalHalberd extends ChargedItem {
    public W_CrystalHalberd(Provider provider) {
        super(TicTac7xChargesImprovedConfig.crystal_halberd, ItemID.CRYSTAL_HALBERD, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.CRYSTAL_HALBERD_INACTIVE).fixedCharges(0),
            new TriggerItem(ItemID.CRYSTAL_HALBERD),
            new TriggerItem(ItemID.CRYSTAL_HALBERD_2500).fixedCharges(2500),
        };

        this.triggers.addAll(List.of(
            // Check.
            new OnChatMessage("Your crystal halberd has (?<charges>.+) charges? remaining.").setDynamicallyCharges(),

            // Attack with stab.
            new OnAnimationChanged(AnimationID.HUMAN_SPEAR_SPIKE).isEquipped().decreaseCharges(1),

            // Attack with slash.
            new OnAnimationChanged(AnimationID.HUMAN_SCYTHE_SWEEP).isEquipped().decreaseCharges(1),

            // Attack with special.
            new OnAnimationChanged(AnimationID.DRAGON_HALBERD_SPECIAL_ATTACK).isEquipped().decreaseCharges(1),

            // Auto-charge.
            new OnAutoChargeMessage("Crystal halberd", "Crystal shard", 100, this)
        ));
    }
}
